package com.bcyj99.sirius.web.order.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bcyj99.sirius.core.authentication.access.MyUser;
import com.bcyj99.sirius.core.bus.order.service.BusOrderService;
import com.bcyj99.sirius.core.bus.order.vo.BusOrder;
import com.bcyj99.sirius.core.common.base.BaseController;
import com.bcyj99.sirius.core.common.constants.ActivitiConstants;
import com.bcyj99.sirius.core.common.page.PagedParamVo;
import com.bcyj99.sirius.core.common.page.PagedResultVo;
import com.bcyj99.sirius.core.common.vo.ResultVo;
import com.bcyj99.sirius.core.workflow.vo.ApprovalComment;

@Controller
@RequestMapping("/busOrder")
public class BusOrderController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(BusOrderController.class);
	
	@Autowired
	private BusOrderService busOrderService;
	
	@RequestMapping(value="/myTodoOrdersPage",name="访问我的待办订单页面")
	public String myTodoOrdersPage() {
		return "/order/orderMain";
	}
	
	@RequestMapping(value="/myOrderCommonPage",name="访问订单公用页面")
	public String myOrderCommonPage() {
		return "/order/orderCommon";
	}
	
	@PostMapping(value="/queryTodoOrders",name="分页查询我的待办订单")
	@ResponseBody
	public PagedResultVo<BusOrder> queryTodoOrders(BusOrder condition,PagedParamVo pagedParam) {
		MyUser user = this.getCurrentUser();
		String userId = user.getId().toString();
		return busOrderService.queryTodoOrders(userId, condition, pagedParam.getPage(), pagedParam.getRows());
	}
	
	@PostMapping(value="/queryOrderApprovalHistory",name="分页查询订单的审批记录")
	@ResponseBody
	public PagedResultVo<ApprovalComment> queryOrderApprovalHistory(Long orderId) {
		return busOrderService.queryOrderApprovalHistory(orderId.toString());
	}
	
	@PostMapping(value="/addOrder",name="新增订单")
	@ResponseBody
	public ResultVo<String> addOrder(@RequestBody BusOrder busOrder) {
		try {
			logger.info("新增订单-开始....");
			
			busOrder.setCreateDate(new Date());
			MyUser user = this.getCurrentUser();
			busOrder.setCreateBy(user.getId().toString());
			
			String busOrderStr = JSON.toJSONString(busOrder);
			logger.info("新增订单-busOrderStr:{}",busOrderStr);
			busOrderService.addOrder(busOrder);
			logger.info("新增订单-成功.");
			return new ResultVo<String>(200,"ok",null);
		} catch (Exception e) {
			logger.error("新增订单-失败.", e);
			return new ResultVo<String>(400,"error",null);
		}
	}
	
	@PostMapping(value="/submitOrderApproval",name="提交订单审批")
	@ResponseBody
	public ResultVo<String> submitOrderApproval(HttpServletRequest request,@RequestBody BusOrder busOrder) {
		try {
			logger.info("订单-提交审批-开始....");
			String busOrderStr = JSON.toJSONString(busOrder);
			logger.info("订单-提交审批-busOrderStr:{}",busOrderStr);
			
			MyUser user = this.getCurrentUser();
			if(user == null){
				logger.info("订单-提交审批:用户信息有误");
				return new ResultVo<String>(201,"用户信息有误",null);
			}
			ApprovalComment approvalComment = busOrder.getApprovalComment();
			if(approvalComment == null){
				logger.info("订单-提交审批:获取审核信息失败");
				return new ResultVo<String>(202,"获取审核信息失败",null);
			}
			String approvalType = approvalComment.getApprovalType();
			
			if(ActivitiConstants.APPROVAL_TYPE_PASS.equals(approvalType)){
				busOrderService.passOrderApproval(user, busOrder);
			}else if(ActivitiConstants.APPROVAL_TYPE_REJECT.equals(approvalType)){
				busOrderService.rejectOrderApproval(user, busOrder);
			}else if(ActivitiConstants.APPROVAL_TYPE_REFUSE.equals(approvalType)){
				busOrderService.refuseOrderApproval(user, busOrder);
			}else if(ActivitiConstants.APPROVAL_TYPE_ABANDON.equals(approvalType)){
				busOrderService.abandonOrderApproval(user, busOrder);
			}else if(ActivitiConstants.APPROVAL_TYPE_ACTIVATE.equals(approvalType)){
				busOrderService.activateOrderApproval(user, busOrder);
			}
			logger.info("订单-提交审批-成功");
			return new ResultVo<String>(200,"ok",null);
		} catch (Exception e) {
			logger.error("订单-提交审批-失败", e);
			return new ResultVo<String>(400,"error",null);
		}
	}
}
