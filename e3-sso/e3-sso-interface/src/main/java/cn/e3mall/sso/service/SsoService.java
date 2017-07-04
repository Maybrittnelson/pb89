package cn.e3mall.sso.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.manager.po.TbUser;

/**
 * 单点登录业务层
 */
public interface SsoService {
	
	/**
	 * 注册
	 * @param tbUser
	 * @return
	 */
	E3Result register(TbUser tbUser);
	
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	E3Result login(String username, String password);
	
	/**
	 * 登录超时校验
	 * @param token
	 * @return
	 */
	E3Result checkLogin(String token);
	
}
