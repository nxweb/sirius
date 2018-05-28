package com.bcyj99.sirius.core.authentication.access;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.bcyj99.sirius.core.security.vo.SecuUser;

public class MyUser extends User {
	private static final long serialVersionUID = -852640466196882845L;
	
	private Long id;
    private String mobile;
    private String telephone;
    private String email;
    private Short isvalid;
    private Long createBy;
    private Date createDate;
    private Long updateBy;
    private Date updateDate;

	public MyUser(String username, String password,SecuUser secuUser, Collection<? extends GrantedAuthority> authorities) {
		this(username, password, secuUser, 
		     true, true, true, true, authorities);
	}
	
	public MyUser(String username, String password,SecuUser secuUser,
			boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.id = secuUser.getId();
		this.mobile = secuUser.getMobile();
		this.telephone = secuUser.getTelephone();
		this.email = secuUser.getEmail();
		this.isvalid = secuUser.getIsvalid();
		this.createBy = secuUser.getCreateBy();
		this.createDate = secuUser.getCreateDate();
		this.updateBy = secuUser.getUpdateBy();
		this.updateDate = secuUser.getUpdateDate();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Short getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(Short isvalid) {
		this.isvalid = isvalid;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
