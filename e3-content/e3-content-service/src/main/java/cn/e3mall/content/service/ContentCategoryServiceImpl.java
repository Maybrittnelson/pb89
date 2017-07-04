package cn.e3mall.content.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.po.TreeNodeResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.manager.mapper.TbContentCategoryMapper;
import cn.e3mall.manager.po.TbContentCategory;
import cn.e3mall.manager.po.TbContentCategoryExample;
import cn.e3mall.manager.po.TbContentCategoryExample.Criteria;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	
	@Autowired
	private TbContentCategoryMapper mapper;
	
	@Override
	public List<TreeNodeResult> getContentCategoryList(long parentId) {
		List<TbContentCategory> list = this.getChildList(parentId);
		List<TreeNodeResult> results = new ArrayList<>();
		TreeNodeResult result ;
		for (TbContentCategory tbContentCategory : list) {
			result = new TreeNodeResult();
			result.setId(tbContentCategory.getId());
			result.setText(tbContentCategory.getName());
			result.setState(tbContentCategory.getIsParent()?"closed":"open");
			results.add(result);
		}
		return results;
	}

	@Override
	public E3Result addContentCategory(long parentId, String name) {
	    TbContentCategory tbcc = new TbContentCategory();
		tbcc.setIsParent(false);
		tbcc.setName(name);
		tbcc.setParentId(parentId);
		tbcc.setStatus(1);//1正常 2删除
		tbcc.setSortOrder(1);
		Date date = new Date();
		tbcc.setCreated(date);
		tbcc.setUpdated(date);
		
		TbContentCategory tbParentNode = mapper.selectByPrimaryKey(parentId);
		if(!tbParentNode.getIsParent()) {
			tbParentNode.setIsParent(true);
			mapper.updateByPrimaryKey(tbParentNode);
		}
		
		mapper.insert(tbcc);
	    return E3Result.ok(tbcc);
	}

	@Override
	public E3Result updateContentCategory(long id, String name) {
		TbContentCategory tbcc = mapper.selectByPrimaryKey(id);
		if(StringUtils.isNoneBlank(name)) {
			tbcc.setName(name);
			tbcc.setUpdated(new Date());
			mapper.updateByPrimaryKey(tbcc);
		}
		return E3Result.ok();
	}

	@Override
	public E3Result deleteContentCategory(Long parentId, Long id) {
		
		mapper.deleteByPrimaryKey(id);//删除当前节点
/*		TbContentCategory currentNode = mapper.selectByPrimaryKey(id);//逻辑删除
		currentNode.setStatus(2);
		mapper.updateByPrimaryKey(currentNode);*/
		
		if(parentId!=null&&getChildList(parentId).size()==0){//当前节点的父节点,仅有此一个子节点
			TbContentCategory tbcc = mapper.selectByPrimaryKey(parentId);
			tbcc.setIsParent(false);//当前节点父节点,修改为无子节点
			mapper.updateByPrimaryKey(tbcc);
		}
		
		List<TbContentCategory> childList = getChildList(id);//获取当前节点的子节点
		if(childList!=null&&childList.size()>0) {
			parentId = id;
			for (TbContentCategory tbcc : childList) {
				id = tbcc.getId();
				this.deleteContentCategory(parentId, id);
			}
		}
		
		return E3Result.ok();
	}

	@Override
	public List<TbContentCategory> getChildList(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		return mapper.selectByExample(example);
	}

}
