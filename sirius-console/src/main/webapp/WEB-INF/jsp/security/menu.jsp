<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单管理</title>
<%@include file="../common/head.jsp"%>

<script type="text/javascript" src="${ctx}/js/security/menu.js"></script>
</head>
<body>
<input type="hidden" id="ctx" value="${ctx}">

<div id="cc" class="easyui-layout"  style="width: 100%; height: 520px; margin-left: auto; margin-right: auto">
    <div id="treeGridDiv" data-options="region:'center',border:false"  style="height: 400px;">
		<table id="menuDg"></table>
	</div>
	<!-- 新增对话框 -->
	<div id="menuDiv" style="width:650px;height:350px;display: none" align="center">
	   <form id="menuDialogForm">
	   <sec:csrfInput />
	   
	   <input id="id" name="id" type="hidden">
	    <table cellspacing="1" cellpadding="0" class="tb_searchbar">
			<tr>
				<td class="td_title" width="15%" align="left">菜单名称</td>
				<td align="left">
				  <input id="menuName" name="menuName" style="width:95%;" class="easyui-validatebox" data-options="required:true"/>
                </td>
			</tr>
			<tr>
				<td class="td_title" width="15%" align="left">关联资源</td>
				<td align="left" style="padding-right:1px;">
				  <input id="resourceId" name="resourceId" style="width:95%;"/>
                </td>
			</tr>
			<tr>
				<td class="td_title" width="15%" align="left">功能图标</td>
				<td align="left">
				  <input id="menuIcon" name="menuIcon" style="width:95%;"/>
                </td>
			</tr>
			<tr>
				<td class="td_title" width="15%" align="left">功能排序</td>
				<td align="left">
				  <input id="menuSeq" name="menuSeq" class="easyui-numberbox" data-options="min:0,precision:0" style="width: 95%;"/>
                </td>
			</tr>
			<tr>
				<td class="td_title" width="15%" align="left">上级功能</td>
				<td align="left">
				    <input id="parentId" name="parentId" style="width:80%;"/>
				    &nbsp;
				    <a href="javascript:void(0);" style="text-decoration:none;color:#6495ed;"  onclick="cleanParent();">置空</a>
                </td>
			</tr>
	     </table>
	    </form>
    </div>
</div>
</body>
</html>