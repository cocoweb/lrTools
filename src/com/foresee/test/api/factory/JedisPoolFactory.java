package com.foresee.test.api.factory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolFactory {

	/*
	 * Commend Refernece
	 * http://my.oschina.net/zhangdapeng89/blog/51953
	 */
	private static JedisPoolConfig config;
	private static JedisPool pool;
	private static Jedis jedis;
	
	static {
		config = new JedisPoolConfig();
		config.setMaxActive(200);
		config.setMaxIdle(20);
		config.setMaxWait(1000);
		config.setTestOnBorrow(false);	
	}

	private static void createPool(String host, int port){
		pool = new JedisPool(config, host, port); 
	}
	
	public static Jedis getResource(String host, int port) {
		createPool(host, port);
		jedis = pool.getResource();
		return jedis;
	}
	
	public static void quit(){
		jedis.quit();
	}

}
