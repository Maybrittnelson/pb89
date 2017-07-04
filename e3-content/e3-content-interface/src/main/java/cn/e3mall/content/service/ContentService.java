package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.po.DatagridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.manager.po.TbContent;

/**
 * 广告的业务层
 *
 */
public interface ContentService {
	

	/**
	 * 后台广告
	 * @param tbContent
	 * @return
	 */
	E3Result addContent(TbContent tbContent);
	

	/**
	 * 后台广告
	 * @param tbContent
	 * @return
	 */
	E3Result updateContent(TbContent tbContent);
	

	/**
	 * 后台广告
	 * @param ids
	 * @return
	 */
	E3Result deleteContent(String ids);


	/**
	 * 后台广告
	 * @param page
	 * @param rows
	 * @param categoryId
	 * @return
	 */
	DatagridResult getContentList(Integer page, Integer rows, Long categoryId);
	
	
	/**
	 * 首页广告
	 * @param categoryId
	 * @return
	 */
	List<TbContent> getContentList(Long categoryId);
}
