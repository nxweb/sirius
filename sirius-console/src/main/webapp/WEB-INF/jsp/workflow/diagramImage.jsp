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

<script type="text/javascript" src="${ctx}/js/workflow/diagramImage.js"></script>
</head>
<body>
<input type="hidden" id="ctx" value="${ctx}">
<input type="hidden" id="processDefinitionId" value="${processDefinitionId}">

<div class='easyui-layout' data-options="region:'north',title:'查询',iconCls:'icon-mysearch ',split:true" style="height:650px; overflow: auto;">
	<div class='img_content'><img class="trace_img" alt="" src="" style='position:absolute; left:10px; top:0px;' /></div>
</div>
</body>
</html>