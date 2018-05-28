package com.bcyj99.sirius.console.security.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bcyj99.sirius.core.common.page.PagedParamVo;
import com.bcyj99.sirius.core.common.page.PagedResultVo;
import com.bcyj99.sirius.core.common.utils.SiriusStringUtils;
import com.bcyj99.sirius.core.common.vo.ResultVo;
import com.bcyj99.sirius.core.security.service.SecuRoleResourceService;
import com.bcyj99.sirius.core.security.service.SecuRoleService;
import com.bcyj99.sirius.core.security.vo.SecuRole;
import com.bcyj99.sirius.core.security.vo.SecuRoleResource;
import com.bcyj99.sirius.core.security.vo.SecuRoleResources;

@Controller
@RequestMapping("/secuRole")
public class SecuRoleController {
	
    private static final Logger logger = LoggerFactory.getLogger(SecuUserController.class);
	
	@Autowired
	private SecuRoleService secuRoleService;
	
	@Autowired
	private SecuRoleResourceService secuRoleResourceService;
	
	@RequestMapping(value="/rolePage",name="角色-主页")
    public String rolePage(){
		return "/security/role";
	}
	
	@PostMapping(value="/queryRoleById",name="根据id查询角色")
	@ResponseBody
    public ResultVo<SecuRole> queryRoleById(Long id){
		try {
			logger.info("查询角色-开始:id={}",id);
			SecuRole role = secuRoleService.queryRoleById(id);
			logger.info("查询角色-成功");
			return new ResultVo<SecuRole>(200,"ok",role);
		} catch (Exception e) {
			logger.error("查询角色-失败:id="+id, e);
			return new ResultVo<SecuRole>(400,"ok",null);
		}
    }
	
	@PostMapping(value="/addRole",name="新增角色")
	@ResponseBody
    public ResultVo<String> addRole(SecuRole role){
		try {
			logger.info("新增角色-开始");
			String roleString = JSON.toJSONString(role);
			logger.info("新增角色-roleString:{}",roleString);
			secuRoleService.addRole(role);
			logger.info("新增角色-成功");
			return new ResultVo<String>(200,"ok",null);
		} catch (Exception e) {
			logger.error("新增角色-失败", e);
			return new ResultVo<String>(400,"error",null);
		}
	}
	
	@PostMapping(value="/addOrModifyRole",name="新增或修改角色")
	@ResponseBody
    public ResultVo<String> addOrModifyRole(SecuRole role){
		try {
			logger.info("新增或修改角色-开始");
			String roleString = JSON.toJSONString(role);
			logger.info("新增或修改角色-roleString:{}",roleString);
			secuRoleService.addOrModifyRole(role);
			logger.info("新增或修改角色-成功");
			return new ResultVo<String>(200,"ok",null);
		} catch (Exception e) {
			logger.error("新增或修改角色-失败", e);
			return new ResultVo<String>(400,"error",null);
		}
	}
	
	@PostMapping(value="/removeRoleById",name="根据id删除角色")
	@ResponseBody
	public ResultVo<String> removeRoleById(Long roleId) {
		try {
			logger.info("根据id删除角色-开始:roleId={}",roleId);
			secuRoleService.removeRoleById(roleId);
			logger.info("根据id删除角色-成功");
			return new ResultVo<String>(200,"ok",null);
		} catch (Exception e) {
			logger.error("根据id删除角色-失败", e);
			return new ResultVo<String>(400,"error",null);
		}
	}
	
	@PostMapping(value="/queryPagedSecuRoles",name="分页查询角色")
	@ResponseBody
    public PagedResultVo<SecuRole> queryPagedSecuRoles(
    		SecuRole role,PagedParamVo param){
		if(SiriusStringUtils.isEmpty(role.getRoleName()) || SiriusStringUtils.isEmpty(role.getRoleName().trim())){
			role.setRoleName(null);
		}
		if(SiriusStringUtils.isEmpty(role.getRoleCode()) || SiriusStringUtils.isEmpty(role.getRoleCode().trim())){
			role.setRoleCode(null);
		}
		return secuRoleService.queryPagedSecuRoles(role, param.getPage(), param.getRows());
    }
	
	@PostMapping(value="/addRoleResources",name="角色关联资源")
	@ResponseBody
    public ResultVo<String> addRoleResources(@RequestBody SecuRoleResources roleResources){
		try {
			logger.info("角色关联资源-开始");
			String roleResourcesString = JSON.toJSONString(roleResources);
			logger.info("角色关联资源-roleResourcesString:{}",roleResourcesString);
			List<SecuRoleResource> checkedRoleResources = roleResources.getCheckedRoleResources();
			List<SecuRoleResource> allRoleResources = roleResources.getAllRoleResources();
			secuRoleResourceService.addRoleResources(checkedRoleResources,allRoleResources);
			logger.info("角色关联资源-成功");
			return new ResultVo<String>(200,"ok",null);
		} catch (Exception e) {
			logger.error("角色关联资源-失败", e);
			return new ResultVo<String>(400,"error",null);
		}
	}
}
