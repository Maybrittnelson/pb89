package cn.e3mall.content.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.e3mall.jedis.JedisClient;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

public class JedisTest {

/*	@Test
	public void test01() throws Exception {
		// 第一步：使用 JedisCluster 对象。需要一个 Set<HostAndPort>参数。Redis 节点的列表。
		Set<HostAndPort> nodes = new HashSet<>(); 
		nodes.add(new HostAndPort("192.168.242.128", 7001)); 
		nodes.add(new HostAndPort("192.168.242.128", 7002)); 
		nodes.add(new HostAndPort("192.168.242.128", 7003)); 
		nodes.add(new HostAndPort("192.168.242.128", 7004)); 
		nodes.add(new HostAndPort("192.168.242.128", 7005)); 
		nodes.add(new HostAndPort("192.168.242.128", 7006)); 
		// 第二步：直接使用 JedisCluster 对象操作 redis。在系统中单例存在。
		JedisCluster jedisCluster = new JedisCluster(nodes); 
		jedisCluster.set("hello", "100"); 
		String result = jedisCluster.get("hello"); 
		// 第三步：打印结果 
		System.out.println(result);
		//第四步：系统关闭前，关闭 JedisCluster 对象。 
		jedisCluster.close();
	}*/
	
/*	@Test
	public void test02() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		JedisClient jedis = ac.getBean(JedisClient.class);
		jedis.set("hello", "100");
		String str = jedis.get("hello");
		System.out.println(str);
	}*/

}
