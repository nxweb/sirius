package com.bcyj99.sirius.core.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcyj99.sirius.core.security.dao.SecuMenuMapper;
import com.bcyj99.sirius.core.security.service.SecuMenuService;
import com.bcyj99.sirius.core.security.vo.SecuMenu;

@Service
public class SecuMenuServiceImpl implements SecuMenuService {
	
	@Autowired
	private SecuMenuMapper secuMenuMapper;

	@Override
	public List<SecuMenu> queryAllMenus() {
		return secuMenuMapper.selectByCondition(new SecuMenu());
	}

	@Override
	public void addMenu(SecuMenu menu) {
		secuMenuMapper.insert(menu);
	}

	@Override
	public Long addOrModifyMenu(SecuMenu menu) {
		if(menu==null){
			return -1L;
		}
		
		if(menu.getId()==null){
			secuMenuMapper.insert(menu);
		}else{
			secuMenuMapper.updateByPrimaryKeySelective(menu);
		}
		return menu.getId();
	}
	
	public void removeMenusByIds(List<SecuMenu> ids){
		secuMenuMapper.deleteMenusByIds(ids);
	}
}
