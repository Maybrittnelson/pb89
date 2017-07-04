package cn.e3mall.jedis;

public interface JedisClient {

	String set(String key, String value);

	String get(String key);

	Long hset(String key, String field, String value);

	String hget(String key, String field);
	
	Long incr(String key);

	Boolean exists(String key);

	Long expire(String key, int seconds);

	Long ttl(String key);

	Long hdel(String key, String... field);
}

