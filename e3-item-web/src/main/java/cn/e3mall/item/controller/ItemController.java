package cn.e3mall.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.manager.po.TbItem;
import cn.e3mall.manager.po.ext.Item;
import cn.e3mall.manager.service.ItemService;

/**
 * 商品详情展示
 *
 */
@Controller
public class ItemController {
	
	@Autowired
	private ItemService service;
	
	@RequestMapping("/item/{itemId}")
	public String showItemPage(@PathVariable Long itemId, Model model) {
		TbItem tbItem = service.queryItemById(itemId);
		Item item = new Item(tbItem);
		
		model.addAttribute("item", item);
		return "item";
	}
}
