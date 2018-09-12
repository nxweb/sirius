package com.bcyj99.sirius.core.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcyj99.sirius.core.sys.service.JedisService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class JedisServiceImpl implements JedisService {
	
	@Autowired
	private JedisPool jedisPool;

	@Override
	public String getKey(String key) {
		Jedis jedis = jedisPool.getResource();
		String str = jedis.get(key);
		return str;
	}

}
