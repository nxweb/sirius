<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资源管理</title>
<%@include file="../common/head.jsp"%>

<script type="text/javascript" src="${ctx}/js/security/resource.js"></script>
</head>
<body>
<input type="hidden" id="ctx" value="${ctx}">

<div id="cc" class="easyui-layout"  style="width: 100%; height: 465px; margin-left: auto; margin-right: auto">
    <div data-options="region:'north',iconCls:'icon-mysearch ',split:true" style="height:100px;">
		<form id="queryConditionResForm">	
		<sec:csrfInput />
		  <table cellspacing="1" cellpadding="0" class="tb_searchbar">
			<tr>
			    <td class="td_title" width="10%">名称</td>
				<td>
				  <input id="resourceName" name="resourceName" style="width:150px;"/>
                </td>
                <td class="td_title" width="10%">类型</td>
				<td>
				  <input id="resourceType" name="resourceType" style="width:150px;"/>
                </td>
			</tr>
			<tr>
			    <td class="td_title" width="10%">路径</td>
				<td>
				  <input id="resourceUrl" name="resourceUrl" style="width:150px;"/>
                </td>
                <td class="td_title" width="10%">说明</td>
				<td>
				  <input id="resourceDesc" name="resourceDesc" style="width:150px;"/>
                </td>
			</tr>	
			<tr>
				<td colspan="4" align="right">
				     <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="queryData();" style="margin-left: 150px;">查询</a>
					 <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="clearQueryCondition();">取消</a>
				</td>
			</tr>
         </table>
       </form>
	</div>
    <div data-options="region:'center',border:false" >
		<table id="resourceDg"></table>
		<div id="resourceDgToolbar" class="easyui-panel" style="padding: 5px;">
		    <sec:authorize access="hasPermission('resource','add')">
		        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="addOrEditResourceDialog('add')">新增</a>
		    </sec:authorize>
		    <sec:authorize access="hasPermission('resource','modify')">
		    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="addOrEditResourceDialog('edit')">修改</a>
		    </sec:authorize>
		    <sec:authorize access="hasPermission('resource','remove')">
		    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="removeResource()">删除</a>
		    </sec:authorize>
		    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="clearSelected()">取消选中</a> 
        </div>
	</div>
</div>

<div id="resourceDiv" style="display: none;">
    <form id="resourceForm">
    <sec:csrfInput />
    <input id="id" name="id" type="hidden">
	    <table cellspacing="1" cellpadding="0" class="tb_searchbar">
			<tr>
			    <td class="td_title" width="15%">名称</td>
				<td>
				  <input id="resourceName" name="resourceName" style="width:350px;"/>
	            </td>
	        </tr>
	        <tr>
			    <td class="td_title" width="15%">编码</td>
				<td>
				  <input id="resourceCode" name="resourceCode" style="width:350px;"/>
	            </td>
	        </tr>
	        <tr>
			    <td class="td_title" width="15%">类型</td>
				<td>
				  <input id="resourceType" name="resourceType" style="width:350px;"/>
	            </td>
	        </tr>
	        <tr>
			    <td class="td_title" width="15%">路径</td>
				<td>
				  <input id="resourceUrl" name="resourceUrl" style="width:350px;"/>
	            </td>
	        </tr>
	        <tr>
			    <td class="td_title" width="15%">说明</td>
				<td>
				  <input id="resourceDesc" name="resourceDesc" style="width:350px;"/>
	            </td>
	        </tr>
        </table>
    </form>
</div>
</body>
</html>