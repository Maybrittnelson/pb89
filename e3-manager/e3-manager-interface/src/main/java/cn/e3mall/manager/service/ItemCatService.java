package cn.e3mall.manager.service;

import java.util.List;

import cn.e3mall.common.po.TreeNodeResult;

/**
 * 商品分类业务层
 *
 */
public interface ItemCatService {
	
	/**
	 * 查询商品分类
	 * @param parentId
	 * @return
	 */
	List<TreeNodeResult> queryItemCatList(Long parentId);
}
