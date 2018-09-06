package com.bcyj99.sirius.core.sys.processor;

import java.io.Serializable;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.bcyj99.sirius.core.sys.dao.SysParamMapper;
import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

public class CacheProcessor implements ApplicationListener<ContextRefreshedEvent> {
	
	private static final Logger logger = LoggerFactory.getLogger(CacheProcessor.class);
	
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	public static BloomFilter<String> bloomFilter;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ApplicationContext ac = event.getApplicationContext();
		logger.info(ac.toString());
		if(ac.getParent()==null){
			RedisTemplate<String,Serializable> redisTemplate = (RedisTemplate<String, Serializable>) ac.getBean("redisTemplate");
			SysParamMapper sysParamMapper = (SysParamMapper) ac.getBean("sysParamMapper");
			logger.info("加载缓存数据-系统参数-启动加载数据的线程.");
			threadPoolTaskExecutor.execute(new LoadSysParamCacheThread(sysParamMapper,redisTemplate));
			
			logger.info("加载缓存数据-数据字典-启动加载数据的线程.");
			threadPoolTaskExecutor.execute(new LoadSysDataDictionaryCacheThread());
			
			bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), 100000);
			CacheProcessor.bloomFilter.put("aa");
			CacheProcessor.bloomFilter.put("bb");
			CacheProcessor.bloomFilter.put("fahaiAuthCompanyDetailUrl");
		}
	}
	
}
