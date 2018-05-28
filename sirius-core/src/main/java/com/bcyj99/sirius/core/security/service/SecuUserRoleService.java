package com.bcyj99.sirius.core.security.service;

import java.util.List;

import com.bcyj99.sirius.core.security.vo.SecuUserRole;

public interface SecuUserRoleService {
	int addUserRoles(List<SecuUserRole> checkUserRoles,List<SecuUserRole> allUserRoles);
}
