package com.bcyj99.sirius.core.security.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SecuUser implements Serializable{
	private static final long serialVersionUID = -8494659655225474556L;

	private Long id;

    private String username;

    private String password;

    private String mobile;

    private String telephone;

    private String email;

    private Short isvalid;

    private Long createBy;

    private Date createDate;

    private Long updateBy;

    private Date updateDate;
    
    private List<SecuRole> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

	public List<SecuRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SecuRole> roles) {
		this.roles = roles;
	}
}