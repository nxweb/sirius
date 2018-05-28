package com.bcyj99.sirius.core.workflow.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.runtime.ProcessInstance;

import com.bcyj99.sirius.core.common.page.PagedParamVo;
import com.bcyj99.sirius.core.common.page.PagedResultVo;
import com.bcyj99.sirius.core.workflow.vo.ActivitiQueryCondition;
import com.bcyj99.sirius.core.workflow.vo.ApprovalAttachment;
import com.bcyj99.sirius.core.workflow.vo.ApprovalComment;
import com.bcyj99.sirius.core.workflow.vo.ProcessDefinitionVo;
import com.bcyj99.sirius.core.workflow.vo.ReviewHistoryVo;
import com.bcyj99.sirius.core.workflow.vo.TaskVo;

public interface ActivitiService {
    
	/**
	 * 部署流程
	 * @param zipInputStream
	 */
	void deploy(ZipInputStream zipInputStream);
	
	/**
	 * 查询流程
	 * @param zipInputStream
	 */
	PagedResultVo<ProcessDefinitionVo> queryPagedProcessDefinitions(ProcessDefinitionVo processDefinitionVo,PagedParamVo param);
	
	/**
	 * 生成流程定义的流程图
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	void generateDiagramImageProcessDefinition(HttpServletResponse response,String processDefinitionId) throws IOException;
	
	/**  
	    * 获取流程图各个节点的具体的信息  
	    * @return  
	    */    
	List<Map<String, Object>> getProcessTrace(String processDefinitionId) throws Exception;
	   
	/**
	 * 生成流程实例的流程图
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	void generateDiagramImageProcessInstance(HttpServletResponse response,String processInstanceId) throws IOException;
	
	/**
	 * 删除流程
	 * @param zipInputStream
	 */
	void removeDeployment(Long id);
	
	/**
	 * 查看流程图
	 * @param response
	 * @param deploymentId
	 * @param resourceName
	 * @throws IOException
	 */
	@Deprecated
	void viewDiagramImage(HttpServletResponse response,String deploymentId, String resourceName) throws IOException;
	
	/**
	 * 根据id启动流程
	 * @param processDefinitionKey
	 * @param businessKey
	 * @param variables
	 * @return
	 */
	ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String businessKey, Map<String, Object> variables);
	
	void passTask(String authenticatedUserId,String processInstanceId,ApprovalComment approvalComment,List<ApprovalAttachment> approvalAttachments,Map<String, Object> variables) throws Exception;
	/**
	 * 通过
	 * @param authenticatedUserId   审批人姓名
	 * @param taskId    
	 * @param processInstanceId
	 * @param approvalComment   审批意见
	 * @param approvalAttachments 审批附件
	 */
	void passTask(String authenticatedUserId,String taskId,String processInstanceId,ApprovalComment approvalComment,List<ApprovalAttachment> approvalAttachments,Map<String, Object> variables) throws Exception;
	
	/**
	 * 驳回
	 * @param authenticatedUserId    审批人姓名
	 * @param taskId   当前节点
	 * @param processInstanceId   当前节点
	 * @param destinationActivityId   目标节点
	 * @param approvalComment    审批意见
	 * @param approvalAttachments   审批附件
	 * @param variables
	 */
	void rejectTask(String authenticatedUserId,String taskId,String processInstanceId,String destinationActivityId,ApprovalComment approvalComment,List<ApprovalAttachment> approvalAttachments,Map<String, Object> variables) throws Exception;
	
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
	void refuseTask(String authenticatedUserId,String taskId,String processInstanceId,ApprovalComment approvalComment,List<ApprovalAttachment> approvalAttachments,Map<String, Object> variables) throws Exception;
	
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
	void abandonTask(String authenticatedUserId,String taskId,String processInstanceId,ApprovalComment approvalComment,List<ApprovalAttachment> approvalAttachments,Map<String, Object> variables) throws Exception;
	
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
	void activateTask(String authenticatedUserId,String processDefinitionKey,String businessKey,ApprovalComment approvalComment,List<ApprovalAttachment> approvalAttachments,Map<String, Object> variables) throws Exception;
	
	/**
	 * 查询待办任务
	 * @param userId
	 * @param processDefinitionKey
	 * @param conditions
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PagedResultVo<TaskVo> queryTodoTasks(String userId,String processDefinitionKey,List<ActivitiQueryCondition> conditions,Integer pageNo,Integer pageSize);
	
	/**
	 * 查询审核历史记录
	 * @param businessKey
	 * @param processDefinitionKey
	 * @return
	 */
	PagedResultVo<ApprovalComment> queryPagedApprovalHistory(String businessKey,String processDefinitionKey);
	
	/**
	 * 查询可驳回的节点
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	List<ReviewHistoryVo> findBackAvtivityVo(String taskId) throws Exception;
	
	/**
	 * 驳回
	 * @param taskId
	 * @param activityId
	 * @param variables
	 * @throws Exception
	 */
	@Deprecated
	void rejectProcess(String taskId, String activityId,Map<String, Object> variables) throws Exception;
}
