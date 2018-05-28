package com.bcyj99.sirius.core.workflow.service.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcyj99.sirius.core.common.constants.ActivitiConstants;
import com.bcyj99.sirius.core.common.constants.SysParamConstants;
import com.bcyj99.sirius.core.common.page.PagedParamVo;
import com.bcyj99.sirius.core.common.page.PagedResultVo;
import com.bcyj99.sirius.core.common.utils.SiriusCollectionUtils;
import com.bcyj99.sirius.core.common.utils.SiriusStringUtils;
import com.bcyj99.sirius.core.sys.service.SysParamService;
import com.bcyj99.sirius.core.workflow.service.ActivitiService;
import com.bcyj99.sirius.core.workflow.vo.ActivitiQueryCondition;
import com.bcyj99.sirius.core.workflow.vo.ApprovalAttachment;
import com.bcyj99.sirius.core.workflow.vo.ApprovalComment;
import com.bcyj99.sirius.core.workflow.vo.ProcessDefinitionVo;
import com.bcyj99.sirius.core.workflow.vo.ReviewHistoryVo;
import com.bcyj99.sirius.core.workflow.vo.TaskVo;

@Service
public class ActivitiServiceImpl implements ActivitiService {
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired  
	private ProcessEngineConfiguration processEngineConfiguration;
	
	@Autowired  
	private ProcessEngineFactoryBean processEngine;
	
	@Autowired
	private SysParamService sysParamService;

	/**
	 * 部署流程
	 * @param zipInputStream
	 */
	@Override
	public void deploy(ZipInputStream zipInputStream) {
		repositoryService.createDeployment().addZipInputStream(zipInputStream).deploy();
	}
	
	/**
	 * 查询流程
	 * @param
	 */
	public PagedResultVo<ProcessDefinitionVo> queryPagedProcessDefinitions(ProcessDefinitionVo processDefinitionVo,PagedParamVo param){
		Integer pageNo = param.getPageNo();
		Integer pageSize = param.getPageSize();
		List<ProcessDefinitionVo> processDefinitionVos = new ArrayList<ProcessDefinitionVo>();
		
		ProcessDefinitionQuery processDefinitionQuery =repositoryService.createProcessDefinitionQuery();
		String pdName = processDefinitionVo.getName();
		if(SiriusStringUtils.isNotBlank(pdName)){
			processDefinitionQuery=processDefinitionQuery.processDefinitionNameLike("%"+pdName.trim()+"%");
		}
		
		List<ProcessDefinition> processDefinitions = processDefinitionQuery.orderByProcessDefinitionName().orderByProcessDefinitionVersion().desc().listPage((pageNo-1)*pageSize, pageSize);
		Long rowCount = processDefinitionQuery.count();
		if(processDefinitions==null ||processDefinitions.isEmpty()){
			return new PagedResultVo<ProcessDefinitionVo>(rowCount,processDefinitionVos);
		}
		
		Map<String,Integer> keyVersionMap = new HashMap<String,Integer>();
		ProcessDefinitionVo pdv = null;
		for(ProcessDefinition pd : processDefinitions){
			pdv = new ProcessDefinitionVo();
			
			if(!keyVersionMap.keySet().contains(pd.getKey())){
				keyVersionMap.put(pd.getKey(), pd.getVersion());
				pdv.setIsLastVersion(1);
			}else{
				pdv.setIsLastVersion(0);
			}
			
			pdv.setId(pd.getId());
			pdv.setVersion(pd.getVersion());
			pdv.setCategory(pd.getCategory());
			pdv.setName(pd.getName());
			pdv.setKey(pd.getKey());
			pdv.setDeploymentId(pd.getDeploymentId());
			pdv.setResourceName(pd.getResourceName());
			pdv.setDiagramResourceName(pd.getDiagramResourceName());
			pdv.setDescription(pd.getDescription());
			
			Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(pd.getDeploymentId()).singleResult();
			pdv.setDeployTime(deployment.getDeploymentTime());
			processDefinitionVos.add(pdv);
		}
		
		return new PagedResultVo<ProcessDefinitionVo>(rowCount,processDefinitionVos);
	}
	
	/**
	 * 生成流程定义的流程图
	 * 
	 */
	public void generateDiagramImageProcessDefinition(HttpServletResponse response,String processDefinitionId) throws IOException{  
        //获取流程图  
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);  
        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();  
          
        //单独返回流程图，不高亮显示  
        InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", "宋体", "宋体", "宋体", null, 1.0);
        // 输出资源内容到相应对象  
        byte[] b = new byte[1024];  
        int len;  
        while ((len = imageStream.read(b, 0, 1024)) != -1) {  
            response.getOutputStream().write(b, 0, len);  
        }  
    }
	
	/**  
	    * 获取流程图各个节点的具体的信息  
	    * @param wfKey  
	    *      流程定义的key  
	    * @return  
	    */    
	   public List<Map<String, Object>> getProcessTrace(String processDefinitionId) throws Exception {    
	       List<Map<String, Object>> activityInfos = new ArrayList<Map<String, Object>>();    
	    ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)  
	            .getDeployedProcessDefinition(processDefinitionId);  
	       List<ActivityImpl> activitiList = processDefinition.getActivities();    
