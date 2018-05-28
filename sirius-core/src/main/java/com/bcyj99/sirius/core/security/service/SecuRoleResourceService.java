package com.bcyj99.sirius.core.security.service;

import java.util.List;

import com.bcyj99.sirius.core.security.vo.SecuRoleResource;

public interface SecuRoleResourceService {
	int addRoleResources(List<SecuRoleResource> checkedRoleResources,List<SecuRoleResource> allRoleResources);
}
