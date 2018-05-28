package com.bcyj99.sirius.core.authentication.access;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.bcyj99.sirius.core.common.utils.SiriusCollectionUtils;
import com.bcyj99.sirius.core.common.utils.SiriusStringUtils;
import com.bcyj99.sirius.core.security.dao.SecuRoleMapper;
import com.bcyj99.sirius.core.security.vo.SecuResource;
import com.bcyj99.sirius.core.security.vo.SecuRole;

public class MyPermissionEvaluator implements PermissionEvaluator {
	
	@Autowired
	private SecuRoleMapper secuRoleMapper;

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		StringBuilder sb = new StringBuilder();
		sb.append(targetDomainObject.toString());
		sb.append("-");
		sb.append(permission.toString());
		if(SiriusStringUtils.isBlank(sb.toString())){
			return false;
		}
		for (GrantedAuthority authority : authorities) {
			String auth = authority.getAuthority();
			SecuRole role = secuRoleMapper.selectByRoleCode(auth.substring(auth.indexOf("_")+1));
			List<SecuResource> resources =  role.getResources();
			if(!SiriusCollectionUtils.isEmpty(resources)){
				for(SecuResource r : resources){
					if ((sb.toString()).equals(r.getResourceCode())) {
			            return true;
			        }
				}
			}
	    }
		return false;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		// TODO Auto-generated method stub
		return false;
	}

}