//	       InputStream xmlIs = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName());    
//	       BpmnModel bm = new BpmnXMLConverter().convertToBpmnModel(new InputStreamSource(xmlIs), false, true);    
	       // 下方使用反射获取最小的x和y，仔细看就会发现调用的是上方2.1节的方法   
	       Class<?> clazz = Class.forName("org.activiti.image.impl.DefaultProcessDiagramGenerator");
	       Method method = clazz.getDeclaredMethod("initProcessDiagramCanvas", BpmnModel.class, String.class,  
	              String.class, String.class,String.class, ClassLoader.class);    
	       method.setAccessible(true);     
//	       DefaultProcessDiagramCanvas pdc = (DefaultProcessDiagramCanvas) method.invoke(clazz.newInstance(), bm,"png", "宋体", "宋体","宋体",  null); // 调用方法    
	   
	       clazz = Class.forName("org.activiti.image.impl.DefaultProcessDiagramCanvas");    
	       Field minXField = clazz.getDeclaredField("minX"); // 得到minX字段    
	       Field minYField = clazz.getDeclaredField("minY");    
	       minXField.setAccessible(true);    
	       minYField.setAccessible(true);    
//	       int minX = minXField.getInt(pdc);// 最小的x值      
//	       int minY = minYField.getInt(pdc); // 最小的y的值    
	   
	  
//	       minX = minX > 0 ? minX - 5 : 0;      
//	       minY = minY > 0 ? minY - 5 : 0;    
	       
	       StringBuilder sb = null;
	       for (ActivityImpl activity : activitiList) {    
	    	   String activityType = (String) activity.getProperty("type");
	    	   
	    	   if("userTask".equals(activityType)){
	    		   Map<String, Object> activityInfo = new HashMap<String, Object>();    
		           activityInfo.put("width", activity.getWidth());    
		           activityInfo.put("height", activity.getHeight());    
//		           activityInfo.put("x", activity.getX() - minX);    
//		           activityInfo.put("y", activity.getY() - minY);
		           
		           activityInfo.put("x", activity.getX());    
		           activityInfo.put("y", activity.getY());
		           
		           activityInfo.put("actId", activity.getId());    
		           
		           TaskDefinition taskDefinition = (TaskDefinition) activity.getProperty("taskDefinition");
		           if(taskDefinition != null){
		        	   Set<Expression> set = taskDefinition.getCandidateGroupIdExpressions();
			           sb = new StringBuilder();
			           for(Expression exp : set){
			        	   String groupId = exp.getExpressionText();
			        	   Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
			        	   if(group == null){
			        		   sb.append(groupId);
				        	   sb.append(" ");
			        	   }else{
			        		   sb.append(group.getName());
				        	   sb.append(" ");
			        	   }
			           }
			           activityInfo.put("role", sb.toString());
		           }
		           
		           activityInfo.put("name", activity.getProperty("name")); // </strong>ActivityImpl 中没有getName方法，所以此处需要这样获取。</span>    
		           activityInfos.add(activityInfo);
	    	   }
	       }    
	        
	         
	       return activityInfos;    
	   }
	
	/**
	 * 生成流程实例的流程图
	 */
	public void generateDiagramImageProcessInstance(HttpServletResponse response,String processInstanceId) throws IOException{  
        //获取历史流程实例  
        HistoricProcessInstance processInstance =  historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String processDefinitionId = processInstance.getProcessDefinitionId();
        //获取流程图  
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);  
        processEngineConfiguration = processEngine.getProcessEngineConfiguration();  
        Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);  
  
        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();  
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefinitionId);  
  
        List<HistoricActivityInstance> highLightedActivitList =  historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();  
        //高亮环节id集合  
        List<String> highLightedActivitis = new ArrayList<String>();  
          
        //高亮线路id集合  
        List<String> highLightedFlows = getHighLightedFlows(definitionEntity,highLightedActivitList);  
  
        for(HistoricActivityInstance tempActivity : highLightedActivitList){  
            String activityId = tempActivity.getActivityId();  
            highLightedActivitis.add(activityId);  
        }  
  
        //中文显示的是口口口，设置字体就好了  
        InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis, highLightedFlows, "宋体", "宋体", "宋体", null, 1.0);
//        InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis,highLightedFlows,"宋体","宋体",null,1.0);  
        //单独返回流程图，不高亮显示  
