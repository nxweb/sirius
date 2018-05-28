package com.bcyj99.sirius.core.security.vo;

import java.util.List;

public class SecuUserRoles {
	private List<SecuUserRole> checkedUserRoles;
	
    private List<SecuUserRole> allUserRoles;

	public List<SecuUserRole> getCheckedUserRoles() {
		return checkedUserRoles;
	}

	public void setCheckedUserRoles(List<SecuUserRole> checkedUserRoles) {
		this.checkedUserRoles = checkedUserRoles;
	}

	public List<SecuUserRole> getAllUserRoles() {
		return allUserRoles;
	}

	public void setAllUserRoles(List<SecuUserRole> allUserRoles) {
		this.allUserRoles = allUserRoles;
	}
}
