package com.bcyj99.sirius.web.security.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcyj99.sirius.core.common.vo.TreeNode;
import com.bcyj99.sirius.core.security.service.SecuMenuService;
import com.bcyj99.sirius.core.security.service.SecuResourceService;
import com.bcyj99.sirius.core.security.service.SecuRoleService;
import com.bcyj99.sirius.core.security.vo.SecuMenu;
import com.bcyj99.sirius.core.security.vo.SecuResource;
import com.bcyj99.sirius.core.security.vo.SecuRole;

@Controller
@RequestMapping("/secuMenu")
public class SecuMenuController {
	
	private static final Logger logger = LoggerFactory.getLogger(SecuMenuController.class);
	
	@Autowired
	private SecuMenuService secuMenuService;
	
	@Autowired
	private SecuRoleService secuRoleService;
	
	@Autowired
	private SecuResourceService secuResourceService;
	
	@RequestMapping(value="/queryAllMenus",name="加载菜单")
	@ResponseBody
	public List<TreeNode> queryAllMenus(){
		try {
			logger.info("加载菜单-开始....");
			List<SecuMenu> menus =  secuMenuService.queryAllMenus();
			List<TreeNode> treeNodes = new ArrayList<TreeNode>();
			loadTreeNodes(menus,treeNodes,0L);
			logger.info("加载菜单-成功.");
			return treeNodes;
		} catch (Exception e) {
			logger.error("加载菜单-失败.", e);
			return new ArrayList<TreeNode>();
		}
	}
	
	private void loadTreeNodes(List<SecuMenu> menus,List<TreeNode> treeNodes,Long parentId){
		if(menus != null && !menus.isEmpty()){
			TreeNode node = null;
			for(SecuMenu menu : menus){
				if(menu.getParentId().equals(parentId)){
					node = new TreeNode();
					node.setId(menu.getId().intValue());
					node.setText(menu.getMenuName());
					node.setIconCls(menu.getMenuIcon());
					node.setChildren(new ArrayList<TreeNode>());
					
					Long resourceId = menu.getResourceId();
					
					boolean hasMenu = false;
					Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
					Iterator<GrantedAuthority> it = authorities.iterator();
					loop1:while(it.hasNext()){
						GrantedAuthority ga = it.next();
						String authority = ga.getAuthority();
						String roleCode = authority.substring(authority.indexOf("_")+1);
						SecuRole secuRole = secuRoleService.queryRoleByRoleCode(roleCode);
						List<SecuResource> listResources = secuRole.getResources();
						for(SecuResource sr : listResources){
							if(sr.getId().equals(resourceId)){
								hasMenu = true;
								break loop1;
							}
						}
					}
					
					if(hasMenu){
						SecuResource secuResource = null;
						if(resourceId != null){
							secuResource = secuResourceService.querySecuResourceById(resourceId);
						}
						if(secuResource != null){
							Map<String, Object> attributes = new HashMap<String, Object>();
							attributes.put("url", secuResource.getResourceUrl());
							node.setAttributes(attributes);
						}
						
						treeNodes.add(node);
						loadTreeNodes(menus,node.getChildren(),node.getId().longValue());
					}
				}
			}
		}
	}
}
