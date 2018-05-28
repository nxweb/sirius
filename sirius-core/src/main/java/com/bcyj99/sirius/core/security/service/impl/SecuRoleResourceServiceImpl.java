package com.bcyj99.sirius.core.security.service.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;

import com.bcyj99.sirius.core.common.utils.SiriusCollectionUtils;
import com.bcyj99.sirius.core.security.dao.SecuResourceMapper;
import com.bcyj99.sirius.core.security.dao.SecuRoleResourceMapper;
import com.bcyj99.sirius.core.security.service.SecuRoleResourceService;
import com.bcyj99.sirius.core.security.vo.SecuResource;
import com.bcyj99.sirius.core.security.vo.SecuRole;
import com.bcyj99.sirius.core.security.vo.SecuRoleResource;

@Service
public class SecuRoleResourceServiceImpl implements SecuRoleResourceService {
	
	@Autowired
	private SecuRoleResourceMapper secuRoleResourceMapper;
	
	@Autowired
	private RedisTemplate<String,Serializable> redisTemplate;
	
	@Autowired
	private SecuResourceMapper secuResourceMapper;

	@Override
	public int addRoleResources(List<SecuRoleResource> checkedRoleResources,List<SecuRoleResource> allRoleResources) {
		Integer count = 0;
		
		if(!SiriusCollectionUtils.isEmpty(allRoleResources)){
			secuRoleResourceMapper.deleteByRoleIdResourceId(allRoleResources);
		}
		
		if(!SiriusCollectionUtils.isEmpty(checkedRoleResources)){
			count = secuRoleResourceMapper.insertRoleResources(checkedRoleResources);
		}
		
		 //1.查找所有资源
        List<SecuResource> resources = secuResourceMapper.selectAll();
        
        ValueOperations<String, Serializable> opsv = redisTemplate.opsForValue();
        //2.查找资源需要角色
        for (SecuResource resource : resources) {
        	HashSet<ConfigAttribute> roles = null;
            SecuResource secuResource = secuResourceMapper.selectRolesByResourceId(resource.getId());
            List<SecuRole> secuRoles = secuResource.getSecuRoles();
            if(secuRoles != null && !secuRoles.isEmpty()){
            	roles = new HashSet<ConfigAttribute>();
            	for(SecuRole sr : secuRoles){
            		roles.add(new SecurityConfig(sr.getRoleCode()));
            	}
            }
            //缓存数据
            opsv.set(resource.getResourceUrl(), roles);
        }
		
		return count;
	}

}
