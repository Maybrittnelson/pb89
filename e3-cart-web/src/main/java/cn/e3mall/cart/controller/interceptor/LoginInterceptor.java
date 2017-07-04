package cn.e3mall.cart.controller.interceptor;

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
	
	@Autowired
	private SsoService ssoService;
	
	@Autowired
	private CartService cartService;
	
	@Value("${E3_TOKEN}")
	private String E3_TOKEN;
	
	@Value("${COOKIE_CART_NAME}")
	private String COOKIE_CART_NAME;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//未登录返回
		String token = CookieUtils.getCookieValue(request, E3_TOKEN, true);
		if(StringUtils.isBlank(token)) {
			return true;
		}
		//判断登录超时
		E3Result e3Result = ssoService.checkLogin(token);
		TbUser tbUser = (TbUser)e3Result.getData();
		if(tbUser==null) {
			return true;
		}	
		//判读cookie是否有cartList
		String cartListJson = CookieUtils.getCookieValue(request, COOKIE_CART_NAME, true);
		if(StringUtils.isNotBlank(cartListJson)) {//合并cookie与redis数据
			cartService.mergeCartListFromCookie2Redis(tbUser.getId(), JsonUtils.jsonToList(cartListJson, Cart.class));
			CookieUtils.deleteCookie(request, response, COOKIE_CART_NAME);//清空cookie中的值
		}
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
