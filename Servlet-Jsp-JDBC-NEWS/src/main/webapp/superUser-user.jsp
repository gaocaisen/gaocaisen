<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>超级用户后台管理页面</title>
<link rel="stylesheet" href="superUser/css/index.css">
<link rel="stylesheet" href="superUser/js/lib/layui/css/layui.css">
<link rel="stylesheet" type="text/css" href="test/css/style.css" />
<link rel="stylesheet" type="text/css" href="superUser/css/superUser-user.css" />
<link rel="stylesheet" type="text/css" href="superUser/css/tanchuang-user.css" />
<script src="superUser/js/lib/layui/layui.js"></script>
<script src="superUser/js/define/index.js"></script>
<script src="superUser/js/define/common.js"></script>
<script type="text/javascript" src="zTree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript">
	
	var pages;
	var count = 1;
	
	$(document).ready(function () {
		listUser(count);
		
		totalPages();
		
		pages = $("#hidden_pages").val();
	});
	
	function totalPages(){
		$.ajax({
	        type: "post",//请求类型
	        url: "user/getTotalRowsFromUser.do",//请求的url
	        dataType: "text",//ajax接口（请求url）返回的数据类型
	        success: function (result) {//data：返回数据（json对象）
	    		if(result!=null || result!=""){
	    			pages = Math.ceil(result/10);
	    			$("#hidden_pages").val(pages);
	    		}
	        }
	    });
	}
	
	
	/* 分页技术 */
	function page(num){
		if(num==1){
			//如果为1，则为首页
			//alert("1");
			count = 1;
		}else if(num==2){
			//如果为2，则为上一页
			if(count==1){
				//alert("1");
				count = 1;
			}else{
				//alert(count-1);
				count = count - 1;
			}
			
		}else if(num==3){
			//如果为3，则为下一页
			//alert(count+1);
			//判断总页数
			if(count<pages){
				count = count+1;
			}
			
		}
		listUser(count);
	}
	
	/* 点击新增用户弹窗 */
	function addUserBtn(){
		$("#myModal")[0].style.display = "block";
		$("#windom_head").text("新增用户");
		$("#windom_foot").val("新增用户");
		//清空弹窗中的信息
		$("#windom_username_id").val("");
		$("#windom_password_id").val("");
		//向下拉框中追加
		//$("#windom_user_dept_select").append();
		//查询出所有的部门
		$.ajax({
	        type: "post",//请求类型
	        url: "dept/getDeptDetail.do",//请求的url
	        //data: "dId="+dId,
	        dataType: "json",//ajax接口（请求url）返回的数据类型
	        success: function (list) {//data：返回数据（json对象）
	        	$("#windom_user_dept_select").empty();
	        	for(var i=0;i<list.length;i++){
	        		var html = '<option value="'+list[i].dId+'">'+list[i].dName+'</option>';
	        		$("#windom_user_dept_select").append(html);
	        	}
	        }
	    });
		
		$("#windom_foot").unbind();
		/* 动态绑定点击事件 */
		$("#windom_foot").click(function(){
			//1判断是否输入了用户名
			var username_input = $("#windom_username_id").val();
			var password_input = $("#windom_password_id").val();
			if(username_input==""){
				alert("用户名不能为空");
			}else if(password_input==""){
				alert("密码不能为空");
			}else{
				//验证输入的用户名是否重复
				$.ajax({
		        type: "post",//请求类型
		        url: "user/getUserByUAP.do",//请求的url
		        data: {
		            username: username_input
		        },
		        dataType: "text",//ajax接口（请求url）返回的数据类型
		        success: function (res) {//data：返回数据（json对象）
		    		if(res=="false"){
		    			//若用户名不重复，则提交表单
		    			$.ajax({
		    		        type: "post",//请求类型
		    		        url: "user/addUser.do",//请求的url
		    		        data:$("#windom_form").serialize(),
		    		        dataType: "text",//ajax接口（请求url）返回的数据类型
		    		        success: function (result) {//data：返回数据（json对象）
		    		    		if(result=="true"){
		    		    			alert("添加成功");
		    		    			delWindow();//关闭弹窗
		    		    			listUser(count);//刷新用户列表
		    		    		}else{
		    		    			alert("添加失败");
		    		    			return false;
		    		    		}
		    		        }
		    		    });
		    		}else if(res=="true"){
		    			alert("用户名已存在，请重新输入！");
		    		}
		        }
		    });
			}
		});
	}
	
	/*  删除用户*/
	function delUser(uId){
		if (confirm("确认删除该用户吗?")) {
			$.ajax({
		        type: "post",//请求类型
		        url: "user/delUser.do",//请求的url
		        data: {
		            uId: uId
		        },
		        dataType: "text",//ajax接口（请求url）返回的数据类型
		        success: function (result) {//data：返回数据（json对象）
		    		if(result=="true"){
		    			alert("删除成功");
		    			listUser(count);
		    		}else if(result=="false"){
		    			alert("删除失败");
		    		}
		        }
		    });
		}
	}
	
	/* 修改用户信息 */
	function changeUser(uId){
		//点击修改按钮，弹出窗口
		$("#myModal")[0].style.display = "block";
		$("#windom_head").text("修改用户信息");
		$("#windom_foot").val("提交修改");
		//清空弹窗中的信息
		$("#windom_username_id").val("");
		$("#windom_password_id").val("");
		//需要把用户信息进行回填
		//根据用户id查询出用户
		$.ajax({
	        type: "post",//请求类型
	        url: "user/getUserByUid.do",//请求的url
	        data: "uId="+uId,
	        dataType: "json",//ajax接口（请求url）返回的数据类型
	        success: function (user) {//data：返回数据（json对象）
	        	$("#windom_username_id").val(user.username);
	        	$("#windom_password_id").val(user.password);
	        	$("#windom_user_dept_select").empty();
	        	var html = '<option value="'+user.dId+'">'+user.dName+'</option>';
        		$("#windom_user_dept_select").append(html);
        		
	        	$.ajax({
	    	        type: "post",//请求类型
	    	        url: "dept/getDeptDetail.do",//请求的url
	    	        //data: "dId="+dId,
	    	        async:false,
	    	        dataType: "json",//ajax接口（请求url）返回的数据类型
	    	        success: function (list) {//data：返回数据（json对象）
	    	        	for(var i=0;i<list.length;i++){
	    	        		if(user.dName!=list[i].dName){
	    	        			var html = '<option value="'+list[i].dId+'">'+list[i].dName+'</option>';
		    	        		$("#windom_user_dept_select").append(html);
	    	        		}
	    	        	}
	    	        }
	    	    });
	        }
	    });
		
		$("#windom_foot").unbind();
		$("#uId_hidden").val(uId);
		/* 动态绑定修改用户信息的点击事件 */
		$("#windom_foot").click(function(){
			//1判断是否输入了用户名
			var username_input = $("#windom_username_id").val();
			var password_input = $("#windom_password_id").val();
			if(username_input==""){
				alert("用户名不能为空");
			}else if(password_input==""){
				alert("密码不能为空");
			}else{
				$.ajax({
			        type: "post",//请求类型
			        url: "user/getUserByUAP.do",//请求的url
			        data: {
			            username: username_input
			        },
			        dataType: "text",//ajax接口（请求url）返回的数据类型
			        success: function (res) {//data：返回数据（json对象）
			        	if(res=="false"){
			    			//若用户名不重复，则提交表单
			    			$.ajax({
			    		        type: "post",//请求类型
			    		        url: "user/modifyUser.do",//请求的url
			    		        data:$("#windom_form").serialize(),
			    		        dataType: "text",//ajax接口（请求url）返回的数据类型
			    		        success: function (result) {//data：返回数据（json对象）
			    		    		if(result=="true"){
			    		    			alert("修改成功");
			    		    			delWindow();//关闭弹窗
			    		    			listUser(count);//刷新用户列表
			    		    		}else{
			    		    			alert("修改失败");
			    		    			return false;
			    		    		}
			    		        }
			    		    });
			    		}else if(res=="true"){
			    			alert("用户名已存在，请重新输入！");
			    		}
			        }
				});
			}
		});
	}
	
	
	/* 点击“x”关闭弹窗 */
	function delWindow(){
		$("#myModal")[0].style.display = "none"; 
	}
	
	/* 展示用户列表 */
	function listUser(count){
		$("#dept_table").empty();
		$.ajax({
	        type: "post",//请求类型
	        url: "user/listUser.do",//请求的url
	       data: {
	            count: count
	        },
	        dataType: "json",//ajax接口（请求url）返回的数据类型
	        success: function (list) {//data：返回数据（json对象）
	        	var html1 = '<tr class="table_header">'
						+'<td>用户ID</td>'
						+'<td>用户名</td>'
						+'<td>上级部门</td>'
						+'<td>身份</td>'
						+'<td>操作</td>'
					+'</tr>';
					$("#dept_table").append(html1);
	        	for(var i=0;i<list.length;i++){
	        		var userId = list[i].uId;
	        		var userUsername = list[i].username;
	        		var userDid = list[i].dId;
	        		var userAdmin = list[i].admin;
	        		//发一个ajsx去读取部门的名称
	        		//alert(userDid);
	        		var admins = "";
	        		if(userAdmin=="1"){
	        			admins = "超级管理员";
	        		}else if(userAdmin=="0"){
	        			admins = "普通用户";
	        		}
	        		$.ajax({
				        type: "post",//请求类型
				        url: "dept/getDeptsByDid.do",//请求的url
				        data: "dId="+userDid,
				        async: false,//同步请求数据
				        dataType: "json",//ajax接口（请求url）返回的数据类型
				        success: function (dept) {//data：返回数据（json对象）
				        	var html = '<tr class="table_header">'
								+'<td>'+userId+'</td>'
								+'<td>'+userUsername+'</td>'
								+'<td>'+dept.dName+'</td>'
								+'<td>'+admins+'</td>'
								+'<td><a onclick="changeUser('+userId+')" class="changeBtn">修改</a>&nbsp;&nbsp;'
								+'<a onclick="delUser('+userId+')" class="deletBtn">删除</a></td>';
							$("#dept_table").append(html); 
	        			}
	    			});
	        	}
	        }
	    });
	}
	
