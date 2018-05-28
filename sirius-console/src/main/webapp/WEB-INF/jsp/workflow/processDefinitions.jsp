<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程部署</title>
<%@include file="../common/head.jsp"%>

<script type="text/javascript" src="${ctx}/js/workflow/processDefinitions.js"></script>
</head>
<body>
<input type="hidden" id="ctx" value="${ctx}">

<div id="cc" class="easyui-layout"  style="width: 100%; height: 495px; margin-left: auto; margin-right: auto">
    <div data-options="region:'north',iconCls:'icon-mysearch ',split:true" style="height:100px;">
		<form id="queryProcessDefinitionForm">	
		<sec:csrfInput />
		  <table cellspacing="1" cellpadding="0" class="tb_searchbar">
			<tr>
			    <td class="td_title" width="10%">名称</td>
				<td colspan="3">
				  <input id="name" name="name" style="width:150px;"/>
                </td>
			</tr>
			<tr>
				<td colspan="4" align="right">
				     <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="queryProcessDefinition();" style="margin-left: 150px;">查询</a>
					 <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="clearProcessDefinitionCondition();">取消</a>
				</td>
			</tr>
         </table>
       </form>
	</div>
    <div data-options="region:'center',border:false" >
		<table id="processDefinitionDataGrid"></table>
		<div id="processDefinitionDataGridToolbar" class="easyui-panel" style="padding: 5px;">
		    <sec:authorize access="hasPermission('processDefinition','deploy')">
		        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="deployProcessDefinitionDialog()">部署</a>
		    </sec:authorize>
		    <sec:authorize access="hasPermission('processDefinition','diagramImage')">
		        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'door_open'" onclick="generateDiagramImageProcessDefinition()">流程图</a>
		    </sec:authorize>
		    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="clearSelected()">取消选中</a> 
        </div>
	</div>
</div>

<div id="processDefinitionDiv" style="display: none;">
    <form id="processDefinitionForm">
	    <table cellspacing="1" cellpadding="0" class="tb_searchbar">
			<tr>
			    <td class="td_title" width="30%">上传文件</td>
				<td>
				  <input type="file" name="deployFile"/>
				  <input type="hidden" name="inputName" value="deployFile"/>
	            </td>
	        </tr>
        </table>
    </form>
</div>
</body>
</html>