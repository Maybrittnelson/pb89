package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.po.TreeNodeResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.manager.po.TbContentCategory;

/**
 * 内容分类的业务层
 *
 */
public interface ContentCategoryService {
	
	/**
	 * 查询内容分类
	 * @param parentId
	 * @return
	 */
	List<TbContentCategory> getChildList(long parentId);
	
	/**
	 * 查询内容分类
	 * @param parentId
	 * @return
	 */
	List<TreeNodeResult> getContentCategoryList(long parentId);
	
	/**
	 * 添加内容分类
	 * @param parentId
	 * @param name
	 * @return
	 */
	E3Result addContentCategory(long parentId, String name);
	
	/**
	 * 重命名内容分类
	 * @param id
	 * @param name
	 * @return
	 */
	E3Result updateContentCategory(long id, String name);
	
	/**
	 * 删除内容分类
	 * @param id
	 * @param name
	 * @return
	 */
	E3Result deleteContentCategory(Long parentId, Long id);
	
	
}
