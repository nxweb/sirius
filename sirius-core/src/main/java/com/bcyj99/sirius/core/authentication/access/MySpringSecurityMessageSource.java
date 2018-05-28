package com.bcyj99.sirius.core.authentication.access;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

public class MySpringSecurityMessageSource extends ResourceBundleMessageSource {
	// ~ Constructors
	// ===================================================================================================

	public MySpringSecurityMessageSource() {
		setBasename("properties/messages");
	}

	// ~ Methods
	// ========================================================================================================

	public static MessageSourceAccessor getAccessor() {
		return new MessageSourceAccessor(new MySpringSecurityMessageSource());
	}
}
