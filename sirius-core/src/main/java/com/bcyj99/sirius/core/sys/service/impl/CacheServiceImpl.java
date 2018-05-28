package com.bcyj99.sirius.core.sys.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.bcyj99.sirius.core.sys.service.CacheService;

@Service
public class CacheServiceImpl<E> implements CacheService<E> {
	
	private static final Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);
	
	@Autowired
	private RedisTemplate<String,E> redisTemplate;

	@Override
	public E get(String key) {
		ValueOperations<String, E> valueOperations =redisTemplate.opsForValue();
		E obj = valueOperations.get(key);
		return obj;
	}
	
	public void set(String key,E value) {
		logger.info("写入redis-开始....");
		logger.info("写入redis-key:{}",key);
		logger.info("写入redis-value:{}",value);
		ValueOperations<String, E> valueOperations =redisTemplate.opsForValue();
		valueOperations.set(key, value);
		logger.info("写入redis-成功.");
	}

	@Override
	public void delete(String key) {
		logger.info("删除redis-开始....");
		logger.info("删除redis-key:{}",key);
		
		redisTemplate.delete(key);		
		logger.info("删除redis-成功.");
	}
	
	public List<E> getList(String key){
		logger.info("写入redis-setList-开始....");
		ListOperations<String,E> listOperations =redisTemplate.opsForList();
		Long size = listOperations.size(key);
		List<E> list = listOperations.range(key, 0, size-1);
		logger.info("写入redis-setList-成功");
		return list;
	}
	
	public void setList(String key,List<E> listValues) {
		logger.info("写入redis-setList-开始....");
		logger.info("写入redis-setList-key:{}",key);
		logger.info("写入redis-setList-listValues:{}",listValues);
		
		if(redisTemplate.hasKey(key)){
			redisTemplate.delete(key);
		}
		ListOperations<String,E> listOperations =redisTemplate.opsForList();
		
		listOperations.rightPushAll(key, listValues);
		
		logger.info("写入redis-setList-成功.");
	}
}
