package com.bcyj99.sirius.core.security.service;

import com.bcyj99.sirius.core.common.page.PagedResultVo;
import com.bcyj99.sirius.core.security.vo.SecuRole;

public interface SecuRoleService {
	SecuRole queryRoleById(Long id);
	
	SecuRole queryRoleByRoleCode(String roleCode);
	
    int addRole(SecuRole role);
    
    int addOrModifyRole(SecuRole role);
	
	PagedResultVo<SecuRole> queryPagedSecuRoles(
			SecuRole role,Integer pageNo,Integer pageSize);
	
	int removeRoleById(Long id);
}
