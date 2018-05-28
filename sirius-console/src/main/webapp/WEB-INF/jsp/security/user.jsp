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

<script type="text/javascript" src="${ctx}/js/security/user.js?v=10"></script>
</head>
<body>
<input type="hidden" id="ctx" value="${ctx}">

<div id="cc" class="easyui-layout"  style="width: 100%; height: 465px; margin-left: auto; margin-right: auto">
    <div data-options="region:'north',iconCls:'icon-mysearch ',split:true" style="height:100px;">
		<form id="queryConditionUserForm">	
		<sec:csrfInput />
		  <table cellspacing="1" cellpadding="0" class="tb_searchbar">
			<tr>
			    <td class="td_title" width="10%">用户名</td>
				<td>
				  <input id="username" name="username" style="width:150px;"/>
                </td>
                <td class="td_title" width="10%">手机号</td>
				<td>
				  <input id="mobile" name="mobile" style="width:150px;"/>
                </td>
			</tr>
			<tr>
				<td colspan="4" align="right">
				     <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="queryUsers();" style="margin-left: 150px;">查询</a>
					 <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="clearQueryCondition();">取消</a>
				</td>
			</tr>
         </table>
       </form>
	</div>
    <div data-options="region:'center',border:false" >
		<table id="userDg"></table>
		<div id="userDgToolbar" class="easyui-panel" style="padding: 5px;">
		    <sec:authorize access="hasPermission('user','add')">
		        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="openAddOrModifyUserDialog('add')">新增</a>
		    </sec:authorize>
		    <sec:authorize access="hasPermission('user','modify')">
		        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="openAddOrModifyUserDialog('edit')">修改</a>
		    </sec:authorize>
		    <sec:authorize access="hasPermission('user','remove')">
		        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="removeUser()">删除</a>
		    </sec:authorize>
		    <sec:authorize access="hasPermission('user','authenticate')">
		        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-myprivilege'" onclick="authenticateUser()">授权</a>
		    </sec:authorize>
		    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="clearSelected()">取消选中</a> 
        </div>
	</div>
</div>

<div id="userDiv" style="display: none;">
    <form id="userForm">
    <sec:csrfInput />
    <input type="hidden" id="id" name="id">
	    <table cellspacing="1" cellpadding="0" class="tb_searchbar">
			<tr>
			    <td class="td_title" width="20%">用户名</td>
				<td>
				  <input id="username" name="username" style="width:200px;" placeholder="用户名只能是字母或数字" class="easyui-validatebox" data-options="required:true,validType:'charOrNum'"/>
	            </td>
	        </tr>
	        <tr class="passwordTr" style="display: none;">
			    <td class="td_title" width="20%">密码</td>
				<td>
				  <input id="password" name="password" style="width:200px;" type="password" placeholder="密码只能是字母或数字" class="easyui-validatebox" data-options="required:true,validType:'charOrNum'"/>
	            </td>
	        </tr>
	        <tr class="passwordTr" style="display: none;">
			    <td class="td_title" width="20%">确认密码</td>
				<td>
				  <input id="rePassword" name="rePassword" style="width:200px;" type="password" class="easyui-validatebox" data-options="validType:'equals[\'#password\']'"/>
	            </td>
	        </tr>
	        <tr>
			    <td class="td_title" width="20%">手机</td>
				<td>
				  <input id="mobile" name="mobile" style="width:200px;" class="easyui-validatebox" data-options="validType:'mobile'"/>
	            </td>
	        </tr>
	        <tr>
			    <td class="td_title" width="20%">座机</td>
				<td>
				  <input id="telephone" name="telephone" style="width:200px;" class="easyui-validatebox" data-options="validType:'fixPhoneNum'"/>
	            </td>
	        </tr>
	        <tr>
			    <td class="td_title" width="20%">邮箱</td>
				<td>
				  <input id="email" name="email" style="width:200px;" class="easyui-validatebox" data-options="validType:'email'"/>
	            </td>
	        </tr>
	        <tr class="isvalidTr">
			    <td class="td_title" width="20%">有效性</td>
				<td>
				  <input id="isvalid" name="isvalid" style="width:200px;"/>
	            </td>
	        </tr>
        </table>
    </form>
</div>

<div id="authenticateUserDiv" style="display: none;">
    <table id="authenticateUserDg"></table>
</div>
</body>
</html>