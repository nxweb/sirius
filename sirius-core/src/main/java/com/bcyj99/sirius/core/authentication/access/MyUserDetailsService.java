package com.bcyj99.sirius.core.authentication.access;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bcyj99.sirius.core.security.dao.SecuUserMapper;
import com.bcyj99.sirius.core.security.vo.SecuRole;
import com.bcyj99.sirius.core.security.vo.SecuUser;

public class MyUserDetailsService implements UserDetailsService {
	
	private static final Short isValid = 1;
	
	@Autowired
	private SecuUserMapper secuUserMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SecuUser secuUser = secuUserMapper.selectByUsername(username);
		List<SecuRole> roles = secuUser.getRoles();
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if(roles != null && !roles.isEmpty()){
			for(SecuRole secuRole:roles){
				authorities.add(new SimpleGrantedAuthority("ROLE_"+secuRole.getRoleCode()));
			}
		}
		
		boolean enabled = false;
		if(MyUserDetailsService.isValid.equals(secuUser.getIsvalid())){
			enabled = true;
		}
		
		UserDetails user = new MyUser(secuUser.getUsername(), secuUser.getPassword(),secuUser, enabled, true, true, true, authorities);
		return user;
	}

}
