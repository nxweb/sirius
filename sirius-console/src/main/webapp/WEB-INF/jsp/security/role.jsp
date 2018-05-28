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

<script type="text/javascript" src="${ctx}/js/security/role.js?v=20180106"></script>
</head>
<body>
<input type="hidden" id="ctx" value="${ctx}">

<div id="cc" class="easyui-layout"  style="width: 100%; height: 465px; margin-left: auto; margin-right: auto">
    <div data-options="region:'north',iconCls:'icon-mysearch ',split:true" style="height:100px;">
		<form id="queryConditionRoleForm">	
		<sec:csrfInput />
		  <table cellspacing="1" cellpadding="0" class="tb_searchbar">
			<tr>
			    <td class="td_title" width="10%">角色名称</td>
				<td>
				  <input id="roleName" name="roleName" style="width:150px;"/>
                </td>
                <td class="td_title" width="10%">角色编码</td>
				<td>
				  <input id="roleCode" name="roleCode" style="width:150px;"/>
                </td>
			</tr>
			<tr>
				<td colspan="4" align="right">
				     <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="queryRoles();" style="margin-left: 150px;">查询</a>
					 <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="clearQueryCondition();">取消</a>
				</td>
			</tr>
         </table>
       </form>
	</div>
    <div data-options="region:'center',border:false" >
		<table id="roleDg"></table>
		<div id="roleDgToolbar" class="easyui-panel" style="padding: 5px;">
		    <sec:authorize access="hasPermission('role','add')">
		        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="addOrModifyRoleDialog('add')">新增</a>
		    </sec:authorize>
		    <sec:authorize access="hasPermission('role','modify')">
		    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="addOrModifyRoleDialog('edit')">修改</a>
		    </sec:authorize>
		    <sec:authorize access="hasPermission('role','remove')">
		    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="removeRole()">删除</a>
		    </sec:authorize>
		    <sec:authorize access="hasPermission('role','authenticate')">
		    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-myprivilege'" onclick="authenticateRole()">授权</a>
		    </sec:authorize>
		    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="clearSelected()">取消选中</a> 
        </div>
	</div>
</div>

<div id="roleDiv" style="display: none;">
    <form id="roleForm">
    <sec:csrfInput />
    <input type="hidden" id="id" name="id">
	    <table cellspacing="1" cellpadding="0" class="tb_searchbar">
			<tr>
			    <td class="td_title" width="25%">角色名称</td>
				<td>
				  <input id="roleName" name="roleName" style="width:200px;"/>
	            </td>
	        </tr>
	        <tr>
			    <td class="td_title" width="25%">角色编码</td>
				<td>
				  <input id="roleCode" name="roleCode" style="width:200px;"/>
	            </td>
	        </tr>
	        <tr>
			    <td class="td_title" width="25%">角色说明</td>
				<td>
				  <input id="roleDesc" name="roleDesc" style="width:200px;"/>
	            </td>
	        </tr>
        </table>
    </form>
</div>

<div id="authenticateRoleDiv" style="display: none;">
    <form id="authenticateRoleForm">
    <sec:csrfInput />
    <input type="hidden" id="id" name="id">
	    <table cellspacing="1" cellpadding="0" class="tb_searchbar">
			<tr>
			    <td class="td_title" width="10%">资源名称</td>
				<td>
				  <input id="resourceName" name="resourceName" style="width:150px;"/>
				  <a href="javascript:void(0);" class="easyui-linkbutton"  data-options="iconCls:'icon-search'" onclick="queryPagedSecuResources()">搜索</a>
                </td>
			</tr>
        </table>
    </form>
    
    <table id="authenticateRoleDg"></table>
</div>

</body>
</html>