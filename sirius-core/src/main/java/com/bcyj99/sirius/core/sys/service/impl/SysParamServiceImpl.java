package com.bcyj99.sirius.core.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcyj99.sirius.core.common.page.PagedResultVo;
import com.bcyj99.sirius.core.sys.dao.SysParamMapper;
import com.bcyj99.sirius.core.sys.service.CacheService;
import com.bcyj99.sirius.core.sys.service.SysParamService;
import com.bcyj99.sirius.core.sys.vo.SysParam;

@Service
public class SysParamServiceImpl implements SysParamService {
	@Autowired
	private SysParamMapper sysParamMapper;
	
	@Autowired
	private CacheService<String> cacheService;

	@Override
	public void addOrModifySysParam(SysParam sysParam) {
		Long id = sysParam.getId();
		if(id == null){
			sysParamMapper.insertSelective(sysParam);
			cacheService.set(sysParam.getParamKey(), sysParam.getParamValue());
		}else{
			sysParamMapper.updateByPrimaryKeySelective(sysParam);
			cacheService.set(sysParam.getParamKey(), sysParam.getParamValue());
		}
	}
	
	@Override
	public void removeSysParam(Long id,String paramKey) {
		sysParamMapper.deleteByPrimaryKey(id);
		cacheService.delete(paramKey);
	}

	@Override
	public PagedResultVo<SysParam> queryPagedSysParams(SysParam sysParam, Integer pageNo, Integer pageSize) {
		Integer total = sysParamMapper.selectPagedSysParamsCount(sysParam);
		List<SysParam> rows = sysParamMapper.selectPagedSysParams(sysParam, (pageNo-1)*pageSize, pageSize);
		
		return new PagedResultVo<SysParam>(total.longValue(), rows);
	}

	@Override
	public String getCacheSysParam(String paramKey) {
		return cacheService.get(paramKey);
	}
}
