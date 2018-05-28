package com.bcyj99.sirius.console.sys.controller;

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
import com.bcyj99.sirius.core.sys.service.CacheService;
import com.bcyj99.sirius.core.sys.service.SysParamService;
import com.bcyj99.sirius.core.sys.vo.SysParam;

@Controller
@RequestMapping("/sysParam")
public class SysParamController {
	
	private static final Logger logger = LoggerFactory.getLogger(SysParamController.class);
	
	@Autowired
	private SysParamService sysParamService;
	
	@Autowired
	private CacheService<String> cacheService;
	
	@PostMapping(value="/getSysParamByKey",name="缓存中获取系统参数")
	@ResponseBody
	public ResultVo<String> getSysParamByKey(String paramKey){
		try {
			logger.info("缓存中获取系统参数-开始....");
			logger.info("缓存中获取系统参数-paramKey:{}",paramKey);
			String paramValue = cacheService.get(paramKey);
			logger.info("缓存中获取系统参数-成功.");
			return new ResultVo<String>(200,"ok",paramValue);
		} catch (Exception e) {
			logger.error("缓存中获取系统参数-失败.", e);
			return new ResultVo<String>(400,"error",null);
		}
	}
	
	@RequestMapping("/sysParamPage")
	public String sysParamPage() {
		return "/sys/sysParam";
	}

	@PostMapping(value="/queryPagedSysParams",name="分页查询系统参数")
	@ResponseBody
	public PagedResultVo<SysParam> queryPagedSysParams(SysParam sysParam,PagedParamVo pagedParam){
		try {
			logger.info("分页查询系统参数-开始....");
			String sysParamStr = JSON.toJSONString(sysParam);
			logger.info("分页查询系统参数-sysParamStr:{}",sysParamStr);
			
			String paramKey = sysParam.getParamKey();
			if(SiriusStringUtils.isBlank(paramKey)){
				sysParam.setParamKey(null);
			}else{
				sysParam.setParamKey(paramKey.trim());
			}
			
			String paramValue = sysParam.getParamValue();
			if(SiriusStringUtils.isBlank(paramValue)){
				sysParam.setParamValue(null);
			}else{
				sysParam.setParamValue(paramValue.trim());
			}
			
			String paramRemark = sysParam.getParamRemark();
			if(SiriusStringUtils.isBlank(paramRemark)){
				sysParam.setParamRemark(null);
			}else{
				sysParam.setParamRemark(paramRemark.trim());
			}
			
			PagedResultVo<SysParam> result =  sysParamService.queryPagedSysParams(sysParam, pagedParam.getPage(), pagedParam.getRows());
			logger.info("分页查询系统参数-成功.");
			return result;
		} catch (Exception e) {
			logger.error("分页查询系统参数-失败.", e);
			return null;
		}
	}
	
	@PostMapping(value="/addOrModifySysParam",name="新增或修改系统参数")
	@ResponseBody
	public ResultVo<String> addOrModifySysParam(SysParam sysParam){
		try {
			logger.info("新增或修改系统参数-开始....");
			String sysParamStr = JSON.toJSONString(sysParam);
			logger.info("新增或修改系统参数-sysParamStr:{}",sysParamStr);
			sysParamService.addOrModifySysParam(sysParam);
			logger.info("新增或修改系统参数-成功.");
			return new ResultVo<String>(200,"ok",null);
		} catch (Exception e) {
			logger.error("新增或修改系统参数-失败.", e);
			return new ResultVo<String>(400,"error",null);
		}
	}
	
	@PostMapping(value="/removeSysParamById",name="删除系统参数")
	@ResponseBody
	public ResultVo<String> removeSysParamById(Long id,String paramKey){
		try {
			logger.info("删除系统参数-开始....");
			logger.info("删除系统参数-id:{}",id);
			logger.info("删除系统参数-paramKey:{}",paramKey);
			sysParamService.removeSysParam(id,paramKey);
			logger.info("删除系统参数-成功.");
			return new ResultVo<String>(200,"ok",null);
		} catch (Exception e) {
			logger.error("删除系统参数-失败.", e);
			return new ResultVo<String>(400,"error",null);
		}
	}
}
