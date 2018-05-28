package com.bcyj99.sirius.core.security.service.impl;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcyj99.sirius.core.common.page.PagedResultVo;
import com.bcyj99.sirius.core.security.dao.SecuRoleMapper;
import com.bcyj99.sirius.core.security.service.SecuRoleService;
import com.bcyj99.sirius.core.security.vo.SecuRole;

@Service
public class SecuRoleServiceImpl implements SecuRoleService {
	
	@Autowired
	private SecuRoleMapper secuRoleMapper;
	
	@Autowired
	private IdentityService identityService;

	@Override
	public int addRole(SecuRole role) {
		
		Group group = new GroupEntity(role.getRoleCode());
		group.setName(role.getRoleName());
		identityService.saveGroup(group);
		
		return secuRoleMapper.insert(role);
	}

	@Override
	public PagedResultVo<SecuRole> queryPagedSecuRoles(SecuRole role, Integer pageNo, Integer pageSize) {
		Long total = secuRoleMapper.selectPagedRolesCount(role).longValue();
		List<SecuRole> rows = secuRoleMapper.selectPagedRoles(role, (pageNo-1)*pageSize, pageSize);
		
		return new PagedResultVo<SecuRole>(total,rows);
	}

	@Override
	public SecuRole queryRoleById(Long id) {
		return secuRoleMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public SecuRole queryRoleByRoleCode(String roleCode) {
		return secuRoleMapper.selectByRoleCode(roleCode);
	}

	@Override
	public int removeRoleById(Long id) {
		return secuRoleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int addOrModifyRole(SecuRole role) {
		
		Group group = identityService.createGroupQuery().groupId(role.getRoleCode()).singleResult();
		if(group == null){
			group = new GroupEntity(role.getRoleCode());
			group.setName(role.getRoleName());
			identityService.saveGroup(group);
		}else{
			group.setId(role.getRoleCode());
			group.setName(role.getRoleName());
			identityService.saveGroup(group);
		}
		
		if(role.getId()==null){
			secuRoleMapper.insertSelective(role);
		}else{
			secuRoleMapper.updateByPrimaryKeySelective(role);
		}
		return role.getId().intValue();
	}

}
