package com.bcyj99.sirius.core.bus.order.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcyj99.sirius.core.authentication.access.MyUser;
import com.bcyj99.sirius.core.bus.order.dao.BusOrderMapper;
import com.bcyj99.sirius.core.bus.order.service.BusOrderService;
import com.bcyj99.sirius.core.bus.order.vo.BusOrder;
import com.bcyj99.sirius.core.common.constants.ActivitiConstants;
import com.bcyj99.sirius.core.common.page.PagedResultVo;
import com.bcyj99.sirius.core.common.utils.SiriusCollectionUtils;
import com.bcyj99.sirius.core.common.utils.SiriusStringUtils;
import com.bcyj99.sirius.core.workflow.service.ActivitiService;
import com.bcyj99.sirius.core.workflow.vo.ActivitiQueryCondition;
import com.bcyj99.sirius.core.workflow.vo.ApprovalComment;
import com.bcyj99.sirius.core.workflow.vo.TaskVo;

@Service
public class BusOrderServiceImpl implements BusOrderService {

	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private BusOrderMapper busOrderMapper;
	
	@Override
	public PagedResultVo<BusOrder> queryTodoOrders(String userId, BusOrder condition, Integer pageNo,
			Integer pageSize) {
		List<ActivitiQueryCondition> conditions = new ArrayList<ActivitiQueryCondition>();
		if(SiriusStringUtils.isNotBlank(condition.getOrderNo())){
			conditions.add(new ActivitiQueryCondition("orderNo", ActivitiConstants.QUERY_TYPE_LIKE, condition.getOrderNo()));	
		}
		
		PagedResultVo<TaskVo> pagedResultVo = activitiService.queryTodoTasks(userId, ActivitiConstants.PROCESS_DEFINITION_KEY_ORDER, conditions, pageNo, pageSize);
		
		List<BusOrder> rows = new ArrayList<BusOrder>();
		PagedResultVo<BusOrder> result = new PagedResultVo<BusOrder>(0L,rows);
		if(pagedResultVo ==null){
			return result;
		}
		
		Long total = pagedResultVo.getTotal();
		List<TaskVo> taskVos = pagedResultVo.getRows();
		if(SiriusCollectionUtils.isEmpty(taskVos)){
			return result;
		}
		
		for(TaskVo taskVo : taskVos){
			String businessKey = taskVo.getBusinessKey();
			if(SiriusStringUtils.isNumeric(businessKey)){
				BusOrder bo = busOrderMapper.selectByPrimaryKey(Long.parseLong(businessKey));
				bo.setTaskVo(taskVo);
				rows.add(bo);
			}
		}
		result.setTotal(total);
		result.setRows(rows);
		
		return result;
	}
	
	public PagedResultVo<ApprovalComment> queryOrderApprovalHistory(String orderId){
		return activitiService.queryPagedApprovalHistory(orderId, ActivitiConstants.PROCESS_DEFINITION_KEY_ORDER);
	}

	@Override
	public void addOrder(BusOrder busOrder) throws Exception {
		busOrderMapper.insertSelective(busOrder);
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("orderNo", busOrder.getOrderNo());
		variables.put("customerName", busOrder.getCustomerName());
		variables.put("createBy", busOrder.getCreateBy());
		ProcessInstance pi = activitiService.startProcessInstanceByKey(ActivitiConstants.PROCESS_DEFINITION_KEY_ORDER, busOrder.getId().toString(), variables);
		
		activitiService.passTask(busOrder.getCreateBy(), pi.getProcessInstanceId(), busOrder.getApprovalComment(), busOrder.getApprovalAttachments(), null);
	}

	@Override
	public BusOrder quereyOrderById(Long orderId) {
		return busOrderMapper.selectByPrimaryKey(orderId);
	}

	@Override
	public void passOrderApproval(MyUser user, BusOrder busOrder) throws Exception{
		activitiService.passTask(user.getUsername(), 
				busOrder.getTaskVo().getTaskId(), 
				busOrder.getTaskVo().getProcessInstanceId(), 
				busOrder.getApprovalComment(), 
				busOrder.getApprovalAttachments(), 
				null);
	}

	@Override
	public void rejectOrderApproval(MyUser user, BusOrder busOrder) throws Exception {
		activitiService.rejectTask(user.getUsername(), 
				busOrder.getTaskVo().getTaskId(), 
				busOrder.getTaskVo().getProcessInstanceId(), 
				busOrder.getDestActivityId(), 
				busOrder.getApprovalComment(), 
				busOrder.getApprovalAttachments(), 
				null);		
	}

	@Override
	public void refuseOrderApproval(MyUser user, BusOrder busOrder) throws Exception {
		activitiService.refuseTask(user.getUsername(), 
				busOrder.getTaskVo().getTaskId(), 
				busOrder.getTaskVo().getProcessInstanceId(), 
				busOrder.getApprovalComment(), 
				busOrder.getApprovalAttachments(), 
				null);		
	}

	@Override
	public void abandonOrderApproval(MyUser user, BusOrder busOrder) throws Exception {
		activitiService.abandonTask(user.getUsername(), 
				busOrder.getTaskVo().getTaskId(), 
				busOrder.getTaskVo().getProcessInstanceId(), 
				busOrder.getApprovalComment(), 
				busOrder.getApprovalAttachments(), 
				null);		
	}

	@Override
	public void activateOrderApproval(MyUser user, BusOrder busOrder) throws Exception {
		activitiService.activateTask(user.getUsername(), 
				ActivitiConstants.PROCESS_DEFINITION_KEY_ORDER, 
				busOrder.getId().toString(), busOrder.getApprovalComment(), 
				busOrder.getApprovalAttachments(), 
				null);
	}

}
