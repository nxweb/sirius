package com.bcyj99.sirius.core.security.service;

import com.bcyj99.sirius.core.common.page.PagedResultVo;
import com.bcyj99.sirius.core.security.vo.SecuUser;

public interface SecuUserService {
	SecuUser queryUserById(Long id);
	
	int addUser(SecuUser user);
	
	int addOrModifyUser(SecuUser user);
	
	PagedResultVo<SecuUser> queryPagedSecuUsers(
			SecuUser user,Integer pageNo,Integer pageSize);
}
