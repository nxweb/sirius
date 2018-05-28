<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sirius" uri="http://ntt123.sirius.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的待办订单</title>
<%@include file="../common/head.jsp"%>

<sirius:script type="text/javascript" src="${ctx}/js/order/orderMain.js"></sirius:script>
</head>
<body>
<input type="hidden" id="ctx" value="${ctx}">

<div id="cc" class="easyui-layout"  style="width: 100%; height: 465px; margin-left: auto; margin-right: auto">
    <div data-options="region:'north',iconCls:'icon-mysearch ',split:true" style="height:100px;">
		<form id="queryOrderForm">	
		<sec:csrfInput />
		  <table cellspacing="1" cellpadding="0" class="tb_searchbar">
			<tr>
			    <td class="td_title" width="10%">订单号</td>
				<td>
				  <input name="orderNo" style="width:150px;"/>
                </td>
                <td class="td_title" width="10%">客户名</td>
				<td>
				  <input name="customerName" style="width:150px;"/>
                </td>
			</tr>
			<tr>
				<td colspan="4" align="right">
				     <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="queryOrderData();" style="margin-left: 150px;">查询</a>
					 <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="clearOrderCondition();">取消</a>
				</td>
			</tr>
         </table>
       </form>
	</div>
    <div data-options="region:'center',border:false" >
		<table id="orderDataGrid"></table>
		<div id="orderDataGridToolbar" class="easyui-panel" style="padding: 5px;">
		    <sec:authorize access="hasPermission('order','add')">
		        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="addOrEditOrderTab()">新增</a>
		    </sec:authorize>
		    <sec:authorize access="hasPermission('order','remove')">
		    	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="removeSysParam()">删除</a>
		    </sec:authorize>
		    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="clearSelected()">取消选中</a> 
        </div>
	</div>
</div>

<div id="orderDiv" style="display: none;">
    <form id="orderForm">
    <sec:csrfInput />
    <input id="id" name="id" type="hidden">
	    <table cellspacing="1" cellpadding="0" class="tb_searchbar">
			<tr>
			    <td class="td_title" width="15%">参数名称</td>
				<td>
				  <input id="paramKey" name="paramKey" style="width:350px;"/>
	            </td>
	        </tr>
	        <tr>
			    <td class="td_title" width="15%">参数值</td>
				<td>
				  <input id="paramValue" name="paramValue" style="width:350px;"/>
	            </td>
	        </tr>
	        <tr>
			    <td class="td_title" width="15%">参数说明</td>
				<td>
				  <input id="paramRemark" name="paramRemark" style="width:350px;"/>
	            </td>
	        </tr>
        </table>
    </form>
</div>
</body>
</html>