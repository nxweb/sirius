package com.bcyj99.sirius.core.security.service.impl;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bcyj99.sirius.core.common.page.PagedResultVo;
import com.bcyj99.sirius.core.common.utils.SiriusStringUtils;
import com.bcyj99.sirius.core.security.dao.SecuUserMapper;
import com.bcyj99.sirius.core.security.service.SecuUserService;
import com.bcyj99.sirius.core.security.vo.SecuUser;

@Service
public class SecuUserServiceImpl implements SecuUserService {
	
	@Autowired
	private SecuUserMapper secuUserMapper;
	
	@Autowired
	private IdentityService identityService;

	@Override
	public int addUser(SecuUser user) {
		String rawPassword = user.getPassword();
		if(!SiriusStringUtils.isEmpty(rawPassword)){
			BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = bcryptPasswordEncoder.encode(rawPassword);
			user.setPassword(encodedPassword);
		}
		secuUserMapper.insert(user);
		
		User actUser = new UserEntity(user.getId().toString());
		actUser.setEmail(user.getEmail());
		actUser.setLastName(user.getUsername());
		identityService.saveUser(actUser);
		
		return user.getId().intValue();
	}

	@Override
	public PagedResultVo<SecuUser> queryPagedSecuUsers(SecuUser user, Integer pageNo, Integer pageSize) {
		List<SecuUser> rows = secuUserMapper.selectPagedUsers(user, (pageNo-1)*pageSize, pageSize);
		Integer total = secuUserMapper.selectPagedUsersCount(user);
		
		PagedResultVo<SecuUser> result = new PagedResultVo<SecuUser>(total.longValue(), rows);
		return result;
	}

	@Override
	public SecuUser queryUserById(Long id) {
		return secuUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public int addOrModifyUser(SecuUser user) {
		if(user.getId()==null){
			String rawPassword = user.getPassword();
			if(!SiriusStringUtils.isEmpty(rawPassword)){
				BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
				String encodedPassword = bcryptPasswordEncoder.encode(rawPassword);
				user.setPassword(encodedPassword);
			}
			secuUserMapper.insertSelective(user);
			
			User actUser = new UserEntity(user.getId().toString());
			actUser.setEmail(user.getEmail());
			actUser.setLastName(user.getUsername());
			identityService.saveUser(actUser);
			
			return user.getId().intValue();
		}else{
			user.setPassword(null);
			secuUserMapper.updateByPrimaryKeySelective(user);
			
			User actUser = new UserEntity(user.getId().toString());
			actUser.setEmail(user.getEmail());
			actUser.setLastName(user.getUsername());
			identityService.saveUser(actUser);
			
			return user.getId().intValue();
		}
	}

}
