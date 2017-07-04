package cn.e3mall.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.manager.service.ItemService;
import cn.e3mall.search.service.SearchItemService;

/**
 * 后台管理页面表现层
 * 
 */
@Controller
public class SystemController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private SearchItemService service;
	
	
	@RequestMapping("/")
	public String showIndexPage() {
		return "index";
	}
	
	@RequestMapping("system/{pageName}")
	public String showPage(@PathVariable String pageName) {
		return pageName;
	}
	
	/**
	 * 将商品导入索引库
	 * @return
	 */
	@RequestMapping("/index/item/import")
	@ResponseBody
	public E3Result importSearchItems() {
		return service.importItems();
	}
	
}
