<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>壹點贷运营平台</title>
<%@include file="./head.jsp"%>

<script type="text/javascript" src="${ctx}/js/common/index.js"></script>

<style type="text/css">
.footer {
	text-align: center;
	color: #15428B;
	margin: 0px;
	padding: 0px;
	line-height: 23px;
	font-weight: bold;
}
.top_img3{ position:absolute;right:25px; top:28px; z-index:19991; display:none;}
</style>

</head>
<input type="hidden" id="ctx" value="${ctx}">
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<!-- 顶部 -->
	<div region="north" split="true" border="false"
		style="overflow: hidden; height: 55px; background: url(${ctx}/image/top_bg.png) #7f99be repeat-x center 50%; line-height: 40px; color: #fff; font-family: Verdana, 微软雅黑, 黑体">
		<span style="float: right; padding-right: 20px;" class="head">
			<img src="${ctx}/image/user.png" align="absmiddle" />&nbsp;欢迎,&nbsp;<sec:authentication property="principal.username" />&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

			<a href="javascript:void(0)" id="loginOut" style="text-decoration: none; color: white;" onclick="logout()">【安全退出】</a>
			<form method="POST" id="loginOutForm" action="${ctx}/logout" style="display: none;">
			    <sec:csrfInput />
            </form>
			
		</span>
		<span style="padding-left: 10px; font-size: 20px;">
			<img src="${ctx}/image/logo_index.png" align="absmiddle" />
		</span>
	</div>
	<!-- 底部 -->
	<div region="south" split="true" style="height: 30px; background: #D2E0F2;">
		<div class="footer">壹點贷运营平台V1.0.0</div>
	</div>

	<!-- 左边导航菜单 -->
	<div region="west" hide="true" split="true" title="导航菜单" style="width: 180px;" id="west">
        <ul id="menuTree"></ul>
	</div>

	<!-- 主体 -->
	<div id="mainPanle" region="center" style="background: #eee; overflow-y: hidden">
		<div id="tabs" class="easyui-tabs" fit="true" border="false"></div>
	</div>
	
	<div id="rightMenu" class="easyui-menu" style="width: 120px;">
	    <div id="mm-refresh" name="6" iconCls="icon-mylist2">刷新</div>
	    <div class="menu-sep"></div>
        <div id="mm-close" name="1">关闭</div>
        <div id="mm-closeAll" name="2">关闭全部标签页</div>
        <div id="mm-closeOther" name="3">关闭其他标签页</div>
        <div id="mm-closeRight" name="4">关闭右侧标签页</div>
        <div id="mm-closeLeft" name="5">关闭左侧标签页</div>
    </div>
</body>
</html>