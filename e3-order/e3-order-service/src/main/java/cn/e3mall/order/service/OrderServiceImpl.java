package cn.e3mall.order.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.manager.mapper.TbItemMapper;
import cn.e3mall.manager.mapper.TbOrderItemMapper;
import cn.e3mall.manager.mapper.TbOrderMapper;
import cn.e3mall.manager.mapper.TbOrderShippingMapper;
import cn.e3mall.manager.po.TbItem;
import cn.e3mall.manager.po.TbOrderItem;
import cn.e3mall.manager.po.ext.OrderInfo;
import redis.clients.jedis.JedisCluster;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private TbOrderMapper orderMapper;

	@Autowired
	private TbOrderShippingMapper orderShippingMapper;

	@Autowired
	private TbOrderItemMapper orderItemMapper;

	@Autowired
	private TbItemMapper itemMapper;

	@Autowired
	private JedisCluster cluster;

	@Value("${REDIS_ORDER_ID_KEY}")
	private String REDIS_ORDER_ID_KEY;

	@Value("${REDIS_ORDER_ID_START_NUM}")
	private String REDIS_ORDER_ID_START_NUM;

	@Value("${REDIS_ORDER_ITEM_ID_KEY}")
	private String REDIS_ORDER_ITEM_ID_KEY;
	
	@Override
	public E3Result creatOrder(OrderInfo orderInfo) {
		
		for (TbOrderItem orderItem : orderInfo.getOrderItems()) {
			TbItem tbItem = itemMapper.selectByPrimaryKey(Long.parseLong(orderItem.getId()));
			if(orderItem.getNum()<1) {//库存不足的校验
				return E3Result.build(500, "商品库存不足");
			}
			if(orderItem.getPrice().equals(orderItem.getPrice())) {//商品单价不一致
				return E3Result.build(500, "购物车中的价格与实际商品单价不符");
			}
		}
		// 3、创建订单ID（使用redis的自增方式创建）
		String value = cluster.get(REDIS_ORDER_ID_KEY);
		// 如果第一次生成订单ID，则需要设置一个初始值
		if (StringUtils.isEmpty(value)) {
			cluster.set(REDIS_ORDER_ID_KEY, REDIS_ORDER_ID_START_NUM);
		}

		// 生成订单ID
		String orderId = cluster.incr(REDIS_ORDER_ID_KEY).toString();

		// 4、补全订单属性
		orderInfo.setOrderId(orderId);
		orderInfo.setPostFee("0");
		// 默认为未付款
		orderInfo.setStatus(1);
		orderInfo.setCreateTime(new Date());
		orderInfo.setUpdateTime(new Date());

		// 5、添加订单表
		orderMapper.insert(orderInfo);

		// 6、对订单明细信息循环遍历
		for (TbOrderItem orderItem : orderInfo.getOrderItems()) {
			// 生成订单明细ID
			String orderItemId = cluster.incr(REDIS_ORDER_ITEM_ID_KEY).toString();
			// 补全属性
			orderItem.setId(orderItemId);
			orderItem.setOrderId(orderId);
			// 添加订单商品明细表
			orderItemMapper.insert(orderItem);
		}
		// 添加订单配送表
		orderInfo.getOrderShipping().setOrderId(orderId);
		orderInfo.getOrderShipping().setCreated(new Date());
		orderInfo.getOrderShipping().setUpdated(new Date());
		orderShippingMapper.insert(orderInfo.getOrderShipping());

		// 返回订单ID
		return E3Result.ok(orderId);
	}
	
	
}
