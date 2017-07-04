package cn.e3mall.order.controller.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.manager.po.TbUser;
import cn.e3mall.manager.po.ext.Cart;
import cn.e3mall.sso.service.SsoService;

public class LoginInterceptor implements HandlerInterceptor {
	
	@Value("${E3_TOKEN}")
	private String E3_TOKEN;
	
	@Value("${SSO_LOGIN_URL}")
	private String SSO_LOGIN_URL;
	
	@Value("${COOKIE_CART_NAME}")
	private String COOKIE_CART_NAME;
	
	@Autowired
	private SsoService ssoService;
	
	@Autowired
	private CartService cartService;

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//判断是否登录
		String token = CookieUtils.getCookieValue(request, E3_TOKEN, true);
		if(StringUtils.isBlank(token)) {
			String redirectUrl = request.getRequestURL().toString();
			response.sendRedirect(SSO_LOGIN_URL+redirectUrl);
			return false;
		}
		//判断是否登录超时
		E3Result e3Result = ssoService.checkLogin(token);
		if(e3Result.getData()==null) {
			String redirectUrl = request.getRequestURL().toString();
			response.sendRedirect(SSO_LOGIN_URL+redirectUrl);
			return false;
		}
		TbUser user = (TbUser)e3Result.getData();
		//登录成功合并cookie与session中cartList
		String cartListJson = CookieUtils.getCookieValue(request, COOKIE_CART_NAME, true);
		if(StringUtils.isNotBlank(cartListJson)) {
			cartService.mergeCartListFromCookie2Redis(user.getId(), JsonUtils.jsonToList(cartListJson, Cart.class));
			CookieUtils.deleteCookie(request, response, COOKIE_CART_NAME);
		}
		request.setAttribute("user", user);
 		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
