package com.bcyj99.sirius.core.authentication.handler;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class MySimpleUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	public MySimpleUrlAuthenticationSuccessHandler(String defaultTargetUrl) {
		super(defaultTargetUrl);
	}

}
