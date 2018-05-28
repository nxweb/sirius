package com.bcyj99.sirius.core.authentication.access;

import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class MyLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

	public MyLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
	}

}
