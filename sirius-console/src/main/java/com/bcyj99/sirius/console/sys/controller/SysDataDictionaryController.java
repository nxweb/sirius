package com.bcyj99.sirius.console.sys.controller;

import java.util.List;

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
import com.bcyj99.sirius.core.sys.service.SysDataDictionaryService;
import com.bcyj99.sirius.core.sys.vo.SysDataDictionary;
import com.bcyj99.sirius.core.sys.vo.SysDataDictionaryVo;

@Controller
@RequestMapping("/sysDataDictionary")
public class SysDataDictionaryController {
	
	private static final Logger logger = LoggerFactory.getLogger(SysDataDictionaryController.class);
	
	@Autowired
	private SysDataDictionaryService sysDataDictionaryService;
	
	@Autowired
	private CacheService<SysDataDictionaryVo> cacheService;
	
	@PostMapping(value="/getSysDataDictionaryByName",name="缓存中获取数据字典")
	@ResponseBody
	public ResultVo<List<SysDataDictionaryVo>> getSysDataDictionaryByName(String dicName){
		try {
			logger.info("缓存中获取数据字典-开始....");
			logger.info("缓存中获取数据字典-dicName:{}",dicName);
			List<SysDataDictionaryVo> list = cacheService.getList(dicName);
			logger.info("缓存中获取数据字典-成功.");
			return new ResultVo<List<SysDataDictionaryVo>>(200,"ok",list);
		} catch (Exception e) {
			logger.error("缓存中获取数据字典-失败.", e);
			return new ResultVo<List<SysDataDictionaryVo>>(400,"error",null);
		}
	}
	
	@RequestMapping("/dataDictionaryPage")
	public String dataDictionaryPage() {
		List<SysDataDictionaryVo> list = cacheService.getList("gender");
		System.out.println("========================="+list.size());
		return "/sys/sysDataDictionary";
	}

	@PostMapping(value="/queryPagedSysDataDictionary",name="分页查询数据字典")
	@ResponseBody
	public PagedResultVo<SysDataDictionary> queryPagedSysDataDictionary(SysDataDictionary sysDataDictionary,PagedParamVo pagedParam){
		try {
			logger.info("分页查询数据字典-开始....");
			String sysDataDictionaryStr = JSON.toJSONString(sysDataDictionary);
			logger.info("分页查询数据字典-sysDataDictionaryStr:{}",sysDataDictionaryStr);
			
			String dicName = sysDataDictionary.getDicName();
			if(SiriusStringUtils.isBlank(dicName)){
				sysDataDictionary.setDicName(null);
			}else{
				sysDataDictionary.setDicName(dicName.trim());
			}
			
			String dicMeaning = sysDataDictionary.getDicMeaning();
			if(SiriusStringUtils.isBlank(dicMeaning)){
				sysDataDictionary.setDicMeaning(null);
			}else{
				sysDataDictionary.setDicMeaning(dicMeaning.trim());
			}
			
			String attributeValue = sysDataDictionary.getAttributeValue();
			if(SiriusStringUtils.isBlank(attributeValue)){
				sysDataDictionary.setAttributeValue(null);
			}else{
				sysDataDictionary.setAttributeValue(attributeValue.trim());
			}
			
			String attributeMeaning = sysDataDictionary.getAttributeMeaning();
			if(SiriusStringUtils.isBlank(attributeMeaning)){
				sysDataDictionary.setAttributeMeaning(null);
			}else{
				sysDataDictionary.setAttributeMeaning(attributeMeaning.trim());
			}
			
			PagedResultVo<SysDataDictionary> result = sysDataDictionaryService.queryPagedSysDataDictionarys(sysDataDictionary, pagedParam.getPage(), pagedParam.getRows());
			logger.info("分页查询数据字典-成功.");
			return result;
		} catch (Exception e) {
			logger.error("分页查询数据字典-失败.", e);
			return null;
		}
	}
	
	@PostMapping(value="/addOrModifySysDataDictionary",name="新增或修改数据字典")
	@ResponseBody
	public ResultVo<String> addOrModifySysDataDictionary(SysDataDictionary sysDataDictionary){
		try {
			logger.info("新增或修改数据字典-开始....");
			String sysDataDictionaryStr = JSON.toJSONString(sysDataDictionary);
			logger.info("新增或修改数据字典-sysParamStr:{}",sysDataDictionaryStr);
			sysDataDictionaryService.addOrModifySysDataDictionary(sysDataDictionary);
			logger.info("新增或修改数据字典-成功.");
			return new ResultVo<String>(200,"ok",null);
		} catch (Exception e) {
			logger.error("新增或修改数据字典-失败.", e);
			return new ResultVo<String>(400,"error",null);
		}
	}
	
	@PostMapping(value="/removeSysDataDictionaryById",name="删除数据字典")
	@ResponseBody
	public ResultVo<String> removeSysDataDictionaryById(Long id,String dicName){
		try {
			logger.info("删除数据字典-开始....");
			logger.info("删除数据字典-id:{}",id);
			logger.info("删除数据字典-dicName:{}",dicName);
			sysDataDictionaryService.removeSysDataDictionary(id, dicName);
			logger.info("删除数据字典-成功.");
			return new ResultVo<String>(200,"ok",null);
		} catch (Exception e) {
			logger.error("删除数据字典-失败.", e);
			return new ResultVo<String>(400,"error",null);
		}
	}
}
