package cn.e3mall.sso.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.util.DebugUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.manager.mapper.TbUserMapper;
import cn.e3mall.manager.po.TbUser;
import cn.e3mall.manager.po.TbUserExample;
import cn.e3mall.manager.po.TbUserExample.Criteria;
import redis.clients.jedis.JedisCluster;

@Service
public class SsoServiceImpl implements SsoService {
	
	@Autowired
	private TbUserMapper mapper;
	
	@Override
	public E3Result register(TbUser tbUser) {
		//判断对象是否为空
		if(tbUser == null) {
			return E3Result.build(500, "用户信息不得为空");
		}
		
		//判断对象的姓名是否为空
		if(!StringUtils.isNotBlank(tbUser.getUsername())) {
			return E3Result.build(500, "用户昵称不得为空");
		}
		
		//根据用户名,取出用户对象
		TbUserExample example = new TbUserExample();
		Criteria crt = example.createCriteria();
		crt.andUsernameEqualTo(tbUser.getUsername());
		List<TbUser> list = mapper.selectByExample(example );
		
		
		if(list!=null&&list.size()>0) {//已存在
			return E3Result.build(500, "该用户名已存在");
		}
		
		//判断对象的手机号是否为空
		if(!StringUtils.isNotBlank(tbUser.getPhone())) {
			return E3Result.build(500, "用户手机号不得为空");
		}
		
		//根据手机号,取出用户对象
		example.clear();
		Criteria crt1 = example.createCriteria();
		crt1.andPhoneEqualTo(tbUser.getPhone());
		List<TbUser> list1 = mapper.selectByExample(example );
		
		
		if(list1!=null&&list1.size()>0) {//已存在
			return E3Result.build(500, "该手机号已经存在");
		}
		
		//未存在
		//完成剩余用户信息
		String password = tbUser.getPassword();
		if(!StringUtils.isNotBlank(password)) {
			return E3Result.build(500, "密码不得为空");
		}
		
		
		password = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());//对密码加密
		
		tbUser.setPassword(password);
		tbUser.setUpdated(new Date());
		tbUser.setCreated(new Date());
		mapper.insert(tbUser);
		
		return E3Result.ok();
	}
	
	@Autowired
	private JedisCluster cluster;
	
	@Value("${REDIS_SESSION_ID_PRE}")
	private String REDIS_SESSION_ID_PRE;
	
	@Value("${REDIS_SESSION_USER_KEY}")
	private String REDIS_SESSION_USER_KEY;
	
	@Value("${REDIS_SESSION_EXPIRE}")
	private int REDIS_SESSION_EXPIRE;

	@Override
	public E3Result login(String username, String password) {
		//判断用户名是否为空
		if(StringUtils.isBlank(username)) {
			return E3Result.build(500, "用户名不得为空");
		}
		
		//根据用户名搜索数据库
		TbUserExample example = new TbUserExample();
		Criteria crt = example.createCriteria();
		crt.andUsernameEqualTo(username);
		List<TbUser> list = mapper.selectByExample(example);
		
		//判读是否存在该用户名
		if(list==null||list.size()!=1) {
			return E3Result.build(500, "该用户名不存在");
		}
		
		//判断密码是否一致
		TbUser user = list.get(0);
		password = DigestUtils.md5DigestAsHex(password.getBytes());
		if(!user.getPassword().equals(password)) {
			return E3Result.build(500, "密码不匹配");
		}
		
		//添加到redis集群中
		String token = UUID.randomUUID().toString();
		cluster.hset(REDIS_SESSION_ID_PRE+token, REDIS_SESSION_USER_KEY, JsonUtils.objectToJson(user));
		cluster.expire(REDIS_SESSION_ID_PRE+token, REDIS_SESSION_EXPIRE);
		
		return E3Result.ok(token);
	}

	@Override
	public E3Result checkLogin(String token) {
		if(StringUtils.isBlank(token)) {
			return E3Result.build(500, "未登录,请重新登录");
		}
		String tbUser = cluster.hget(REDIS_SESSION_ID_PRE+token, REDIS_SESSION_USER_KEY);
		if(StringUtils.isBlank(tbUser)) {
			return E3Result.build(500, "登录超时,请重新登录");
		}
		
		cluster.expire(REDIS_SESSION_ID_PRE+token, REDIS_SESSION_EXPIRE);
		return E3Result.ok(JsonUtils.jsonToPojo(tbUser, TbUser.class));
	}

}
