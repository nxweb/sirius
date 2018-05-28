package com.bcyj99.sirius.console.security.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bcyj99.sirius.core.common.utils.SiriusCollectionUtils;
import com.bcyj99.sirius.core.common.vo.ResultVo;
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
	
	@RequestMapping("/menusPage")
	public String menusPage(){
		return "/security/menu";
	}
	
	@RequestMapping("/queryAllMenusTg")
	@ResponseBody
	public List<SecuMenu> queryAllMenusTg(){
		List<SecuMenu> menus =  secuMenuService.queryAllMenus();
		List<SecuMenu> menusResult = new ArrayList<SecuMenu>();
		loadTreeMenu(menus,menusResult,0L);
		return menusResult;
	}
	
	@PostMapping(value="/addMenu",name="新增菜单")
	@ResponseBody
	public ResultVo<String> addMenu(SecuMenu menu){
		try {
			logger.info("新增菜单-开始");
			Long parentId = menu.getParentId();
			if(null == parentId){
				menu.setParentId(0L);
			}
			String menuString = JSON.toJSONString(menu);
			logger.info("新增菜单-menuString:{}",menuString);
			secuMenuService.addMenu(menu);
			logger.info("新增菜单-成功");
			return new ResultVo<String>(200,"ok",null);
		} catch (Exception e) {
			logger.error("新增菜单-失败", e);
			return new ResultVo<String>(400,"error",null);
		}
	}
	
	@PostMapping(value="/addOrModifyMenu",name="新增或修改菜单")
	@ResponseBody
	public ResultVo<String> addOrModifyMenu(SecuMenu menu){
		try {
			logger.info("新增或修改菜单-开始");
			Long parentId = menu.getParentId();
			if(null == parentId){
				menu.setParentId(0L);
			}
			String menuString = JSON.toJSONString(menu);
			logger.info("新增或修改菜单-menuString:{}",menuString);
			secuMenuService.addOrModifyMenu(menu);
			logger.info("新增或修改菜单-成功");
			return new ResultVo<String>(200,"ok",null);
		} catch (Exception e) {
			logger.error("新增或修改菜单-失败", e);
			return new ResultVo<String>(400,"error",null);
		}
	}
	
	@RequestMapping("/queryAllMenus")
	@ResponseBody
	public List<TreeNode> queryAllMenus(){
		List<SecuMenu> menus =  secuMenuService.queryAllMenus();
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		loadTreeNodes(menus,treeNodes,0L);
		return treeNodes;
	}
	
	@PostMapping(value="/removeMenusByParentId",name="删除菜单及子菜单")
	@ResponseBody
	public ResultVo<String> removeMenusByParentId(Long parentId){
		try {
			logger.info("新增或修改菜单-开始:parentId="+parentId);
			List<SecuMenu> menus =  secuMenuService.queryAllMenus();
			List<SecuMenu> menusResult = new ArrayList<SecuMenu>();
			
			loadSelfAndChildrenMenu(menus, menusResult, parentId);
			
			secuMenuService.removeMenusByIds(menusResult);
			logger.info("新增或修改菜单-成功");
			return new ResultVo<String>(200,"ok",null);
		} catch (Exception e) {
			logger.error("新增或修改菜单-失败", e);
			return new ResultVo<String>(400,"error",null);
		}
	}
	
	private void loadSelfAndChildrenMenu(List<SecuMenu> menus,List<SecuMenu> menusResult,Long parentId){
		if(!SiriusCollectionUtils.isEmpty(menus)){
			for(SecuMenu menu : menus){
				if(menu.getId().equals(parentId)){
					menusResult.add(menu);
				}
				if(menu.getParentId().equals(parentId)){
					menusResult.add(menu);
					loadTreeMenu(menus,menusResult,menu.getId());
				}
			}
		}
	}
	
	private void loadTreeMenu(List<SecuMenu> menus,List<SecuMenu> menusResult,Long parentId){
		if(!SiriusCollectionUtils.isEmpty(menus)){
			for(SecuMenu menu : menus){
				if(menu.getParentId().equals(parentId)){
					if(menu.getSecuResource() != null){
						menu.setResourceName(menu.getSecuResource().getResourceName());
						menu.setResourceUrl(menu.getSecuResource().getResourceUrl());
					}
					menu.setChildren(new ArrayList<SecuMenu>());
					menu.setIconCls(menu.getMenuIcon());
					menusResult.add(menu);
					loadTreeMenu(menus,menu.getChildren(),menu.getId());
				}
			}
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
