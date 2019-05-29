<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="zTree/js/jquery-1.4.4.min.js"></script>
<title>后台管理登录界面</title>
	<style type="text/css">
		body{
			background-image: url("images/444.jpg");
			background-size: 100% ;
			background-repeat: no-repeat;
		}
		.loginDiv {
			height: 280px;
			width: 500px;
			background-color: rgba(255, 255, 255, 0.3);
			float: right;
			margin: 300px 700px 0 0;
			border-radius: 10px;
			color: #666666;
			padding: 8px;
		}
		.form-group{
			width: 500px;
			height: 30px;
			margin-top: 35px;
		}
		.col-md-8{
			display: inline-block;
		}
		p{
			font-weight: bold;
		}
		.input_class{
			background-color: rgba(255, 255, 255, 0.25);
			border: 0;
			margin-left: 15px;
			width: 250px;
			height: 30px;
			border-radius: 5px;
			padding-left: 10px;
			color: black;
		}
		.span_class{
			font-weight: bold;
			margin-left: 50px;
			color: black;
		}
		#loginBtn{
			width:70px;
			height:30px;
			border:0;
			margin-left: 100px;
			border-radius: 5px;
			background-color: rgba(255, 255, 255, 0.25);
			font-weight: bold;
		}
		h3{
			color: black;
		}
	</style>
</head>
<body>
	<!-- 透明层 -->
	<div class="loginDiv">
		<h3 align="center" >欢迎登录</h3>
		<!--表单开始-->
		<form id="form_login" method="post" action="login.exc">
			<!--用户名-->
			<div class="form-group">
				<span class="span_class" >用户名：</span>
				<input type="text" class="input_class" placeholder="请输入用户名！" name="username" value="${param.username }">
			</div>
			<!--密码-->
			<div class="form-group">
				<span class="span_class"  >密&nbsp;&nbsp;&nbsp;码：</span>
				<input type="text" class="input_class" placeholder="请输入密码！" name="password">
			</div>
			<!--提交按钮-->
			<div class="form-group">
				<input id="loginBtn" type="submit" value="登录"/>
				<span style="color:red" >${msg}</span>
			</div>
		</form>
	</div>
</body>
</html>