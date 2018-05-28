package com.bcyj99.sirius.core.bus.order.service;

import com.bcyj99.sirius.core.authentication.access.MyUser;
import com.bcyj99.sirius.core.bus.order.vo.BusOrder;
import com.bcyj99.sirius.core.common.page.PagedResultVo;
import com.bcyj99.sirius.core.workflow.vo.ApprovalComment;
import com.bcyj99.sirius.core.workflow.vo.ApprovalVo;

public interface BusOrderService {

	PagedResultVo<BusOrder> queryTodoOrders(String userId,BusOrder condition,Integer pageNo,Integer pageSize);
	
	PagedResultVo<ApprovalComment> queryOrderApprovalHistory(String orderId);
	
	void addOrder(BusOrder busOrder) throws Exception;
	
	BusOrder quereyOrderById(Long orderId);
	
	void passOrderApproval(MyUser user,BusOrder busOrder) throws Exception;
	
	void rejectOrderApproval(MyUser user,BusOrder busOrder) throws Exception;
	
	void refuseOrderApproval(MyUser user,BusOrder busOrder) throws Exception;
	
	void abandonOrderApproval(MyUser user,BusOrder busOrder) throws Exception;
	
	void activateOrderApproval(MyUser user,BusOrder busOrder) throws Exception;
}
