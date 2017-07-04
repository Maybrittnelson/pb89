package cn.e3mall.manager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.po.TreeNodeResult;
import cn.e3mall.manager.mapper.TbItemCatMapper;
import cn.e3mall.manager.po.TbItemCat;
import cn.e3mall.manager.po.TbItemCatExample;
import cn.e3mall.manager.po.TbItemCatExample.Criteria;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper mapper;
	
	@Override
	public List<TreeNodeResult> queryItemCatList(Long parentId) {
		if(parentId==null) {
			parentId = 0L;
		}
		
		TbItemCatExample example = new TbItemCatExample();
		Criteria  criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		List<TbItemCat> list = mapper.selectByExample(example);
		List<TreeNodeResult> results = new ArrayList<>();
		for (TbItemCat tbItemCat : list) {
			TreeNodeResult result = new TreeNodeResult();
			result.setId(tbItemCat.getId());
			result.setText(tbItemCat.getName());
			result.setState(tbItemCat.getIsParent()?"closed":"open");
			results.add(result);
		}
		
		return results;
	}

}