</script>
</head>
<body>
	<div class="layui-layout-admin fly-body">
		<div class="layui-header">
			<div class="admin-title dp-ib">
				<h1 class="fs-24 dp-ib c-fff mgl-20 mgt-10">后台管理</h1>
			</div>
			<div href="#!" class="layui-right user">
				<a href="#!" class="user-avatar"><img src="images/head.jpg"
					alt="" class="layui-circle"><span class="c-fff mgl-20">超级管理员</span></a>
			</div>
		</div>
		<div class="layui-side">
			<ul class="layui-nav layui-nav-tree" id="sideNav"
				lay-filter="sideNav">
				<li class="layui-nav-item layui-nav-itemed"><a href="superUser-dept.jsp">机构管理</a></li>
				<li class="layui-nav-item"><a href="superUser-user.jsp">用户管理</a></li>
				<li class="layui-nav-item"><a href="superUser-model.jsp">栏目管理</a></li>
				<li class="layui-nav-item"><a href="superUser-article.jsp">文章管理</a></li>
			</ul>
		</div>
		<div class="layui-body">
			<!-- 弹窗 -->
			<div id="myModal" class="modal">
				<!-- 弹窗内容 -->
				<div class="modal-content">
					<div class="modal-header">
						<span class="close" onclick="delWindow()">×</span>
						<h2 id="windom_head">弹窗头部</h2>
					</div>
					<div class="modal-body">
						<h1></h1>
						<form  method="post" id="windom_form">
							<table>
								<tr class="empty_user_tr"></tr>
								<tr>
									<td>用户名：</td><td><input type="text" placeholder="请输入用户名" name="username" id="windom_username_id"></td>
									<td><input type="hidden" id="uId_hidden" name="uId"></td>
								</tr>
								<tr class="empty_user_tr"></tr>
								<tr>
									<td>密码：</td><td><input type="password" placeholder="请输入密码" name="password" id="windom_password_id"></td>
								</tr>
								<tr class="empty_user_tr"></tr>
								<tr>
									<td>部门：</td>
									<td>
										<select name="dId" id="windom_user_dept_select"></select>
									</td>
								</tr>
								<tr class="empty_user_tr"></tr>
							</table>
						</form>
					</div>
					<div class="modal-footer">
						<input type="button" value="新增用户" id="windom_foot">
					</div>
				</div>
			</div>
			<div id="empty_div">
				<h1 id="empty_div_h1">用户信息表</h1>
			</div>
			<div id="user_table_div">
				<table class="table" id="dept_table">
					<tr class="table_header">
						<td>用户ID</td>
						<td>用户名</td>
						<td>上级部门</td>
						<td>身份</td>
						<td>操作</td>
					</tr>
				</table>
				<input type="button" value="新增用户" onclick="addUserBtn()">
				<input type="button" value="首页" onclick="page(1)">
				<input type="button" value="上一页" onclick="page(2)">
				<input type="button" value="下一页" onclick="page(3)">
				<input type="hidden" id="hidden_pages" >
			</div>
		</div>
	</div>
</body>
</body>
</html>