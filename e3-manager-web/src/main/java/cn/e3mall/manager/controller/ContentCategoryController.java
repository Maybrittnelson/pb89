package cn.e3mall.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.po.TreeNodeResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;

/**
 * 广告内容分类表现层
 * 
 */
@RequestMapping("/contentCategory")
@Controller
public class ContentCategoryController {
	
	@Autowired
	private ContentCategoryService service;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<TreeNodeResult> list(@RequestParam(value="id",defaultValue="0")Long parentId) {
		return service.getContentCategoryList(parentId);
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public E3Result create(@RequestParam(defaultValue="0")Long parentId, String name) {
		return service.addContentCategory(parentId, name);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public E3Result update(Long id, String name) {
		return service.updateContentCategory(id, name);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public E3Result delete(Long parentId, Long id) {
		return service.deleteContentCategory(parentId, id);
	}

}
