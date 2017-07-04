package cn.e3mall.cart.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.manager.mapper.TbItemMapper;
import cn.e3mall.manager.po.TbItem;
import cn.e3mall.manager.po.ext.Cart;
import cn.e3mall.manager.po.ext.Item;
import redis.clients.jedis.JedisCluster;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private JedisCluster cluster;
	
	@Autowired
	private TbItemMapper mapper;//用原生
	
	@Value("${REDIS_CART_KEY_PRE}")
	private String REDIS_CART_KEY_PRE;
	
	@Value("${REDIS_CART_EXPIRE}")
	private int REDIS_CART_EXPIRE;
	
	@Override
	public List<Cart> getCartListFromRedis(Long userId) {
		//
		String cartListJson = cluster.get(REDIS_CART_KEY_PRE+userId);
		List<Cart> cartList = new ArrayList<>();
		if(StringUtils.isNotBlank(cartListJson)) {
			cartList = JsonUtils.jsonToList(cartListJson, Cart.class);
		}
		return cartList;
	}

	@Override
	public void addItem2CartListFromJedis(Long userId, Long itemId, Integer num) {
		List<Cart> cartList = this.getCartListFromRedis(userId);
		
		boolean flag = false;
		for (Cart cart : cartList) {
			if(cart.getId().equals(itemId)) {
				cart.setNum(cart.getNum()+num);
				flag = true;
				break;
			}
		}
		
		if(!flag) {
			TbItem tbItem = mapper.selectByPrimaryKey(itemId);
			Item item = new Item();
			if(tbItem!=null) {
				item = new Item(tbItem);
			}
			Cart cart = new Cart();
			cart.setId(item.getId());
			cart.setPrice(item.getPrice());
			cart.setTitle(item.getTitle());
			cart.setImages(item.getImages());
			
			cart.setNum(num);
			cartList.add(cart);
		}
		
		updateCartListFromCluster(userId, cartList);//更新redis缓存
	}

	@Override
	public void updateItemNumFromRedisCartList(Long userId, Long itemId, Integer num) {
		List<Cart> cartList = this.getCartListFromRedis(userId);
		for (Cart cart : cartList) {
			if(cart.getId().equals(itemId)) {
				cart.setNum(num);
				break;
			}
		}
		
		updateCartListFromCluster(userId, cartList);
	}
	
	@Override
	public void deleteItemFromRedisCartList(Long userId, Long itemId) {
		List<Cart> cartListFromRedis = this.getCartListFromRedis(userId);
		for (Cart cart : cartListFromRedis) {
			if(cart.getId().equals(itemId)) {
				cartListFromRedis.remove(cart);
				break;
			}
		}
	}


	@Override
	public void mergeCartListFromCookie2Redis(Long userId, List<Cart> jsonToList) {
		for (Cart cart : jsonToList) {
			this.addItem2CartListFromJedis(userId, cart.getId(), cart.getNum());
		}
	}
	
	/**
	 * @param userId
	 * @param cartList
	 */
	public void updateCartListFromCluster(Long userId, List<Cart> cartList) {
		cluster.set(REDIS_CART_KEY_PRE+userId, JsonUtils.objectToJson(cartList));//重新赋值
		
		cluster.expire(REDIS_CART_KEY_PRE+userId, REDIS_CART_EXPIRE);//设置过期时间
	}




}
