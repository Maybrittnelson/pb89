package cn.e3mall.manager.service;

import cn.e3mall.common.po.DatagridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.manager.po.TbItem;

/**
 * 商品业务层
 *
 */
public interface ItemService {
	
	/**
	 * 查询一条
	 * @param id
	 * @return
	 */
	TbItem queryItemById(Long id);
	
	/**
	 * 查询分页
	 * @param page
	 * @param rows
	 * @return
	 */
	DatagridResult queryItemList(Integer page, Integer rows);
	
	/**
	 * 添加商品
	 * @param item
	 * @param description
	 * @return
	 */
	E3Result saveItem(TbItem item, String description);
	
	
	/**
	 * 删除商品
	 * @param ids
	 * @return
	 */
	E3Result deleteItem(String ids);
}
