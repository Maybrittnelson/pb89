package cn.e3mall.search.service;

import cn.e3mall.common.utils.E3Result;

/**
 * 后台系统&索引库业务层
 */
public interface SearchItemService {
	
	/**
	 * 添加全部商品到索引库
	 * @return
	 */
	E3Result importItems();
	
	
	/**
	 * 添加新增商品到索引库
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	E3Result addDocument(Long itemId) throws Exception;	
}
