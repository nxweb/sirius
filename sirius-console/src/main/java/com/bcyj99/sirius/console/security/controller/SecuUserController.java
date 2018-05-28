package com.bcyj99.sirius.console.security.controller;

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
import com.bcyj99.sirius.core.common.vo.ResultVo;
import com.bcyj99.sirius.core.security.service.SecuUserRoleService;
import com.bcyj99.sirius.core.security.service.SecuUserService;
import com.bcyj99.sirius.core.security.vo.SecuUser;
import com.bcyj99.sirius.core.security.vo.SecuUserRoles;

@Controller
@RequestMapping("/secuUser")
public class SecuUserController {
	
    private static final Logger logger = LoggerFactory.getLogger(SecuUserController.class);
	
	@Autowired
	private SecuUserService secuUserService;
	
	@Autowired
	private SecuUserRoleService secuUserRoleService;
	
	@RequestMapping(value="/userPage",name="用户-主页")
    public String userPage(){
		return "/security/user";
	}
	
	@PostMapping(value="/queryUserById",name="根据id查询用户")
	@ResponseBody
    public ResultVo<SecuUser> queryUserById(Long id){
		try {
			logger.info("根据id查询用户-开始:id={}",id);
			SecuUser user = secuUserService.queryUserById(id);
			logger.info("根据id查询用户-成功");
			return new ResultVo<SecuUser>(200,"ok",user);
		} catch (Exception e) {
			logger.error("根据id查询用户-失败:id="+id, e);
			return new ResultVo<SecuUser>(400,"ok",null);
		}
    }
	
	@PostMapping(value="/addUser",name="新增用户")
	@ResponseBody
    public ResultVo<String> addUser(SecuUser user){
		try {
			logger.info("新增用户-开始");
			String userString = JSON.toJSONString(user);
			logger.info("新增用户-userString:{}",userString);
			secuUserService.addUser(user);
			logger.info("新增用户-成功");
			return new ResultVo<String>(200,"ok",null);
		} catch (Exception e) {
			logger.error("新增用户-失败", e);
			return new ResultVo<String>(400,"error",null);
		}
	}
	
	@PostMapping(value="/addOrModifyUser",name="新增或修改用户")
	@ResponseBody
    public ResultVo<String> addOrModifyUser(SecuUser user){
		try {
			logger.info("新增或修改用户-开始");
			String userString = JSON.toJSONString(user);
			logger.info("新增或修改用户-userString:{}",userString);
			secuUserService.addOrModifyUser(user);
			logger.info("新增或修改用户-成功");
			return new ResultVo<String>(200,"ok",null);
		} catch (Exception e) {
			logger.error("新增或修改用户-失败", e);
			return new ResultVo<String>(400,"error",null);
		}
	}
	
	@PostMapping(value="/queryPagedSecuUsers",name="分页查询用户")
	@ResponseBody
    public PagedResultVo<SecuUser> queryPagedSecuUsers(
			SecuUser user,PagedParamVo param){
		return secuUserService.queryPagedSecuUsers(user, param.getPage(), param.getRows());
    }
	
	@PostMapping(value="/addUserRoles",name="用户关联角色")
	@ResponseBody
    public ResultVo<String> addUserRoles(@RequestBody SecuUserRoles userRoles){
		try {
			logger.info("用户关联角色-开始");
			String userRolesString = JSON.toJSONString(userRoles);
			logger.info("用户关联角色-userRolesString:{}",userRolesString);
			secuUserRoleService.addUserRoles(userRoles.getCheckedUserRoles(),userRoles.getAllUserRoles());
			logger.info("用户关联角色-成功");
			return new ResultVo<String>(200,"ok",null);
		} catch (Exception e) {
			logger.error("用户关联角色-失败", e);
			return new ResultVo<String>(400,"error",null);
		}
	}
}
