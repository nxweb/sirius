package com.bcyj99.sirius.core.sys.processor;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.bcyj99.sirius.core.common.utils.SiriusCollectionUtils;
import com.bcyj99.sirius.core.sys.dao.SysParamMapper;
import com.bcyj99.sirius.core.sys.vo.SysParam;

public class LoadSysParamCacheThread implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(LoadSysParamCacheThread.class);
	
	private SysParamMapper sysParamMapper;
	
	private RedisTemplate<String,Serializable> redisTemplate;

	public LoadSysParamCacheThread() {
		super();
	}

	public LoadSysParamCacheThread(SysParamMapper sysParamMapper,RedisTemplate<String,Serializable> redisTemplate) {
		super();
		this.sysParamMapper = sysParamMapper;
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void run() {
		logger.info("加载缓存数据-系统参数-开始...");
		ValueOperations<String, Serializable> ops = redisTemplate.opsForValue();
		
		List<SysParam> allSysParams = sysParamMapper.selectAll();
		if(!SiriusCollectionUtils.isEmpty(allSysParams)){
			logger.info("加载缓存数据-系统参数-size:{}",allSysParams.size());
			for(SysParam sp : allSysParams){
				ops.set(sp.getParamKey(), sp.getParamValue());
			}
		}
		logger.info("加载缓存数据-系统参数-成功.");
	}

}
