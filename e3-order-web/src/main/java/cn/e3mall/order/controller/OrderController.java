package cn.e3mall.order.controller;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.manager.po.TbUser;
import cn.e3mall.manager.po.ext.Cart;
import cn.e3mall.manager.po.ext.OrderInfo;
import cn.e3mall.order.service.OrderService;

@RequestMapping("/order")
@Controller
public class OrderController {
	
	@Autowired
	private CartService cartService;
	
	@RequestMapping("/order-cart") 
	public String showOrderCart(HttpServletRequest request) {
		TbUser tbUser = (TbUser)request.getAttribute("user");
		List<Cart> cartList = cartService.getCartListFromRedis(tbUser.getId());
		request.setAttribute("cartList", cartList);
		return "order-cart";
	}
	
	@Autowired
	private OrderService orderService;

	@RequestMapping("/create")
	public String createOrder(OrderInfo orderInfo, ServletRequest request) {
		// 获取登录信息
		TbUser user = (TbUser) request.getAttribute("user");

		// 设置用户ID
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());

		// 创建订单，并返回订单ID
		E3Result result = orderService.creatOrder(orderInfo);

		// 如果没有订单ID，则说明创建订单失败
		if (result.getData() == null) {
			request.setAttribute("message", result.getMsg());
			return "error/exception";
		}
		// request域数据：orderId、payment、date
		request.setAttribute("orderId", result.getData());
		request.setAttribute("payment", orderInfo.getPayment());

		// 计算预计到达时间（三天后）
		DateTime dateTime = new DateTime();
		dateTime = dateTime.plusDays(3);

		request.setAttribute("date", dateTime.toString("yyyy-MM-dd"));
		
		return "success";
	}

	

}
