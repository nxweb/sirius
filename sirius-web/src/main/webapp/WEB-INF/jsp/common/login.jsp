<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<%@include file="./head.jsp"%>
<script type="text/javascript" src="${ctx}/js/common/login.js"></script>
<style type="text/css">
html,body {
	background-color: #FFF;
	color: #444;
	font: 12px/1.5 tahoma, arial, 'Hiragino Sans GB', \5b8b\4f53, sans-serif;
	padding: 0;
	margin: 0;
}
img {
	border: none;
}

.head {
	width: 890px;
	margin: 50px auto 0 auto;
}

.head strong {
	color: #FFF;
	border-radius: 3px;
	padding: 2px 10px 3px;
	padding: 3px 10px 2px \9;
	*padding: 4px 10px 1px;
	position: absolute;
	margin: 5px 0 0 15px;
	background-color: #C40;
	font-weight: normal;
}

.main {
	width: 890px;
	margin: 0 auto;
	padding: 20px;
	border-radius: 3px;
	overflow: hidden;
	height: 460px;
}

.main .right {
	float: right;
	background-color: #FAFAFA;
	box-shadow: 1px 1px 1px #FAFAFA;
	border-radius: 3px;
	padding: 10px 20px 20px 20px;
	width: 280px;
	border: 2px solid #DDD;
	*height: 240px;
}

.main .right p {
	font-family: "Microsoft YaHei";
	font-size: 14px;
	font-weight: bold;
	color: #C40;
}

.main .right div input {
	border: 1px solid #999;
	border-right: 1px solid #DCDCDC;
	border-bottom: 1px solid #DCDCDC;
	height: 14px;
	line-height: 14px;
	padding: 9px;
	vertical-align: middle;
	width: 250px;
	margin-bottom: 5px;
	background-color: #FFF;
}

.button_01 {
	display: inline-block;
	height: 34px;
	line-height: 24px;
	line-height: 20px \9;
	width: 270px;
	padding: 5px 0;
	*padding: 3px 0 7px;
	_padding: 5px 0;
	text-align: center;
	text-decoration: none;
	color: #604413;
	background: #F8BC54;
	border: 1px solid #B7842C;
	border-radius: 3px;
	background: linear-gradient(top, #FCB233 0%, #F8BC54 100%);
	box-shadow: 0px 1px 1px rgba(255, 255, 255, 0.8) inset, 1px 1px 3px
		rgba(0, 0, 0, 0.2);
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#FCB233',
		endColorstr='#F8BC54', GradientType=0);
	cursor: pointer;
	font-family: "Microsoft YaHei";
	font-size: 14px;
	font-weight: bold;
	*position: absolute;
}

.button_01:hover {
	color: #412E0C;
	border: 1px solid #A17324;
	box-shadow: 0px 1px 1px rgba(255, 255, 255, 0.8) inset, 1px 1px 5px
		rgba(0, 0, 0, 0.4);
	filter: progid:DXImageTransform.Microsoft.Shadow(Strength=2, Direction=135,
		Color='#999999');
}

.foot {
	text-align: center;
	/*border-top: 1px solid #DDD;*/
	padding-top: 20px;
	width: 890px;
	margin: 0 auto;
}

.tip {
	font-size: 12px;
	font-weight: normal;
	color: #404040;
	background:
		url(/YDD/images/easyui_icons/info.png)
		no-repeat;
	border: 1px solid #ddd;
	width: 245px;
	padding: 2px 0px 2px 25px;
	line-height: 18px;
	background-position: 3px 4px;
	border-color: #40b3ff;
	background-color: #e5f5ff;
}
.images_1{
	overflow: hidden;
	width: 100%;
	position: relative;
}
.images_1 img{
	width: 100%;
	height: 100%;
	position: absolute;
	display: none;
	z-index: 1;
}
.images_1 img:nth-child(1){
	display: block;
}
#page{
	position: absolute;
	top: 0;
	left: 20%;
	z-index: 999;
}
.navi_dialog{width:100%; height:100%; position:fixed; left:0; top:0; z-index:9996; display:none;}
.navi_dialog_bg{width:100%; height:100%; position:absolute; background:#000000; left:0; top:0; z-index:9997;filter: alpha(opacity=100); filter: progid:DXImageTransform.Microsoft.Alpha(opacity=100); opacity:0.40;}
.navi_dialog_con{width:500px; padding:15px; height:280px; border-radius:5px; position:absolute; left:55%; top:50%; z-index:9998; margin:-225px 0 0 -325px; background:#fff;}
.navi_dialog_con_h3{width:100%; text-align:center; height:25px; line-height:20px; font-size:20px; color:#666666; border-bottom:1px dashed #ccc;}
.navi_dialog_con_h3_span{ float:right; cursor:pointer; font-weight:normal; padding:5px 10px;}
.navi_dialog_con_p{ width:100%; text-align:left; padding-left:20px; margin:15px 0; height:25px; line-height:25px; font-size:16px; color:#0084e9; border-left:4px solid #0084e9;}
.navi_dialog_con_ul{width:100%; display:block; }
.navi_dialog_con_li{width:100%; display:block; height:45px; margin-left:65px; margin-top: 45px;}
.navi_dialog_con_li_span{width:160px; display:inline-block; height:40px; line-height:40px; font-size:14px; margin-left:5px; text-align:left;}

</style>

</head>

<body>
<input type="hidden" id="ctx" value="${ctx}">
	<div class="images_1">
		<img src="${ctx}/image/logo_1.jpg" alt="1" />
		<img src="${ctx}/image/logo_2.jpg" alt="2" />
		<img src="${ctx}/image/logo_3.jpg" alt="3" />
	</div>
	<div id="page">
		<div class="head">
		<a href="javascript:void(0)">
			<img src="${ctx}/image/logo.png">
		</a>
		<strong>运营管理平台</strong>
	</div>
	<div class="main">
		<div class="left"></div>
		<div class="right">
		    <form action="${ctx}/login" method="post">
		    <sec:csrfInput/>
			<p>用户名</p>
			<div>
				<input id="username" name="username" class="text" type="text" value="${username}"/>
			</div>
			<p>密码</p>
			<div>
				<input id="password" name="password" class="text" type="password" value="${password}"/>
			</div>
			
			<p>验证码</p>
			<div>
				<input type="text" id="validateCode" name="validateCode" style="width:60px" maxlength="4" placeholder="不区分大小写"/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<img alt="验证码看不清，换一张" src="${ctx}/home/getKaptcha"
					id="validateCodeImg" onclick="changeImg()"> 
					<a href="javascript:void(0)" onclick="changeImg()">换一张</a>
			</div>
			<div >
			<input type="checkbox" id="keepLogin" value="on" name="keepLogin" checked="true" style="width:10px" >
			记住用户名 
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:void(0)" onclick="pwdDialog()">修改密码</a>
			&nbsp;&nbsp;
			<a href="javascript:void(0)" onclick="forgetPwd()">忘记密码</a>
			</div>
			<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION.message  }">
				<div id="tipsDiv" style="height: 40px;">
					<div>
						<p class="tip">
							<font id="info" style="font-weight: normal;">${SPRING_SECURITY_LAST_EXCEPTION.message }</font>
						</p>
					</div>
				</div>
			</c:if>
			<p>
				<input type="submit" value="登录" class="button_01"/>
			</p>
			</form>
		</div>
	</div>
	<div class="foot">Copy &copy; 运营平台V1.0.0</div>
	</div>
</body>
</html>