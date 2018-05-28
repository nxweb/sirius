package com.bcyj99.sirius.core.security.vo;

import java.util.List;

public class SecuRoleResources {
	private List<SecuRoleResource> checkedRoleResources;
	
	private List<SecuRoleResource> allRoleResources;

	public List<SecuRoleResource> getCheckedRoleResources() {
		return checkedRoleResources;
	}

	public void setCheckedRoleResources(List<SecuRoleResource> checkedRoleResources) {
		this.checkedRoleResources = checkedRoleResources;
	}

	public List<SecuRoleResource> getAllRoleResources() {
		return allRoleResources;
	}

	public void setAllRoleResources(List<SecuRoleResource> allRoleResources) {
		this.allRoleResources = allRoleResources;
	}
}
