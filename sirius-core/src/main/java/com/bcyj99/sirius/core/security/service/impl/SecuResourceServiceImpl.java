package com.bcyj99.sirius.core.security.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.bcyj99.sirius.core.common.page.PagedResultVo;
import com.bcyj99.sirius.core.security.dao.SecuResourceMapper;
import com.bcyj99.sirius.core.security.dao.SecuRoleResourceMapper;
import com.bcyj99.sirius.core.security.service.SecuResourceService;
import com.bcyj99.sirius.core.security.vo.SecuResource;

@Service
public class SecuResourceServiceImpl implements SecuResourceService {
	
	@Autowired
	private SecuResourceMapper secuResourceMapper;
	
	@Autowired
	private SecuRoleResourceMapper secuRoleResourceMapper;
	
	@Autowired
	private RedisTemplate<String,Serializable> redisTemplate;

	@Override
	public void addResource(SecuResource secuResource) {
		secuResourceMapper.insert(secuResource);
	}
	
	public void addOrModifyResource(SecuResource secuResource) {
		if(secuResource.getId()==null){
			secuResourceMapper.insert(secuResource);	
		}else{
			secuResourceMapper.updateByPrimaryKeySelective(secuResource);
		}
	}

	@Override
	public PagedResultVo<SecuResource> queryPagedSecuResources(SecuResource secuResource, Integer pageNo,
			Integer pageSize) {
		Integer total = secuResourceMapper.selectPagedResourcesCount(secuResource);
		List<SecuResource> rows = secuResourceMapper.selectPagedResources(secuResource, (pageNo-1)*pageSize, pageSize);
		
		return new PagedResultVo<SecuResource>(total.longValue(),rows);
	}

	@Override
	public SecuResource querySecuResourceById(Long id) {
		return secuResourceMapper.selectByPrimaryKey(id);
	}

	@Override
	public void removeResourceById(Long resourceId) {
		SecuResource secuResource = secuResourceMapper.selectByPrimaryKey(resourceId);
		secuResourceMapper.deleteByPrimaryKey(resourceId);
		
		secuRoleResourceMapper.deleteByResourceId(resourceId);
		
		if(secuResource != null){
		    redisTemplate.delete(secuResource.getResourceUrl());
		}
	}

}
