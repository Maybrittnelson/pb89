package cn.e3mall.manager.mapper.search;

import java.util.List;

import cn.e3mall.common.po.SearchItem;

/**
 * 将商品添加到索引库持久层
 *
 */
public interface ItemMapper {
	
	/**
	 * 查询所有
	 * @return
	 */
	List<SearchItem> getItemList();
	
	
	/**
	 * 查询一条
	 * @param itemId
	 * @return
	 */
	SearchItem getItemById(Long itemId);
}
