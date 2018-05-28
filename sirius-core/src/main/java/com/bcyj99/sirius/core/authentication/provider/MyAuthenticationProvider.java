package com.bcyj99.sirius.core.authentication.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import com.bcyj99.sirius.core.authentication.access.MySpringSecurityMessageSource;

public class MyAuthenticationProvider implements AuthenticationProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(MyAuthenticationProvider.class);
	
	private UserDetailsService userDetailsService;
	
	private PasswordEncoder passwordEncoder;
	
	protected boolean hideUserNotFoundExceptions = true;
	
	protected MessageSourceAccessor messages = MySpringSecurityMessageSource.getAccessor();
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
		
		UserDetails user = null;
		try {
			user = retrieveUser(username,
					(UsernamePasswordAuthenticationToken) authentication);
		}
		catch (UsernameNotFoundException notFound) {
			logger.debug("User '" + username + "' not found");

			if (hideUserNotFoundExceptions) {
				throw new BadCredentialsException("Bad credentials");
			}
			else {
				throw notFound;
			}
		}

		Assert.notNull(user,
				"retrieveUser returned null - a violation of the interface contract");
		
		additionalAuthenticationChecks(user,
					(UsernamePasswordAuthenticationToken) authentication);
		
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
				user, authentication.getCredentials(),
				user.getAuthorities());
		result.setDetails(authentication.getDetails());

		return result;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
	
	protected final UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		UserDetails loadedUser;

		try {
			loadedUser = this.getUserDetailsService().loadUserByUsername(username);
		}
		catch (UsernameNotFoundException notFound) {
			if (authentication.getCredentials() != null) {
				String rawPassword = authentication.getCredentials().toString();
				String encodedPassword = passwordEncoder.encode(rawPassword);
				passwordEncoder.matches(rawPassword, encodedPassword);
			}
			throw notFound;
		}
		catch (Exception repositoryProblem) {
			throw new InternalAuthenticationServiceException(
					repositoryProblem.getMessage(), repositoryProblem);
		}

		if (loadedUser == null) {
			throw new InternalAuthenticationServiceException(
					"UserDetailsService returned null, which is an interface contract violation");
		}
		return loadedUser;
	}
	
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		if (authentication.getCredentials() == null) {
			logger.debug("Authentication failed: no credentials provided");

			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.passwordNotEmpty",
					"Bad credentials"));
		}

		String presentedPassword = authentication.getCredentials().toString();
		
		if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
			logger.debug("Authentication failed: password does not match stored value");

			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.usernamePasswordError",
					"Bad credentials"));
		}
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public boolean isHideUserNotFoundExceptions() {
		return hideUserNotFoundExceptions;
	}

	public void setHideUserNotFoundExceptions(boolean hideUserNotFoundExceptions) {
		this.hideUserNotFoundExceptions = hideUserNotFoundExceptions;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

}
