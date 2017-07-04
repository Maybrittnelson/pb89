package cn.e3mall.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.po.DatagridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.manager.po.TbItem;
import cn.e3mall.manager.service.ItemService;

/**
 * 商品分类表现层
 * 
 */
@RequestMapping("/item")
@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("{id}")
	@ResponseBody
	public TbItem queryItemById(@PathVariable Long id) {
		if(id==null) {
			return null;
		}
		return itemService.queryItemById(id);
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public DatagridResult queryItemList(@RequestParam(defaultValue="1") Integer page, @RequestParam(defaultValue="30") Integer rows) {
		return itemService.queryItemList(page, rows);
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public E3Result saveItem(TbItem item, String description) {
		return itemService.saveItem(item, description);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public E3Result deleteItem(String ids) {
		return itemService.deleteItem(ids);
	}
}
