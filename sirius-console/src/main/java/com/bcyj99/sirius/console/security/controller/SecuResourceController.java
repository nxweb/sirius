package com.bcyj99.sirius.console.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bcyj99.sirius.core.common.page.PagedParamVo;
import com.bcyj99.sirius.core.common.page.PagedResultVo;
import com.bcyj99.sirius.core.common.utils.SiriusStringUtils;
import com.bcyj99.sirius.core.common.vo.ResultVo;
import com.bcyj99.sirius.core.security.service.SecuResourceService;
import com.bcyj99.sirius.core.security.vo.SecuResource;

@Controller
@RequestMapping("/secuResource")
public class SecuResourceController {
	
	private static final Logger logger = LoggerFactory.getLogger(SecuResourceController.class);
	
	@Autowired
	private SecuResourceService secuResourceService;
	
	@RequestMapping("/resourcesPage")
	public String resourcesPage() {
		return "/security/resource";
	}
	
	@PostMapping("/addResource")
	@ResponseBody
	public ResultVo<String> addResource(SecuResource secuResource) {
		try {
			secuResourceService.addResource(secuResource);
			return new ResultVo<String>(200,"ok",null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("新增资源失败", e);
			return new ResultVo<String>(400,"error",null);
		}
	}
	
	@PostMapping(value="/addOrModifyResource",name="新增或修改资源")
	@ResponseBody
	public ResultVo<String> addOrModifyResource(SecuResource secuResource) {
		try {
			logger.info("新增或修改资源-开始");
			String secuResourceJsonStr = JSON.toJSONString(secuResource);
			logger.info("新增或修改资源-secuResourceJsonStr:{}",secuResourceJsonStr);
			secuResourceService.addOrModifyResource(secuResource);
			logger.info("新增或修改资源-成功");
			return new ResultVo<String>(200,"ok",null);
		} catch (Exception e) {
			logger.error("新增或修改资源-失败", e);
			return new ResultVo<String>(400,"error",null);
		}
	}
	
	@PostMapping(value="/removeResourceById",name="根据id删除资源")
	@ResponseBody
	public ResultVo<String> removeResourceById(Long resourceId) {
		try {
			logger.info("根据id删除资源-开始:resourceId={}",resourceId);
			secuResourceService.removeResourceById(resourceId);
			logger.info("根据id删除资源-成功");
			return new ResultVo<String>(200,"ok",null);
		} catch (Exception e) {
			logger.error("根据id删除资源-失败", e);
			return new ResultVo<String>(400,"error",null);
		}
	}

	@PostMapping("/queryPagedSecuResources")
	@ResponseBody
	public PagedResultVo<SecuResource> queryPagedSecuResources(SecuResource secuResource, PagedParamVo param) {
		if(SiriusStringUtils.isBlank(secuResource.getResourceName())){
			secuResource.setResourceName(null);
		}
		if(SiriusStringUtils.isBlank(secuResource.getResourceType())){
			secuResource.setResourceType(null);
		}
		if(SiriusStringUtils.isBlank(secuResource.getResourceUrl())){
			secuResource.setResourceUrl(null);
		}
		if(SiriusStringUtils.isBlank(secuResource.getResourceDesc())){
			secuResource.setResourceDesc(null);
		}
		
		return secuResourceService.queryPagedSecuResources(secuResource, param.getPage(), param.getRows());
	}
}
