package cn.e3mall.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.po.DatagridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.manager.po.TbContent;

/**
 * 广告内容表现层
 * 
 */
@Controller
@RequestMapping("/content")
public class ContentController {
	
	@Autowired
	private ContentService service;
	
	@RequestMapping("/query/list")
	@ResponseBody
	public DatagridResult queryItemList(@RequestParam(defaultValue="1") Integer page, @RequestParam(defaultValue="30") Integer rows,@RequestParam(defaultValue="0") Long categoryId) {
		return service.getContentList(page, rows,categoryId);
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public E3Result create(TbContent tbContent) {
		return service.addContent(tbContent);
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public E3Result update(TbContent tbContent) {
		return service.updateContent(tbContent);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public E3Result delete(String ids) {
		return service.deleteContent(ids);
	}
	
}
