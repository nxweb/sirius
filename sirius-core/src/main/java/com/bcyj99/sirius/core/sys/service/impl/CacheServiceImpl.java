package com.bcyj99.sirius.core.sys.service.impl;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.bcyj99.sirius.core.sys.processor.CacheProcessor;
import com.bcyj99.sirius.core.sys.service.CacheService;

@Service
public class CacheServiceImpl<E> implements CacheService<E> {
	
	private static final Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);
	
	@Autowired
	private RedisTemplate<String,E> redisTemplate;
	
	public Set<String> getAllKeys(){
		return redisTemplate.keys("*");
	}

	@Override
	public E get(String key) {
		if(!CacheProcessor.bloomFilter.mightContain(key)) {
			return null;
		}
		
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
	
	/**
	 * 互斥性。在任意时刻，只有一个客户端能持有锁。
	         不会发生死锁。即使有一个客户端在持有锁的期间崩溃而没有主动解锁，也能保证后续其他客户端能加锁。
	         具有容错性。只要大部分的Redis节点正常运行，客户端就可以加锁和解锁。
	         解铃还须系铃人。加锁和解锁必须是同一个客户端，客户端自己不能把别人加的锁给解了。
	 */
	public boolean lock(String businessId, E uuid, int expireTimeInSecond) {
		ValueOperations<String, E> ops = redisTemplate.opsForValue();
		Boolean isSuccess = ops.setIfAbsent(businessId, uuid);
		if(isSuccess) {
			redisTemplate.expire(businessId, expireTimeInSecond, TimeUnit.SECONDS);
			return true;
		}
		return false;
	}
	
	public boolean releaseLock(String businessId, E uuid) {
		ValueOperations<String, E> ops = redisTemplate.opsForValue();
		E e = ops.get(businessId);
		if(uuid.equals(e)) {
			redisTemplate.delete(businessId);
			return true;
		}
		return false;
	}
}
