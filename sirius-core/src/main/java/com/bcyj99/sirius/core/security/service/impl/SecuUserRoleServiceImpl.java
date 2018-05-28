package com.bcyj99.sirius.core.security.service.impl;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcyj99.sirius.core.common.utils.SiriusCollectionUtils;
import com.bcyj99.sirius.core.security.dao.SecuRoleMapper;
import com.bcyj99.sirius.core.security.dao.SecuUserRoleMapper;
import com.bcyj99.sirius.core.security.service.SecuUserRoleService;
import com.bcyj99.sirius.core.security.vo.SecuRole;
import com.bcyj99.sirius.core.security.vo.SecuUserRole;

@Service
public class SecuUserRoleServiceImpl implements SecuUserRoleService {
	
	@Autowired
	private SecuUserRoleMapper secuUserRoleMapper;
	
	@Autowired
	private SecuRoleMapper secuRoleMapper;
	
	@Autowired
	private IdentityService identityService;

	@Override
	public int addUserRoles(List<SecuUserRole> checkUserRoles,List<SecuUserRole> allUserRoles) {
		Integer count = 0;
		SecuRole secuRole = null;
		Long roleId = null;
		if(!SiriusCollectionUtils.isEmpty(allUserRoles)){
			secuUserRoleMapper.deleteUserRoles(allUserRoles);
			for(SecuUserRole sr : allUserRoles){
				roleId = sr.getRoleId();
				if(roleId != null){
					secuRole = secuRoleMapper.selectByPrimaryKey(roleId);
					identityService.deleteMembership(sr.getUserId().toString(), secuRole.getRoleCode());
				}
			}
		}
		
		if(!SiriusCollectionUtils.isEmpty(checkUserRoles)){
			count = secuUserRoleMapper.insertUserRoles(checkUserRoles);
			for(SecuUserRole sr : checkUserRoles){
				roleId = sr.getRoleId();
				if(roleId != null){
					secuRole = secuRoleMapper.selectByPrimaryKey(roleId);
					identityService.createMembership(sr.getUserId().toString(), secuRole.getRoleCode());
				}
			}
		}
		return count;
	}

}
