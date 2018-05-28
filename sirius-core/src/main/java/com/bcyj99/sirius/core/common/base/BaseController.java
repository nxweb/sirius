package com.bcyj99.sirius.core.common.base;

import org.springframework.security.core.context.SecurityContextHolder;

import com.bcyj99.sirius.core.authentication.access.MyUser;

public class BaseController {
	public MyUser getCurrentUser() {
		MyUser user = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user;
	}
}
