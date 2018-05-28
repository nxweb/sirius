package com.bcyj99.sirius.core.security.service;

import java.util.List;

import com.bcyj99.sirius.core.security.vo.SecuMenu;

public interface SecuMenuService {
	List<SecuMenu> queryAllMenus();
	
	void addMenu(SecuMenu menu);
	
	Long addOrModifyMenu(SecuMenu menu);
	
	void removeMenusByIds(List<SecuMenu> ids);
}
