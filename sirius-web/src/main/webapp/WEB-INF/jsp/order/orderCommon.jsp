<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单公用页面</title>
<%@include file="../common/head.jsp"%>

<script type="text/javascript" src="${ctx}/js/order/orderCommon.js?v=20180114"></script>
</head>
<body>
<input type="hidden" id="ctx" value="${ctx}">
<input type="hidden" id="taskDefinitionKey" value="${taskDefinitionKey}">
<input type="hidden" id="taskId" value="${taskId}">

<div id="cc" class="easyui-layout"  style="width: 100%; height: auto; margin-left: auto; margin-right: auto">
    <div data-options="iconCls:'icon-mysearch ',split:true">
		<form id="orderAddOrEditForm">	
		<sec:csrfInput />
		
		<input type="hidden" id="orderId" value="${orderId}">
		  <table cellspacing="1" cellpadding="0" class="tb_searchbar">
			<tr class="modify">
			    <td class="td_title" width="10%">客户名</td>
				<td>
				  <input name="customerName" style="width:150px;"/>
                </td>
                <td class="td_title" width="10%">金额</td>
				<td>
				  <input name="amount" style="width:150px;"/>
                </td>
			</tr>
			<tr class="modify">
                <td class="td_title" width="10%">备注</td>
				<td colspan="3">
				  <input name="remark" style="width:150px;"/>
                </td>
			</tr>
			
			
			<tr class="view">
			    <td class="td_title" width="10%">客户名</td>
				<td>
				  <label class="customerName"></label>
                </td>
                <td class="td_title" width="10%">金额</td>
				<td>
				  <label class="amount"></label>
                </td>
			</tr>
			<tr class="view">
                <td class="td_title" width="10%">备注</td>
				<td colspan="3">
				  <label class="remark"></label>
                </td>
			</tr>
         </table>
       </form>
	</div>
</div>

<table id="approvalHistory"></table>

<div id="attachmentsDialog" style="display: none;">
		<ul id="attachmentsDialogUl"></ul>
	</div>	

<div class="verify-content" style="margin-top: 20px;">
		<div class="" style="margin-top: 20px;">
			<form id="approvalForm" enctype="multipart/form-data">
				<table cellspacing="1" cellpadding="0" class="tb_searchbar product_dialog_table" style="text-align: center;">
					<tbody>
						<tr style="height: 40px;">
							<th width="*" colspan="4" style="text-align: left;"><span style="margin-left: 10px;">审批</span></th>
						</tr>
						<tr>
							<td class="ver-header-td" rowspan="5" style="width: 10%">审批</td>
							<td style="width: 15%">审批结果</td>
							<td  class="operat-td" style="text-align: left;">
								<div class='agree_result' style="margin-left: 10px;">
									<span id="verify_aggree" >
										<input name="approvalType" type="radio" value="pass" checked="checked"/><label for="verify_aggree">通过</label>
									</span>
									<span id="verify_reject">
									    <input name="approvalType" type="radio" value="reject"/><label>驳回</label>
									</span>
									<span id="verify_refuse">
									    <input name="approvalType" type="radio" value="refuse"/><label>拒绝</label>
								    </span>
									<span id="verify_scrap">
									    <input name="approvalType" type="radio" value="abandon"/><label>废弃</label>
								    </span>
								    <span id="verify_activate">
									    <input name="approvalType" type="radio" value="activate"/><label>激活</label>
								    </span>
								</div>
								<!-- /驳回目标 -->
								<div class='reject_body' style="margin-left: 10px; display: none;">
									<label>请选择驳回流程：</label>
									<select style="width: 160px;" class='reject_select'>
										<option value="-1">--请选择驳回流程--</option>
									</select>
									
									<span class='reject_tip' style="margin-left: 20px; color: green;"></span>
									<div class="jump-tip-div" style="display: none;">
										<input type="checkbox" name="jumpFlag" value="1"/><span class="jump-tip" style="color:red;"></span>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td style="width: 15%">审批选项</td>
							<td>
								<table cellspacing="1" cellpadding="0" class="tb_searchbar" 
									style="text-align: left; height: 70px; width: 99%;margin-left: 1px;">
									<tbody>
										<tr style="background-color: #ccc;color:black;font-weight: bold;">
											<td width="200" align="left">优先级   （下一任务办理人）</td>
											<td width="120" align="left">限时完成    （非必填）</td>
										</tr>
										<tr>
											<td>
												<input id="p_50" type="radio" name="priority" value="50" checked="checked"/><label for="p_50">普通</label>
												<input id="p_60" type="radio" name="priority" value="60"/><label for="p_60">紧急</label>
												<input id="p_70" type="radio" name="priority" value="70"/><label for="p_70">特急</label>
											</td>
											<td>
												<input id="dueDate" name="dueDate" class="easyui-datebox dueDate"/>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
						
						<tr>
							<td style="width: 15%">审批意见</td>
							<td class="operat-td" style="text-align: left; height: 80px;">
								<textarea class="fullMessage" name="fullMessage" style="width: 95%; height:60px; resize: none; color: black;"></textarea>
							</td>
						</tr>
						
						<tr class="nextNodeTr">
							<td style="width: 15%">下一流程</td>
							<td class="operat-td" style="text-align: left; height: 80px;">
								<select id="nextNodeSelect" name="nextNode">
								    <option value="cityManagerApproval" selected="selected">城市经理审核</option>
								    <option value="cityViceManagerApproval">城市副理审核</option>
								</select>
							</td>
						</tr>
						
						<tr>
							<td style="width: 18%">审批附件</td>
							<td align="left">
							   	<input name="approvalAttachment" style="width:250px;" type="file" multiple="multiple"/>
							   	<a href="javascript:void(0);" style="margin-left: 20px;" class="easyui-linkbutton" iconCls="icon-save" vtype="save" onclick="uploadApprovalAttachment();">保存附件</a>
					  	   		<ul id="attachementsUl">
					  	   		</ul>
			                </td>
						</tr>
						
						<tr class="btn_tr">
							<td colspan="4">
								<a href="javascript:void(0);" class="easyui-linkbutton" style="margin-left: 130px;" onclick="submitApproval()">提交申请</a>
								<span class="show_at_agree" style="color: green; margin-left: 18px;">*（提交前请先确认业务已办理完成）</span>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>

</body>
</html>