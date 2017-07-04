package cn.e3mall.search.service;

import java.util.List;

import cn.e3mall.common.po.SearchResult;

/**
 * 前台系统&索引库业务层
 */
public interface SearchService {
	
	/**
	 * 前台查询商品索引库分页
	 * @param page
	 * @param pageRows
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	SearchResult getSearchResults(Integer page, Integer pageRows, String keyword) throws Exception;
}
