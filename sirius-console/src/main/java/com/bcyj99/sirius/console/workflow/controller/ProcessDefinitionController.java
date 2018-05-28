package com.bcyj99.sirius.console.workflow.controller;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bcyj99.sirius.core.common.page.PagedParamVo;
import com.bcyj99.sirius.core.common.page.PagedResultVo;
import com.bcyj99.sirius.core.common.vo.ResultVo;
import com.bcyj99.sirius.core.workflow.service.ActivitiService;
import com.bcyj99.sirius.core.workflow.vo.ProcessDefinitionVo;

@Controller
@RequestMapping("/processDefinition")
public class ProcessDefinitionController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProcessDefinitionController.class);
	
	@Autowired
	private ActivitiService activitiService;
	
	@RequestMapping(value="/processDefinitionsPage",name="访问流程部署页面")
	public String processDefinitionsPage(HttpServletRequest request){
		try {
			logger.info("访问流程部署页面-开始");
			logger.info("访问流程部署页面-成功");
			return "/workflow/processDefinitions";
		} catch (Exception e) {
			logger.error("访问流程部署页面-失败", e);
			return "/common/error";
		}
	}

	@PostMapping(value="/deploy",name="部署流程")
	@ResponseBody
	public ResultVo<String> deploy(HttpServletRequest request,String inputName){
		try {
			logger.info("部署流程-开始");
			MultipartHttpServletRequest fileRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = fileRequest.getFile(inputName);
			InputStream in = multipartFile.getInputStream();
			ZipInputStream zipInputStream = new ZipInputStream(in);
			activitiService.deploy(zipInputStream);
			logger.info("部署流程-成功");
			return new ResultVo<String>(200,"ok",null);
		} catch (Exception e) {
			logger.error("部署流程-失败", e);
			return new ResultVo<String>(201,"error",null);
		}
	}
	
	@PostMapping(value="/quereyPagedProcessDefinitions",name="分页查询流程定义")
	@ResponseBody
	public PagedResultVo<ProcessDefinitionVo> quereyPagedProcessDefinitions(ProcessDefinitionVo processDefinitionVo,PagedParamVo param){
		try {
//			String token = request.getHeader("Access-Toke");
//			System.out.println(token);
			
			logger.info("查询流程-开始");
			param.setPageNo(param.getPage());
			param.setPageSize(param.getRows());
			
			PagedResultVo<ProcessDefinitionVo> pagedDeployments = activitiService.queryPagedProcessDefinitions(processDefinitionVo,param);
			logger.info("查询流程-成功");
			return pagedDeployments;
		} catch (Exception e) {
			logger.error("查询流程-失败", e);
			return null;
		}
	}
	
	@RequestMapping(value="/viewDiagramImageProcessDefinitionPage",name="查看流程定义的流程图")
	public String viewDiagramImageProcessDefinitionPage(Model model,String processDefinitionId){
		try {
			logger.info("查看流程定义的流程图-开始");
			logger.info("查看流程定义的流程图-成功");
			model.addAttribute("processDefinitionId", processDefinitionId);
			return "/workflow/diagramImage";
		} catch (Exception e) {
			logger.error("查看流程定义的流程图-失败", e);
			return "/common/error";
		}
	}
	
	@RequestMapping(value="/generateDiagramImageProcessDefinition",name="生成流程定义的流程图")
	public void generateDiagramImageProcessDefinition(HttpServletResponse response,String processDefinitionId){
		try {
			logger.info("生成流程定义的流程图-开始");
			activitiService.generateDiagramImageProcessDefinition(response, processDefinitionId);
			logger.info("生成流程定义的流程图-成功");
		} catch (Exception e) {
			logger.error("生成流程定义的流程图-失败", e);
		}
	}
	
	@PostMapping(value="/getProcessTrace",name="获取流程图各个节点的具体的信息")
	@ResponseBody
	public ResultVo<List<Map<String, Object>>> getProcessTrace(String processDefinitionId){
		try {
			logger.info("获取流程图各个节点的具体的信息-开始");
			List<Map<String, Object>> list = activitiService.getProcessTrace(processDefinitionId);
			logger.info("获取流程图各个节点的具体的信息-成功");
			return new ResultVo<List<Map<String, Object>>>(200,"ok",list);
		} catch (Exception e) {
			logger.error("获取流程图各个节点的具体的信息-失败", e);
			return new ResultVo<List<Map<String, Object>>>(201,"error",null);
		}
	}
}
