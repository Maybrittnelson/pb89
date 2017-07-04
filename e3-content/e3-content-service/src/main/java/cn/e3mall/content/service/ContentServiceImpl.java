package cn.e3mall.content.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.po.DatagridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.jedis.JedisClient;
import cn.e3mall.manager.mapper.TbContentMapper;
import cn.e3mall.manager.po.TbContent;
import cn.e3mall.manager.po.TbContentExample;
import cn.e3mall.manager.po.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper mapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_CONTENT_KEY}")
	private String REDIS_CONTENT_KEY;
	
	@Override
	public E3Result addContent(TbContent tbContent) {
		Date date = new Date();
		tbContent.setCreated(date);
		tbContent.setUpdated(date);
		mapper.insert(tbContent);
		
		return E3Result.ok();
	}

	@Override
	public E3Result updateContent(TbContent tbContent) {
		Date date = new Date();
		tbContent.setUpdated(date);
		mapper.updateByPrimaryKey(tbContent);
		
		cleaJedis(tbContent.getCategoryId().toString());//缓存同步 
		return E3Result.ok();
	}

	@Override
	public E3Result deleteContent(String ids) {
		if(StringUtils.isNotBlank(ids)) {
			String[] contentIds = ids.split(",");
			
			//根据id获取商品分类id,并清除缓存
			TbContent tbContent = mapper.selectByPrimaryKey(Long.parseLong(contentIds[0]));
			if (tbContent!=null&&tbContent.getCategoryId()!=null) {
				cleaJedis(""+tbContent.getCategoryId());
			}
			for (String id : contentIds) {
				mapper.deleteByPrimaryKey(Long.parseLong(id));
			}
			
		}
		//清楚huan'cun
		return E3Result.ok();
	}

	@Override
	public DatagridResult getContentList(Integer page, Integer rows, Long categoryId) {
		if(page==null) {
			page = 1;
		}
		if(rows==null) {
			rows = 30;
		}
		
		//设置分页信息
		PageHelper.startPage(page, rows);
		
		//执行查询
		TbContentExample example = new TbContentExample();
		Criteria crt = example.createCriteria();
		crt.andCategoryIdEqualTo(categoryId);
		
		//example不能为空
		List<TbContent> list = mapper.selectByExample(example);
		
		//获取分页后的数据
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		
		//封装数据
		DatagridResult result = new DatagridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		
		return result;
	}

	@Override
	public List<TbContent> getContentList(Long categoryId) {
		List<TbContent> list;
		
		String json = jedisClient.hget(REDIS_CONTENT_KEY, categoryId + "");
		// 判断 json 是否为空
		if (StringUtils.isNotBlank(json)) {
			list = JsonUtils.jsonToList(json, TbContent.class);
			return list;
		}

		// 执行查询
		TbContentExample example = new TbContentExample();
		Criteria crt = example.createCriteria();
		crt.andCategoryIdEqualTo(categoryId);

		// example不能为空
		list = mapper.selectByExample(example);

		jedisClient.hset(REDIS_CONTENT_KEY, categoryId + "", JsonUtils.objectToJson(list));
		return list;
	}
	
	public void cleaJedis(String categoryId) {
		String json = jedisClient.hget(REDIS_CONTENT_KEY, categoryId);
		
		if (StringUtils.isNotBlank(json)) {
			jedisClient.hdel(REDIS_CONTENT_KEY, categoryId);//清空缓存
		}
	}
	

}
