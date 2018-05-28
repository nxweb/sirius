package com.bcyj99.sirius.core.sys.processor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.bcyj99.sirius.core.common.utils.SiriusCollectionUtils;
import com.bcyj99.sirius.core.common.utils.SpringApplicationContextHolder;
import com.bcyj99.sirius.core.sys.dao.SysDataDictionaryMapper;
import com.bcyj99.sirius.core.sys.vo.SysDataDictionary;
import com.bcyj99.sirius.core.sys.vo.SysDataDictionaryVo;

public class LoadSysDataDictionaryCacheThread implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(LoadSysDataDictionaryCacheThread.class);
	
	private SysDataDictionaryMapper sysDataDictionaryMapper;
	
	private RedisTemplate<String,SysDataDictionaryVo> redisTemplate;
	
	
	public LoadSysDataDictionaryCacheThread() {
		super();
		sysDataDictionaryMapper = (SysDataDictionaryMapper) SpringApplicationContextHolder.getSpringBean("sysDataDictionaryMapper");
		redisTemplate = (RedisTemplate<String,SysDataDictionaryVo>) SpringApplicationContextHolder.getSpringBean("redisTemplate");
	}


	@Override
	public void run() {
		logger.info("加载缓存数据-数据字典-开始...");
		SysDataDictionary record = new SysDataDictionary();
		List<SysDataDictionaryVo> list = sysDataDictionaryMapper.selectDictionaryVos(record);
		if(!SiriusCollectionUtils.isEmpty(list)){
			logger.info("加载缓存数据-数据字典-size:{}",list.size());
			ListOperations<String, SysDataDictionaryVo> listOperations =redisTemplate.opsForList();
			
			Set<String> keys = new HashSet<String>();
			for(SysDataDictionaryVo sv : list){
				keys.add(sv.getDicName());
			}
			
			redisTemplate.delete(keys);
			
			for(SysDataDictionaryVo sv : list){
				listOperations.rightPush(sv.getDicName(), sv);
			}
		}
		logger.info("加载缓存数据-数据字典-成功.");
	}

}
