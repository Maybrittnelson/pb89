package cn.e3mall.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.manager.po.TbItem;
import cn.e3mall.manager.po.TbUser;
import cn.e3mall.manager.po.ext.Cart;
import cn.e3mall.manager.po.ext.Item;
import cn.e3mall.manager.service.ItemService;

/**
 * cartList在:
 * 		redis
 * 			(搜索//redis)
 * 		cookie
 * 			(搜索//cookie)
 * 中的curd
 *
 */
@RequestMapping("/cart")
@Controller
public class CartController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private CartService cartService;
	
	@Value("${COOKIE_CART_NAME}")
	private String COOKIE_CART_NAME;
	
	@RequestMapping("/showCart")
	public String showCartList(HttpServletRequest request) {
		List<Cart> cartList = null;
		TbUser tbUser = (TbUser)request.getAttribute("user");
		if(tbUser!=null) {//redis
			cartList = cartService.getCartListFromRedis(tbUser.getId());
		} else {//cookie
			cartList = getCartListFromCookie(request);
		}
		request.setAttribute("cartList", cartList);
		return "cart";
	}
	
	@RequestMapping("/showCartSuccess")
	public String showCartSuccess() {
		return "cartSuccess";
	}

	@RequestMapping("/add/{itemId}/{num}")
	public String addItem(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request, HttpServletResponse response) {
		//redis
		TbUser tbUser = (TbUser)request.getAttribute("user");
		if(tbUser!=null) {
			cartService.addItem2CartListFromJedis(tbUser.getId(), itemId, num);
			return "redirect:/cart/showCartSuccess";
		}
		
		//cookie
		List<Cart> cartListFromCookie = this.getCartListFromCookie(request);
		//定义falg
		boolean flag = false;
		
		for (Cart cart : cartListFromCookie) {
			if(cart.getId().equals(itemId)) {
				cart.setNum(cart.getNum()+num);
				flag = true;
				break;
			}
		}
		
		if(!flag) {//如果flag为false,证明原先cookie没有该item信息
			TbItem tbItem = itemService.queryItemById(itemId);
			Item item = new Item();
			if(tbItem!=null) {
				item = new Item(tbItem);
			}
			
			Cart cart = new Cart();
			cart.setId(item.getId());
			cart.setImages(item.getImages());
			cart.setPrice(item.getPrice());
			cart.setTitle(item.getTitle());
			
			cart.setNum(num);//当前页面传参商品数量
			cartListFromCookie.add(cart );
		}
		
		CookieUtils.setCookie(request, response, COOKIE_CART_NAME, JsonUtils.objectToJson(cartListFromCookie), true);
		return "redirect:/cart/showCartSuccess";
	}
	
	
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public String updateItemNum(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request, HttpServletResponse response) {
		//redis
		TbUser tbUser = (TbUser)request.getAttribute("user");
		if(tbUser!=null) {
			cartService.updateItemNumFromRedisCartList(tbUser.getId(), itemId, num);
			return "ok";//ajax页面不处理data
		}
		
		//cookie
		List<Cart> cartListFromCookie = this.getCartListFromCookie(request);
		for (Cart cart : cartListFromCookie) {
			if(cart.getId().equals(itemId)) {
				cart.setNum(num);
				break;
			}
		}
		CookieUtils.setCookie(request, response, COOKIE_CART_NAME, JsonUtils.objectToJson(cartListFromCookie), true);
		
		return "ok";
	}
	
	@RequestMapping("/delete/{itemId}")
	public String deleteItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
		//redis
		TbUser tbUser = (TbUser)request.getAttribute("user");
		if(tbUser!=null) {
			cartService.deleteItemFromRedisCartList(tbUser.getId(), itemId);
			return "redirect:/cart/showCart";
		}
		
		//cookie
		List<Cart> cartListFromCookie = this.getCartListFromCookie(request);
		for (Cart cart : cartListFromCookie) {
			if(cart.getId().equals(itemId)) {
				cartListFromCookie.remove(cart);
				break;
			}
		}
		CookieUtils.setCookie(request, response, COOKIE_CART_NAME, JsonUtils.objectToJson(cartListFromCookie), true);
		
		return "redirect:/cart/showCart";
	}
	
	public List<Cart> getCartListFromCookie(HttpServletRequest request) {
		List<Cart> cartList = new ArrayList<>();//防止讯后续foreach出现空指针,需new
		String cartListJson = CookieUtils.getCookieValue(request, COOKIE_CART_NAME, true);
		if(StringUtils.isNotBlank(cartListJson)) {
			cartList = JsonUtils.jsonToList(cartListJson, Cart.class);
		}
		return cartList;
	}
}
