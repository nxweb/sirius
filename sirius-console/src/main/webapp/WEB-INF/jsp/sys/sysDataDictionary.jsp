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

<script type="text/javascript" src="${ctx}/js/sys/sysDataDictionary.js?v=20180114"></script>
</head>
<body>
<input type="hidden" id="ctx" value="${ctx}">

<div id="cc" class="easyui-layout"  style="width: 100%; height: 465px; margin-left: auto; margin-right: auto">
    <div data-options="region:'north',iconCls:'icon-mysearch ',split:true" style="height:100px;">
		<form id="querySysDataDictionaryForm">	
		<sec:csrfInput />
		  <table cellspacing="1" cellpadding="0" class="tb_searchbar">
			<tr>
			    <td class="td_title" width="10%">字典名称</td>
				<td>
				  <input class="dicName" name="dicName" style="width:150px;"/>
                </td>
                <td class="td_title" width="10%">字典含义</td>
				<td>
				  <input class="dicMeaning" name="dicMeaning" style="width:150px;"/>
                </td>
			</tr>
			<tr>
			    <td class="td_title" width="10%">属性值</td>
				<td>
				  <input class="attributeValue" name="attributeValue" style="width:150px;"/>
                </td>
                <td class="td_title" width="10%">属性值含义</td>
				<td>
				  <input class="attributeMeaning" name="attributeMeaning" style="width:150px;"/>
                </td>
			</tr>	
			<tr>
				<td colspan="4" align="right">
				     <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="querySysDataDictionary();" style="margin-left: 150px;">查询</a>
					 <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="clearSysDataDictionaryCondition();">取消</a>
				</td>
			</tr>
         </table>
       </form>
	</div>
    <div data-options="region:'center',border:false" >
		<table id="sysDataDictionaryDataGrid"></table>
		<div id="sysDataDictionaryDataGridToolbar" class="easyui-panel" style="padding: 5px;">
		    <sec:authorize access="hasPermission('sysDataDictionary','add')">
		        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="addOrEditSysDataDictionaryDialog('add')">新增</a>
		    </sec:authorize>
		    <sec:authorize access="hasPermission('sysDataDictionary','modify')">
		        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="addOrEditSysDataDictionaryDialog('edit')">修改</a>
		    </sec:authorize>
		    <sec:authorize access="hasPermission('sysDataDictionary','remove')">
		        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="removeSysParam()">删除</a>
		    </sec:authorize>
		    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="clearSelected()">取消选中</a> 
        </div>
	</div>
</div>

<div id="sysDataDictionaryDiv" style="display: none;">
    <form id="sysDataDictionaryForm">
    <sec:csrfInput />
    <input id="id" name="id" type="hidden">
	    <table cellspacing="1" cellpadding="0" class="tb_searchbar">
			<tr>
			    <td class="td_title" width="15%">字典名称</td>
				<td>
				  <input id="dicName" name="dicName" style="width:350px;"/>
	            </td>
	        </tr>
	        <tr>
			    <td class="td_title" width="15%">字典含义</td>
				<td>
				  <input id="dicMeaning" name="dicMeaning" style="width:350px;"/>
	            </td>
	        </tr>
	        <tr>
			    <td class="td_title" width="15%">属性值</td>
				<td>
				  <input id="attributeValue" name="attributeValue" style="width:350px;"/>
	            </td>
	        </tr>
	        <tr>
			    <td class="td_title" width="15%">属性值含义</td>
				<td>
				  <input id="attributeMeaning" name="attributeMeaning" style="width:350px;"/>
	            </td>
	        </tr>
	        <tr>
			    <td class="td_title" width="15%">备注</td>
				<td>
				  <input id="remark" name="remark" style="width:350px;"/>
	            </td>
	        </tr>
        </table>
    </form>
</div>
</body>
</html>