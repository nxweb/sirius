package com.bcyj99.sirius.core.authentication.access;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class MyUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {
	private static final long serialVersionUID = 5918905233283196892L;
	
	private Long id;

	public MyUsernamePasswordAuthenticationToken(Object principal, Object credentials, Long id) {
		super(principal, credentials);
		this.id=id;
	}
	
	public MyUsernamePasswordAuthenticationToken(Object principal, Object credentials,Long id,
			Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
		this.id=id;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
