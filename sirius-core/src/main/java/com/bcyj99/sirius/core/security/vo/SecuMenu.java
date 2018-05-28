package com.bcyj99.sirius.core.security.vo;

import java.util.List;

public class SecuMenu {
    private Long id;

    private String menuName;
    
    private Long parentId;

    private String menuIcon;
    
    private String iconCls;

    private Long resourceId;
    
    private String resourceName;

    private String resourceUrl;

    private Integer menuLevel;

    private Integer menuSeq;
    
    private List<SecuMenu> children;
    
    private SecuResource secuResource;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    public Integer getMenuSeq() {
        return menuSeq;
    }

    public void setMenuSeq(Integer menuSeq) {
        this.menuSeq = menuSeq;
    }

	public List<SecuMenu> getChildren() {
		return children;
	}

	public void setChildren(List<SecuMenu> children) {
		this.children = children;
	}

	public SecuResource getSecuResource() {
		return secuResource;
	}

	public void setSecuResource(SecuResource secuResource) {
		this.secuResource = secuResource;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
}