//        InputStream imageStream = diagramGenerator.generatePngDiagram(bpmnModel);  
        // 输出资源内容到相应对象  
        byte[] b = new byte[1024];  
        int len;  
        while ((len = imageStream.read(b, 0, 1024)) != -1) {  
            response.getOutputStream().write(b, 0, len);  
        }  
    }  
     /**  
     * 获取需要高亮的线  
     * @param processDefinitionEntity  
     * @param historicActivityInstances  
     * @return  
     */  
    private List<String> getHighLightedFlows(  
            ProcessDefinitionEntity processDefinitionEntity,  
            List<HistoricActivityInstance> historicActivityInstances) {  
          
        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId  
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {// 对历史流程节点进行遍历  
            ActivityImpl activityImpl = processDefinitionEntity  
                    .findActivity(historicActivityInstances.get(i)  
                            .getActivityId());// 得到节点定义的详细信息  
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点  
            ActivityImpl sameActivityImpl1 = processDefinitionEntity  
                    .findActivity(historicActivityInstances.get(i + 1)  
                            .getActivityId());  
            // 将后面第一个节点放在时间相同节点的集合里  
            sameStartTimeNodes.add(sameActivityImpl1);  
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {  
                HistoricActivityInstance activityImpl1 = historicActivityInstances  
                        .get(j);// 后续第一个节点  
                HistoricActivityInstance activityImpl2 = historicActivityInstances  
                        .get(j + 1);// 后续第二个节点  
                if (activityImpl1.getStartTime().equals(  
                        activityImpl2.getStartTime())) {  
                    // 如果第一个节点和第二个节点开始时间相同保存  
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity  
                            .findActivity(activityImpl2.getActivityId());  
                    sameStartTimeNodes.add(sameActivityImpl2);  
                } else {  
                    // 有不相同跳出循环  
                    break;  
                }  
            }  
            List<PvmTransition> pvmTransitions = activityImpl  
                    .getOutgoingTransitions();// 取出节点的所有出去的线  
            for (PvmTransition pvmTransition : pvmTransitions) {  
                // 对所有的线进行遍历  
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition  
                        .getDestination();  
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示  
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {  
                    highFlows.add(pvmTransition.getId());  
                }  
            }  
        }  
        return highFlows;  
    }  
	
    @Deprecated
	public void viewDiagramImage(HttpServletResponse response,String deploymentId, String resourceName) throws IOException{
		InputStream inputStream = repositoryService.getResourceAsStream(deploymentId, resourceName);
		BufferedInputStream bis = new BufferedInputStream(inputStream);
		
		ServletOutputStream out = response.getOutputStream();
		int itemp = 0;
		while ((itemp = bis.read()) != -1) {
			out.write(itemp);
		}
		
		bis.close();
		out.close();
	}
	
	/**
	 * 删除流程
	 * @param zipInputStream
	 */
	public void removeDeployment(Long id){
		repositoryService.deleteDeployment(id.toString(), true);
	}
	
	/** 
     * 驳回流程 
     *  
     * @param taskId 
     *            当前任务ID 
     * @param activityId 
     *            驳回节点ID 
     * @param variables 
     *            流程存储参数 
     * @throws Exception 
     */  
	@Deprecated
    public void rejectProcess(String taskId, String activityId,Map<String, Object> variables) throws Exception {  
        if (SiriusStringUtils.isEmpty(activityId)) {  
            throw new Exception("驳回目标节点ID为空！");  
        }  
  
        // 查询本节点发起的会签任务，并结束  
//        List<Task> tasks = taskService.createTaskQuery().parentTaskId(taskId)  
//                .taskDescription("jointProcess").list();  
//        for (Task task : tasks) {  
//            commitProcess(task.getId(), null, null);  
//        }  
  
        // 查找所有并行任务节点，同时驳回  
        List<Task> taskList = findTaskListByKey(findProcessInstanceByTaskId(taskId).getId(), findTaskById(taskId).getTaskDefinitionKey());  
        for (Task task : taskList) {  
            commitProcess(task.getId(), variables, activityId);  
        }  
    }
	
	/** 
     * 根据当前任务ID，查询可以驳回的任务节点 
     * 最终版
     * @param taskId 
     *            当前任务ID 
     */  
    public List<ReviewHistoryVo> findBackAvtivityVo(String taskId) throws Exception {
    	List<ReviewHistoryVo> list = new ArrayList<ReviewHistoryVo>();
    	List<ActivityImpl> activityImpls = this.findBackAvtivity(taskId);
    	if(activityImpls == null || activityImpls.isEmpty()){
    		return list;
    	}else{
    		ReviewHistoryVo rhv = null;
    		String activityId = null;
    		List<HistoricActivityInstance> hisActivities = null;
    		for(ActivityImpl ai : activityImpls){
    			activityId = ai.getId();
    			hisActivities = historyService.createHistoricActivityInstanceQuery().activityId(activityId).finished().list();
    			if(hisActivities !=null && !hisActivities.isEmpty()){
    				rhv = new ReviewHistoryVo();
    				rhv.setActivityId(hisActivities.get(0).getActivityId());
    				rhv.setTaskName(hisActivities.get(0).getActivityName());
    				list.add(rhv);
    			}
    		}
    	}
    	return list;
    }
    
    /** 
     * @param taskId 
     *            当前任务ID 
     * @param variables 
     *            流程变量 
     * @param activityId 
     *            流程转向执行任务节点ID<br> 
     *            此参数为空，默认为提交操作 
     * @throws Exception 
     */  
    private void commitProcess(String taskId, Map<String, Object> variables,  
            String activityId) throws Exception {  
        if (variables == null) {  
            variables = new HashMap<String, Object>();  
        }  
        // 跳转节点为空，默认提交操作  
        if (SiriusStringUtils.isEmpty(activityId)) {  
            taskService.complete(taskId, variables);  
        } else {// 流程转向操作  
            turnTransition(taskId, activityId, variables);  
        }  
    }  
    
    /** 
     * 流程转向操作 
     *  
     * @param taskId 
     *            当前任务ID 
     * @param activityId 
     *            目标节点任务ID 
     * @param variables 
     *            流程变量 
     * @throws Exception 
     */  
    private void turnTransition(String taskId, String activityId,  
            Map<String, Object> variables) throws Exception {  
        // 当前节点  
        ActivityImpl currActivity = findActivitiImpl(taskId, null);  
        // 清空当前流向  
        List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);  
  
        // 创建新流向  
        TransitionImpl newTransition = currActivity.createOutgoingTransition();  
        // 目标节点  
        ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);  
        // 设置新流向的目标节点  
        newTransition.setDestination(pointActivity);  
  
        // 执行转向任务  
        taskService.complete(taskId, variables);  
        // 删除目标节点新流入  
        pointActivity.getIncomingTransitions().remove(newTransition);  
  
        // 还原以前流向  
        restoreTransition(currActivity, oriPvmTransitionList);  
    }
    
    /** 
     * 清空指定活动节点流向 
     *  
     * @param activityImpl 
     *            活动节点 
     * @return 节点流向集合 
     */  
    private List<PvmTransition> clearTransition(ActivityImpl activityImpl) {  
        // 存储当前节点所有流向临时变量  
        List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();  
        // 获取当前节点所有流向，存储到临时变量，然后清空  
        List<PvmTransition> pvmTransitionList = activityImpl  
                .getOutgoingTransitions();  
        for (PvmTransition pvmTransition : pvmTransitionList) {  
            oriPvmTransitionList.add(pvmTransition);  
        }  
        pvmTransitionList.clear();  
  
        return oriPvmTransitionList;  
    } 
    
    /** 
     * 还原指定活动节点流向 
     *  
     * @param activityImpl 
     *            活动节点 
     * @param oriPvmTransitionList 
     *            原有节点流向集合 
     */  
    private void restoreTransition(ActivityImpl activityImpl,  
            List<PvmTransition> oriPvmTransitionList) {  
        // 清空现有流向  
        List<PvmTransition> pvmTransitionList = activityImpl  
                .getOutgoingTransitions();  
        pvmTransitionList.clear();  
        // 还原以前流向  
        for (PvmTransition pvmTransition : oriPvmTransitionList) {  
            pvmTransitionList.add(pvmTransition);  
        }  
    }  
    
    /** 
     * 根据流程实例ID和任务key值查询所有同级任务集合 
     *  
     * @param processInstanceId 
     * @param key 
     * @return 
     */  
    private List<Task> findTaskListByKey(String processInstanceId, String key) {  
        return taskService.createTaskQuery().processInstanceId(processInstanceId).taskDefinitionKey(key).list();  
    }  
    
    /** 
     * 根据任务ID获取对应的流程实例 
     *  
     * @param taskId 
     *            任务ID 
     * @return 
     * @throws Exception 
     */  
    private ProcessInstance findProcessInstanceByTaskId(String taskId)  
            throws Exception {  
        // 找到流程实例  
        ProcessInstance processInstance = runtimeService  
                .createProcessInstanceQuery().processInstanceId(  
                        findTaskById(taskId).getProcessInstanceId())  
                .singleResult();  
        if (processInstance == null) {  
            throw new Exception("流程实例未找到!");  
        }  
        return processInstance;  
    }
	
	/** 
     * 根据当前任务ID，查询可以驳回的任务节点 
     *  
     * @param taskId 
     *            当前任务ID 
     */  
    private List<ActivityImpl> findBackAvtivity(String taskId) throws Exception {  
        List<ActivityImpl> rtnList = null;  
        if (isJointTask(taskId)) {// 会签任务节点，不允许驳回  
            rtnList = new ArrayList<ActivityImpl>();  
        } else {  
            rtnList = iteratorBackActivity(taskId, findActivitiImpl(taskId,  
                    null), new ArrayList<ActivityImpl>(),  
                    new ArrayList<ActivityImpl>());  
        }  
        return reverList(rtnList);  
    }
    
    private boolean isJointTask(String taskId) throws Exception{
    	TaskEntity task = this.findTaskById(taskId);
    	Object obj = runtimeService.getVariable(task.getExecutionId(), "nrOfInstances");
    	if(obj != null){
    		return true;
    	}
    	return false;
    }
    
    /** 
     * 反向排序list集合，便于驳回节点按顺序显示 
     *  
     * @param list 
     * @return 
     */  
    private List<ActivityImpl> reverList(List<ActivityImpl> list) {  
        List<ActivityImpl> rtnList = new ArrayList<ActivityImpl>();  
        // 由于迭代出现重复数据，排除重复  
        for (int i = list.size(); i > 0; i--) {  
            if (!rtnList.contains(list.get(i - 1)))  
                rtnList.add(list.get(i - 1));  
        }  
        return rtnList;  
    }  

	/** 
     * 迭代循环流程树结构，查询当前节点可驳回的任务节点 
     *  
     * @param taskId 
     *            当前任务ID 
     * @param currActivity 
     *            当前活动节点 
     * @param rtnList 
     *            存储回退节点集合 
     * @param tempList 
     *            临时存储节点集合（存储一次迭代过程中的同级userTask节点） 
     * @return 回退节点集合 
     */  
    private List<ActivityImpl> iteratorBackActivity(String taskId,  
            ActivityImpl currActivity, List<ActivityImpl> rtnList,  
            List<ActivityImpl> tempList) throws Exception {  
        // 查询流程定义，生成流程树结构  
    	Task task = taskService.createTaskQuery().taskId(taskId).active().singleResult();
    	String processInstanceId = task.getProcessInstanceId();
    	ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
  
        // 当前节点的流入来源  
        List<PvmTransition> incomingTransitions = currActivity.getIncomingTransitions();  
        // 条件分支节点集合，userTask节点遍历完毕，迭代遍历此集合，查询条件分支对应的userTask节点  
        List<ActivityImpl> exclusiveGateways = new ArrayList<ActivityImpl>();  
        // 并行节点集合，userTask节点遍历完毕，迭代遍历此集合，查询并行节点对应的userTask节点  
        List<ActivityImpl> parallelGateways = new ArrayList<ActivityImpl>();  
        
        // 遍历当前节点所有流入路径  
        for (PvmTransition pvmTransition : incomingTransitions) {  
            TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;  
            ActivityImpl activityImpl = transitionImpl.getSource();  
            String type = (String) activityImpl.getProperty("type");  
            /** 
             * 并行节点配置要求：<br> 
             * 必须成对出现，且要求分别配置节点ID为:XXX_start(开始)，XXX_end(结束) 
             */  
            if ("parallelGateway".equals(type)) {// 并行路线  
                String gatewayId = activityImpl.getId();  
                String gatewayType = gatewayId.substring(gatewayId.lastIndexOf("_") + 1);  
                if ("START".equals(gatewayType.toUpperCase())) {// 并行起点，停止递归  
                    return rtnList;  
                } else {// 并行终点，临时存储此节点，本次循环结束，迭代集合，查询对应的userTask节点  
                    parallelGateways.add(activityImpl);  
                }  
            } else if ("startEvent".equals(type)) {// 开始节点，停止递归  
                return rtnList;  
            } else if ("userTask".equals(type)) {// 用户任务  
                tempList.add(activityImpl);  
            } else if ("exclusiveGateway".equals(type)) {// 分支路线，临时存储此节点，本次循环结束，迭代集合，查询对应的userTask节点  
                currActivity = transitionImpl.getSource();  
                exclusiveGateways.add(currActivity);  
            }  
        }  
  
        /** 
         * 迭代条件分支集合，查询对应的userTask节点 
         */  
        for (ActivityImpl activityImpl : exclusiveGateways) {  
            iteratorBackActivity(taskId, activityImpl, rtnList, tempList);  
        }  
  
        /** 
         * 迭代并行集合，查询对应的userTask节点 
         */  
        for (ActivityImpl activityImpl : parallelGateways) {  
            iteratorBackActivity(taskId, activityImpl, rtnList, tempList);  
        }  
  
        /** 
         * 根据同级userTask集合，过滤最近发生的节点 
         */  
        currActivity = filterNewestActivity(processInstance, tempList);  
        if (currActivity != null) {  
            // 查询当前节点的流向是否为并行终点，并获取并行起点ID  
            String id = findParallelGatewayId(currActivity);  
            if (SiriusStringUtils.isEmpty(id)) {// 并行起点ID为空，此节点流向不是并行终点，符合驳回条件，存储此节点  
                rtnList.add(currActivity);  
            } else {// 根据并行起点ID查询当前节点，然后迭代查询其对应的userTask任务节点  
                currActivity = findActivitiImpl(taskId, id);  
            }  
  
            // 清空本次迭代临时集合  
            tempList.clear();  
            // 执行下次迭代  
            iteratorBackActivity(taskId, currActivity, rtnList, tempList);  
        }  
        return rtnList;  
    }  
    
    /** 
     * 根据流入任务集合，查询最近一次的流入任务节点 
     *  
     * @param processInstance 
     *            流程实例 
     * @param tempList 
     *            流入任务集合 
     * @return 
     */  
    private ActivityImpl filterNewestActivity(ProcessInstance processInstance,  
            List<ActivityImpl> tempList) {  
        while (tempList.size() > 0) {  
            ActivityImpl activity_1 = tempList.get(0);  
            HistoricActivityInstance activityInstance_1 = findHistoricUserTask(  
                    processInstance, activity_1.getId());  
            if (activityInstance_1 == null) {  
                tempList.remove(activity_1);  
                continue;  
            }  
  
            if (tempList.size() > 1) {  
                ActivityImpl activity_2 = tempList.get(1);  
                HistoricActivityInstance activityInstance_2 = findHistoricUserTask(  
                        processInstance, activity_2.getId());  
                if (activityInstance_2 == null) {  
                    tempList.remove(activity_2);  
                    continue;  
                }  
  
                if (activityInstance_1.getEndTime().before(  
                        activityInstance_2.getEndTime())) {  
                    tempList.remove(activity_1);  
                } else {  
                    tempList.remove(activity_2);  
                }  
            } else {  
                break;  
            }  
        }  
        if (tempList.size() > 0) {  
            return tempList.get(0);  
        }  
        return null;  
    }  
    
    /** 
     * 根据当前节点，查询输出流向是否为并行终点，如果为并行终点，则拼装对应的并行起点ID 
     *  
     * @param activityImpl 
     *            当前节点 
     * @return 
     */  
    private String findParallelGatewayId(ActivityImpl activityImpl) {  
        List<PvmTransition> incomingTransitions = activityImpl  
                .getOutgoingTransitions();  
        for (PvmTransition pvmTransition : incomingTransitions) {  
            TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;  
            activityImpl = transitionImpl.getDestination();  
            String type = (String) activityImpl.getProperty("type");  
            if ("parallelGateway".equals(type)) {// 并行路线  
                String gatewayId = activityImpl.getId();  
                String gatewayType = gatewayId.substring(gatewayId  
                        .lastIndexOf("_") + 1);  
                if ("END".equals(gatewayType.toUpperCase())) {  
                    return gatewayId.substring(0, gatewayId.lastIndexOf("_"))  
                            + "_start";  
                }  
            }  
        }  
        return null;  
    }  
    
    /** 
     * 根据任务ID和节点ID获取活动节点 <br> 
     *  
     * @param taskId 
     *            任务ID 
     * @param activityId 
     *            活动节点ID <br> 
     *            如果为null或""，则默认查询当前活动节点 <br> 
     *            如果为"end"，则查询结束节点 <br> 
     *  
     * @return 
     * @throws Exception 
     */  
    private ActivityImpl findActivitiImpl(String taskId, String activityId)  
            throws Exception {  
        // 取得流程定义  
        ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);  
  
        // 获取当前活动节点ID  
        if (SiriusStringUtils.isEmpty(activityId)) {  
            activityId = findTaskById(taskId).getTaskDefinitionKey();  
        }  
  
        // 根据流程定义，获取该流程实例的结束节点  
        if (activityId.toUpperCase().equals("END")) {  
            for (ActivityImpl activityImpl : processDefinition.getActivities()) {  
                List<PvmTransition> pvmTransitionList = activityImpl  
                        .getOutgoingTransitions();  
                if (pvmTransitionList.isEmpty()) {  
                    return activityImpl;  
                }  
            }  
        }  
  
        // 根据节点ID，获取对应的活动节点  
        ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition)  
                .findActivity(activityId);  
  
        return activityImpl;  
    }  
    
    /** 
     * 查询指定任务节点的最新记录 
     *  
     * @param processInstance 
     *            流程实例 
     * @param activityId 
     * @return 
     */  
    private HistoricActivityInstance findHistoricUserTask(  
            ProcessInstance processInstance, String activityId) {  
        HistoricActivityInstance rtnVal = null;  
        // 查询当前流程实例审批结束的历史节点  
        List<HistoricActivityInstance> historicActivityInstances = historyService  
                .createHistoricActivityInstanceQuery().activityType("userTask")  
                .processInstanceId(processInstance.getId()).activityId(  
                        activityId).finished()  
                .orderByHistoricActivityInstanceEndTime().desc().list();  
        if (historicActivityInstances.size() > 0) {  
            rtnVal = historicActivityInstances.get(0);  
        }  
  
        return rtnVal;  
    }  
    
    /** 
     * 根据任务ID获取流程定义 
     *  
     * @param taskId 
     *            任务ID 
     * @return 
     * @throws Exception 
     */  
    private ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(  
            String taskId) throws Exception {  
        // 取得流程定义  
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)  
                .getDeployedProcessDefinition(findTaskById(taskId)  
                        .getProcessDefinitionId());  
  
        if (processDefinition == null) {  
            throw new Exception("流程定义未找到!");  
        }  
  
        return processDefinition;  
    } 
    
    /** 
     * 根据任务ID获得任务实例 
     *  
     * @param taskId 
     *            任务ID 
     * @return 
     * @throws Exception 
     */  
    private TaskEntity findTaskById(String taskId) throws Exception {  
        TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(  
                taskId).singleResult();  
        if (task == null) {  
            throw new Exception("任务实例未找到!");  
        }  
        return task;  
    }  
    
    /**
	 * 根据id启动流程
	 * @param processDefinitionKey
	 * @param businessKey
	 * @param variables
	 * @return
	 */
	public ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String businessKey, Map<String, Object> variables){
		return runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
	}
	
	public void passTask(String authenticatedUserId,String processInstanceId,ApprovalComment approvalComment,List<ApprovalAttachment> approvalAttachments,Map<String, Object> variables) throws Exception{
		Task task = taskService.createTaskQuery()
	               .processInstanceId(processInstanceId).active().singleResult();
        String taskId = task.getId();

		this.handleCommentAndAttachment(authenticatedUserId, taskId, processInstanceId, approvalComment.getFullMessage(), approvalAttachments, ActivitiConstants.APPROVAL_TYPE_PASS);
		
		taskService.complete(taskId, variables);
	}
	
	/**
	 * 通过
	 * @param authenticatedUserId   审批人姓名
	 * @param taskId    
	 * @param processInstanceId
	 * @param approvalComment   审批意见
	 * @param approvalAttachments 审批附件
	 */
	public void passTask(String authenticatedUserId,String taskId,String processInstanceId,ApprovalComment approvalComment,List<ApprovalAttachment> approvalAttachments,Map<String, Object> variables) throws Exception{
		this.handleCommentAndAttachment(authenticatedUserId, taskId, processInstanceId, approvalComment.getFullMessage(), approvalAttachments, ActivitiConstants.APPROVAL_TYPE_PASS);
		taskService.complete(taskId, variables);
	}
	
	/**
	 * 驳回
	 * @param authenticatedUserId    审批人姓名
	 * @param taskId   当前节点
	 * @param processInstanceId   当前节点
	 * @param destinationActivityId   目标节点
	 * @param approvalComment    审批意见
	 * @param approvalAttachments   审批附件
	 * @param variables
	 * @throws Exception 
	 */
	public void rejectTask(String authenticatedUserId,String taskId,String processInstanceId,String destinationActivityId,ApprovalComment approvalComment,List<ApprovalAttachment> approvalAttachments,Map<String, Object> variables) throws Exception{
		this.handleCommentAndAttachment(authenticatedUserId, taskId, processInstanceId, approvalComment.getFullMessage(), approvalAttachments, ActivitiConstants.APPROVAL_TYPE_REJECT);
		
		this.turnTransition(taskId, destinationActivityId, variables);
	}
	
	/**
	 * 拒绝
	 * @param authenticatedUserId
	 * @param taskId
	 * @param processInstanceId
	 * @param approvalComment
	 * @param approvalAttachments
	 * @param variables
	 * @throws Exception
	 */
	public void refuseTask(String authenticatedUserId,String taskId,String processInstanceId,ApprovalComment approvalComment,List<ApprovalAttachment> approvalAttachments,Map<String, Object> variables) throws Exception{
		this.handleCommentAndAttachment(authenticatedUserId, taskId, processInstanceId, approvalComment.getFullMessage(), approvalAttachments, ActivitiConstants.APPROVAL_TYPE_REFUSE);
		
		runtimeService.suspendProcessInstanceById(processInstanceId);
	}
	
	/**
	 * 废弃
	 * @param authenticatedUserId
	 * @param taskId
	 * @param processInstanceId
	 * @param approvalComment
	 * @param approvalAttachments
	 * @param variables
	 * @throws Exception
	 */
	public void abandonTask(String authenticatedUserId,String taskId,String processInstanceId,ApprovalComment approvalComment,List<ApprovalAttachment> approvalAttachments,Map<String, Object> variables) throws Exception{
		this.handleCommentAndAttachment(authenticatedUserId, taskId, processInstanceId, approvalComment.getFullMessage(), approvalAttachments, ActivitiConstants.APPROVAL_TYPE_ABANDON);
		
		runtimeService.suspendProcessInstanceById(processInstanceId);
	}
	
	/**
	 * 激活
	 * @param authenticatedUserId
	 * @param processDefinitionKey
	 * @param businessKey   
	 * @param approvalComment
	 * @param approvalAttachments
	 * @param variables
	 * @throws Exception
	 */
	public void activateTask(String authenticatedUserId,String processDefinitionKey,String businessKey,ApprovalComment approvalComment,List<ApprovalAttachment> approvalAttachments,Map<String, Object> variables) throws Exception{
		String processInstanceId = null;
		if(SiriusStringUtils.isNotBlank(businessKey)){
			HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()
					                                    .processDefinitionKey(processDefinitionKey)
					                                    .processInstanceBusinessKey(businessKey).singleResult();
			processInstanceId = hpi.getId();
		}
		
		runtimeService.activateProcessInstanceById(processInstanceId);
		
		Task task = taskService.createTaskQuery()
	               .processDefinitionKey(processDefinitionKey)
	               .processInstanceId(processInstanceId).active().singleResult();
		
		String taskId = task.getId();
		
		this.handleCommentAndAttachment(authenticatedUserId, taskId, processInstanceId, approvalComment.getFullMessage(), approvalAttachments, ActivitiConstants.APPROVAL_TYPE_ACTIVATE);
	}
	
	/**
	 * 处理审批意见和审批附件
	 * @param authenticatedUserId
	 * @param taskId
	 * @param processInstanceId
	 * @param approvalComment
	 * @param approvalAttachments
	 * @param approvalType
	 */
	private void handleCommentAndAttachment(String authenticatedUserId,String taskId,String processInstanceId,String approvalComment,List<ApprovalAttachment> approvalAttachments,String approvalType){
		Authentication.setAuthenticatedUserId(authenticatedUserId);
		Comment comment = taskService.addComment(taskId, processInstanceId, approvalType, approvalComment);
		
		if(!SiriusCollectionUtils.isEmpty(approvalAttachments)){
			for(ApprovalAttachment approvalAttachment : approvalAttachments){
				taskService.createAttachment(comment.getId(), taskId, processInstanceId, approvalAttachment.getFileName(), approvalAttachment.getFileDescription(), approvalAttachment.getFileUrl());
			}
		}
	}
	
	public PagedResultVo<TaskVo> queryTodoTasks(String userId,String processDefinitionKey,List<ActivitiQueryCondition> conditions,Integer pageNo,Integer pageSize){
		TaskQuery taskQuery = taskService.createTaskQuery()
		        .processDefinitionKey(processDefinitionKey)
		        .taskCandidateOrAssigned(userId)
		        .active();
		if(!SiriusCollectionUtils.isEmpty(conditions)){
			for(ActivitiQueryCondition con : conditions){
				if(ActivitiConstants.QUERY_TYPE_EQUAL.equals(con.getQueryType())){
					taskQuery = taskQuery.processVariableValueEquals(con.getQuerykey(), con.getQueryValue());
				}else if(ActivitiConstants.QUERY_TYPE_LIKE.equals(con.getQueryType())){
					taskQuery = taskQuery.processVariableValueLike(con.getQuerykey(), "%"+con.getQueryValue()+"%");
				}
			}
		}
		
		Long total = taskQuery.count();
		
		List<TaskVo> rows = new ArrayList<TaskVo>();
		PagedResultVo<TaskVo> result = new PagedResultVo<TaskVo>(0L,rows);
		if(total<=0){
			return result;
		}
		List<Task> tasks = taskQuery.orderByTaskAssignee().desc().orderByTaskPriority().desc()
		                            .listPage((pageNo-1)*pageSize, pageSize);
		if(SiriusCollectionUtils.isEmpty(tasks)){
			return result;
		}
		
		String processInstanceId = null;
		ProcessInstance pi = null;
		TaskVo taskVo = null;
		for(Task task : tasks){
			if(task==null){
				continue;
			}
			
			processInstanceId = task.getProcessInstanceId();
			
			pi = runtimeService.createProcessInstanceQuery()
					.processDefinitionKey(processDefinitionKey)           
					.processInstanceId(processInstanceId).active().singleResult();
			if(pi==null || !SiriusStringUtils.isNumeric(pi.getBusinessKey())){
				continue;
			}
			
			taskVo = new TaskVo(processInstanceId, task.getId(), task.getTaskDefinitionKey(), task.getAssignee(), pi.getBusinessKey());
			rows.add(taskVo);
		}
		result.setTotal(total);
		result.setRows(rows);
		return result;
	}
	
	public PagedResultVo<ApprovalComment> queryPagedApprovalHistory(String businessKey,String processDefinitionKey) {
		PagedResultVo<ApprovalComment> result = new PagedResultVo<ApprovalComment>(0L,new ArrayList<ApprovalComment>());
		
		if(SiriusStringUtils.isBlank(businessKey)){
			return result;
		}
		
		HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()
				                                    .processInstanceBusinessKey(businessKey)
				                                    .processDefinitionKey(processDefinitionKey)
				                                    .singleResult();
		String processInstanceId = hpi.getId();
		
		List<Comment> processInstanceComments = taskService.getProcessInstanceComments(processInstanceId);
		
		if(SiriusCollectionUtils.isEmpty(processInstanceComments)){
			return result;
		}
		
		List<Attachment> processInstanceAttachments = taskService.getProcessInstanceAttachments(processInstanceId);
		
		String fileRootUrl=sysParamService.getCacheSysParam(SysParamConstants.FILE_STATIC_ROOT_URL);
		
		List<ApprovalComment> approvalComments = new ArrayList<ApprovalComment>();
		ApprovalComment approvalComment = null;
		ApprovalAttachment approvalAttachment = null;
		List<ApprovalAttachment> approvalAttachments = null;
		for(Comment c : processInstanceComments){
			if(!(ActivitiConstants.APPROVAL_TYPE_PASS.equals(c.getType())
				 || ActivitiConstants.APPROVAL_TYPE_REJECT.equals(c.getType())
				 || ActivitiConstants.APPROVAL_TYPE_REFUSE.equals(c.getType())
				 || ActivitiConstants.APPROVAL_TYPE_ABANDON.equals(c.getType())
				 || ActivitiConstants.APPROVAL_TYPE_ACTIVATE.equals(c.getType()))){
				continue;
			}
			
			approvalComment = new ApprovalComment(c);
			
			HistoricTaskInstance taskInstance = historyService.createHistoricTaskInstanceQuery()
					                                          .processDefinitionKey(processDefinitionKey)
					                                          .taskId(c.getTaskId()).singleResult();
			approvalComment.setTaskName(taskInstance.getName());
			
			if(!SiriusCollectionUtils.isEmpty(processInstanceAttachments)){
				approvalAttachments = new ArrayList<ApprovalAttachment>();
				for(Attachment att : processInstanceAttachments){
					if(att!= null && att.getTaskId().equals(c.getTaskId()) && att.getType().equals(c.getId())){
						approvalAttachment = new ApprovalAttachment(att);
						approvalAttachment.setFileFullUrl(fileRootUrl +"/"+ att.getUrl());
						approvalAttachments.add(approvalAttachment);
					}
				}
				approvalComment.setAttachments(approvalAttachments);
			}
			approvalComments.add(approvalComment);
		}
		
		if(!SiriusCollectionUtils.isEmpty(approvalComments)){
			Collections.sort(approvalComments, new Comparator<ApprovalComment>(){
				@Override
				public int compare(ApprovalComment o1, ApprovalComment o2) {
					return (int) (o2.getApprovalTime().getTime()-o1.getApprovalTime().getTime());
				}
			});
		}
		result.setTotal(Integer.valueOf(approvalComments.size()).longValue());
		result.setRows(approvalComments);
		
		return result;
	}
}
