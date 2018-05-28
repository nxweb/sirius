package com.bcyj99.sirius.core.authentication.handler;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class MySimpleUrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	public MySimpleUrlAuthenticationFailureHandler(String defaultFailureUrl) {
		super(defaultFailureUrl);
	}

}
