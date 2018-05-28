package com.bcyj99.sirius.core.security.vo;

import java.util.List;

public class SecuResource {
    private Long id;

    private String resourceName;
    
    private String resourceCode;

    private String resourceType;

    private String resourceUrl;

    private String resourceDesc;
    
    private List<SecuRole> secuRoles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getResourceDesc() {
        return resourceDesc;
    }

    public void setResourceDesc(String resourceDesc) {
        this.resourceDesc = resourceDesc;
    }

	public List<SecuRole> getSecuRoles() {
		return secuRoles;
	}

	public void setSecuRoles(List<SecuRole> secuRoles) {
		this.secuRoles = secuRoles;
	}

	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}
}