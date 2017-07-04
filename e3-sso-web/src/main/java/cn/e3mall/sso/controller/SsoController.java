package cn.e3mall.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.manager.po.TbUser;
import cn.e3mall.sso.service.SsoService;

/**
 * 单点登录表现层
 */
@RequestMapping("/sso")
@Controller
public class SsoController {
	
	@Autowired
	private SsoService service;
	
	@RequestMapping("/showLogin")
	public String showLogin(String redirectUrl, Model model) {
		model.addAttribute("redirectUrl", redirectUrl);
		return "login";
	}
	
	@RequestMapping("/showRegister")
	public String showRegister() {
		return "register";
	}
	
	@RequestMapping("/register")
	@ResponseBody
	public E3Result register(TbUser tbUser) {
		return service.register(tbUser);
	}
	
	
	@Value("${E3_TOKEN}")
	private String E3_TOKEN;
	
	@RequestMapping("/login")
	@ResponseBody
	public E3Result login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		E3Result result = service.login(username, password);
		if(result.getData()==null) {
			return result;
		}
		CookieUtils.setCookie(request, response, E3_TOKEN, result.getData().toString(), true);
		
		return result;
	}
	
	@RequestMapping("/token/{token}")
	@ResponseBody
	public String checkLogin(@PathVariable String token, String callback) {
		E3Result result = service.checkLogin(token);
		if(StringUtils.isBlank(callback)) {
			return JsonUtils.objectToJson(result);
		}
		
		return callback+"("+JsonUtils.objectToJson(result)+")";
	}
	
}
