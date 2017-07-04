package cn.e3mall.manager.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.ctc.wstx.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.po.DatagridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.manager.mapper.TbItemMapper;
import cn.e3mall.manager.po.TbItem;
import cn.e3mall.manager.po.TbItemExample;
import redis.clients.jedis.JedisCluster;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper tbItemMapper;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Resource(name="topicDestination")
	private Destination topicDestination;
	
	@Autowired
	private JedisCluster cluster;
	
	@Value("${REDIS_ITEM_KEY_PRE}")
	private String REDIS_ITEM_KEY_PRE;

	@Value("${REDIS_ITEM_EXPIRE}")
	private int REDIS_ITEM_EXPIRE;

	@Override
	public TbItem queryItemById(Long id) {
		if(id==null) {
			id=0L;
		}
		TbItem tbItem;
		try{
			String josnData = cluster.get(REDIS_ITEM_KEY_PRE+id);
			if(StringUtils.isNotBlank(josnData)) {
				tbItem = JsonUtils.jsonToPojo(josnData, TbItem.class);
				return tbItem;
			}	
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		tbItem = tbItemMapper.selectByPrimaryKey(id);
		try{	
			cluster.set(REDIS_ITEM_KEY_PRE+id, JsonUtils.objectToJson(tbItem));
			cluster.expire(REDIS_ITEM_KEY_PRE+id, REDIS_ITEM_EXPIRE);//进行定时缓存处理
		} catch(Exception ex) {
			
		}
		return tbItem;
	}

	@Override
	public DatagridResult queryItemList(Integer page, Integer rows) {
		if(page==null) {
			page = 1;
		}
		if(rows==null) {
			rows = 30;
		}
		//设置分页信息
		PageHelper.startPage(page, rows);
		//执行查询
		TbItemExample example = new TbItemExample();
		//example不能为空
		List<TbItem> list = tbItemMapper.selectByExample(example);
		//获取分页后的数据
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		
		//封装数据
		DatagridResult result = new DatagridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		
		return result;
	}

	@Override
	public E3Result saveItem(TbItem item, String description) {
		final long itemId = IDUtils.genItemId();
		item.setId(itemId);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		item.setDescription(description);//富文本编辑内容
		item.setStatus((byte)1); //1 正常
		
		tbItemMapper.insert(item);
		
		//jmsTemplate
		jmsTemplate.send(topicDestination, new MessageCreator() {//发送消息
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(""+itemId);
				return textMessage;
			}
		});
		return E3Result.ok();
	}
	
	@Override
	public E3Result deleteItem(String ids) {
		if(StringUtils.isNotBlank(ids)) {
			String[] itemIds = ids.split(",");
			for (String id : itemIds) {
				//cleaJedis(id);缓存清除存在问题
				tbItemMapper.deleteByPrimaryKey(Long.parseLong(id));
			}
		}
		return E3Result.ok();
	}
	
	public void cleaJedis(String contentId) {
		String json = cluster.get(REDIS_ITEM_KEY_PRE+contentId);
		
		if (StringUtils.isNotBlank(json)) {
			cluster.del(REDIS_ITEM_KEY_PRE+contentId);//清空缓存
		}
	}

}
