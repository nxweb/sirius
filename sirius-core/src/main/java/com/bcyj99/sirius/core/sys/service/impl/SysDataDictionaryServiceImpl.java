package com.bcyj99.sirius.core.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcyj99.sirius.core.common.page.PagedResultVo;
import com.bcyj99.sirius.core.sys.dao.SysDataDictionaryMapper;
import com.bcyj99.sirius.core.sys.service.CacheService;
import com.bcyj99.sirius.core.sys.service.SysDataDictionaryService;
import com.bcyj99.sirius.core.sys.vo.SysDataDictionary;
import com.bcyj99.sirius.core.sys.vo.SysDataDictionaryVo;

@Service
public class SysDataDictionaryServiceImpl implements SysDataDictionaryService {
	
	@Autowired
	private SysDataDictionaryMapper sysDataDictionaryMapper;
	
	@Autowired
	private CacheService<SysDataDictionaryVo> cacheService;

	@Override
	public void addOrModifySysDataDictionary(SysDataDictionary sysDataDictionary) {
		Long id = sysDataDictionary.getId();
		if(id==null){
			sysDataDictionaryMapper.insertSelective(sysDataDictionary);
		}else{
			sysDataDictionaryMapper.updateByPrimaryKeySelective(sysDataDictionary);
		}
		
		SysDataDictionary condition = new SysDataDictionary();
		condition.setDicName(sysDataDictionary.getDicName());
		List<SysDataDictionaryVo> list = sysDataDictionaryMapper.selectDictionaryVos(condition);
		cacheService.setList(sysDataDictionary.getDicName(), list);
	}

	@Override
	public void removeSysDataDictionary(Long id,String dictionaryKey) {
		sysDataDictionaryMapper.deleteByPrimaryKey(id);
		
		SysDataDictionary condition = new SysDataDictionary();
		condition.setDicName(dictionaryKey);
		List<SysDataDictionaryVo> list = sysDataDictionaryMapper.selectDictionaryVos(condition);
		cacheService.setList(dictionaryKey, list);
	}

	@Override
	public PagedResultVo<SysDataDictionary> queryPagedSysDataDictionarys(SysDataDictionary sysDataDictionary,
			Integer pageNo, Integer pageSize) {
		
		List<SysDataDictionary> rows = sysDataDictionaryMapper.selectPagedDictionarys(sysDataDictionary, (pageNo-1)*pageSize, pageSize);
		Integer total = sysDataDictionaryMapper.selectPagedDictionarysCount(sysDataDictionary);
		
		return new PagedResultVo<SysDataDictionary>(total.longValue(), rows);
	}

	@Override
	public List<SysDataDictionaryVo> getCacheSysDataDictionary(String dictionaryKey) {
		return cacheService.getList(dictionaryKey);
	}

}
