<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>超级用户后台管理页面</title>
<link rel="stylesheet" href="superUser/css/index.css" type="text/css">
<link rel="stylesheet" href="superUser/js/lib/layui/css/layui.css" type="text/css">
<link rel="stylesheet" href="superUser/css/superUser.css" type="text/css">
<link rel="stylesheet" href="test/css/style.css" type="text/css" />
<link rel="stylesheet" href="superUser/css/superUser-article.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="superUser/css/tanchuang-article.css" />
<script src="superUser/js/lib/layui/layui.js"></script>
<script src="superUser/js/define/index.js"></script>
<script src="superUser/js/define/common.js"></script>
<script type="text/javascript" src="zTree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" charset="utf-8" src="utf8-jsp/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="utf8-jsp/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="utf8-jsp/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
	var ue;
	
	var userUid;  
	
	var pages;
	
	var count = 1;
	
	/* 打开页面显示所有文章信息 */
	$(document).ready(function () {
		userUid = $("#user_uId").val();
		listArticles(count);
		ue = UE.getEditor('editor');
		
		//调用计算总页数的方法
		totalPages();
		
		//给总页数赋值
		pages = $("#hidden_pages").val();
	});
	
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
			if(count<pages){
				count = count + 1;
			}
		}
		listArticles(count);
	}
	
	//获取当前用户所有的文章数，并按照每页15篇文章进行分页，计算出总的页数
	function totalPages(){
		$.ajax({
	        type: "post",//请求类型
	        url: "article/getTotalRowsFromNormal.do",//请求的url
	        dataType: "text",//ajax接口（请求url）返回的数据类型
	        success: function (result) {//data：返回数据（json对象）
	    		if(result!=null || result!=""){
	    			//设置没页10个，计算总页数
	    			pages = Math.ceil(result/10);
	    			$("#hidden_pages").val(pages);
	    		}
	        }
	    });
	}
	
	/* 展示所有文章信息 */
	function listArticles(){
		$("#dept_table").empty();
		$.ajax({
	        type: "post",//请求类型
	        url: "article/listUserArticle.do",//请求的url
	        data: {
	        	uId : userUid,
	         	count : count 
	        },
	        dataType: "json",//ajax接口（请求url）返回的数据类型
	        success: function (list) {//data：返回数据（json对象）
	        	var html1 = '<tr class="table_header">'
						+'<td>栏目ID</td>'
						+'<td>发布人</td>'
						+'<td>标题</td>'
						+'<td>发布日期</td>'
						+'<td>所属栏目</td>'
						+'<td>点击量</td>'
						+'<td>操作</td>'
					+'</tr>';
					$("#dept_table").append(html1);
	        		for(var i=0;i<list.length;i++){
			        	var html = '<tr class="table_header">'
							+'<td>'+list[i].aid+'</td>'
							+'<td>'+list[i].username+'</td>'
							+'<td>'+list[i].title+'</td>'
							+'<td>'+list[i].date+'</td>'
							+'<td>'+list[i].mName+'</td>'
							+'<td>'+list[i].account+'</td>'
							+'<td><a onclick="changeArticle('+list[i].aid+')" class="changeBtn">修改</a>&nbsp;&nbsp;'
							+'<a onclick="delArticle('+list[i].aid+')" class="deletBtn">删除</a></td>';
						$("#dept_table").append(html); 
	        		}
	        }
	    });
	}
	
	/* 新发布文章 */
	function addArticle(){
		$("#myModal")[0].style.display = "block";
		$("#article_title").val("");
		ue.setContent('');
		$("#article-img").attr("src","");
		$("#windom_head").text("发布新文章");
		$("#article_input").val("发布文章");
		//修改新增的路径
		$("#windom_form").attr("action","article/addUserArticle.do");
		//回填数据
		$.ajax({
	        type: "post",//请求类型
	        url: "model/listAllModels.do",//请求的url
	        //data: $("#windom_form").serialize(), 
	        dataType: "json",//ajax接口（请求url）返回的数据类型
	        success: function (list) {//data：返回数据（json对象）
	        	$("#article_select").empty();
	        	for(var i=0;i<list.length;i++){
	        		$("#article_select").append('<option value="'+list[i].mid+'">'+list[i].mName+'</option>')
	        	}
	        }
    	}); 
	}
	
	/* 删除文章 */
	function delArticle(aid){
		if(confirm("确认删除吗?")){
			$.ajax({
		        type: "post",//请求类型
		        url: "article/delArticle.do",//请求的url
		        data: "aid="+aid, 
		        dataType: "text",//ajax接口（请求url）返回的数据类型
		        success: function (res) {//data：返回数据（json对象）
		        	if(res=="true"){
		        		alert("删除成功");
		        		listArticles(count);
		        	}else{
		        		alert("删除失败");
		        	}
		        }
	    	}); 
		}
	}
	
	function changeArticle(aid){
		//打开弹窗
		$("#myModal")[0].style.display = "block";
		//清空标题和所属栏目
		$("#article_title").val("");
		$("#article_select").empty("");
		$("#windom_head").text("修改文章内容");
		$("#article_input").val("提交修改");
		$("#windom_form").attr("action","article/changeUserArticle.do");
		$("#hidden_aid").val(aid);
		//进行标题的回填和栏目的回填
		$.ajax({
	        type: "post",//请求类型
	        url: "article/getArticleByAid.do",//请求的url
	        data: "aid="+aid, 
	        dataType: "json",//ajax接口（请求url）返回的数据类型
	        success: function (article) {//data：返回数据（json对象）
	        	var modelName = article.mName;
	        	$("#article_title").val(article.title);
	        	ue.setContent(article.article);
	        	/* $("#huitian_image").val(article.image); */
	        	//alert(article.image);
	        	$("#change_hidden").val(article.image);
	        	$("#article_select").append('<option value="'+article.mid+'">'+modelName+'</option>')
	        	$("#article-img").attr("src",article.image);
	        	$.ajax({
	    	        type: "post",//请求类型
	    	        url: "model/listAllModels.do",//请求的url
	    	        dataType: "json",//ajax接口（请求url）返回的数据类型
	    	        success: function (models) {//data：返回数据（json对象）
	    	        	for(var i=0;i<models.length;i++){
	    	        		if(modelName!=models[i].mName){
	    	        			$("#article_select").append('<option value="'+models[i].mid+'">'+models[i].mName+'</option>')
	    	        		}
	    	        	};
	    	        }
	    	    })
	        }
	    });
	}
	
	function check(){
		var title = $("#article_title").val();
		if(title==null || title==""){
			alert("标题为空，请输入标题！");
			return false;
		}else if(!ue.hasContents()){
			alert("正文为空，请输入正文！")
			return false;
		}
	}
	
	/* 点击“x”关闭弹窗 */
	function delWindow(){
		$("#myModal")[0].style.display = "none"; 
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
				<a href="#!" class="user-avatar"><img src="images/timg.jpg"
					alt="" class="layui-circle"><span class="c-fff mgl-20">${user.username}</span></a>
					<input type="hidden" value="${user.uId}" id="user_uId">
			</div>
		</div>
		<input type="file" value="">
		<div class="layui-side">
			<ul class="layui-nav layui-nav-tree" id="sideNav" lay-filter="sideNav">
				<li class="layui-nav-item"><a href="normalUser-article.jsp">文章管理</a></li>
			</ul>
		</div>
		<div class="layui-body">
				<!-- 弹窗 -->
			<div id="myModal" class="modal">
				<!-- 弹窗内容 -->
				<div class="modal-content" id="modal-content">
					<div id="modal-header">
						<span id="close" onclick="delWindow()">×</span>
						<h2 id="windom_head">发布新文章</h2>
					</div>
					<div id="modal-body">
						<h1></h1>
						<form action="article/addArticle.do" method="post" id="windom_form" enctype="multipart/form-data" onsubmit="return check()">
							<table id="tanchuang_article_table">
								<tr>
									<td>文章标题:</td><td><input id="article_title" name="title" type="text" placeholder="请输入文章标题" class="article_input"></td>
								</tr>
								<tr>
									<td>文章类型:</td>
									<td>
										<select name ="mid" id="article_select"></select>
									</td>
									<td><input type="hidden" id="hidden_aid" name="aid"></td>
								</tr>
								<tr>
									<td>
									<script name="article" id="editor" type="text/plain" style="width:650px;height:300px;">
										
									</script>
									</td>
								</tr>
								<tr>
									<td id="article_image">请选择你要上传的图片：</td><td><input id="huitian_image" type="file" name="image" multiple></td>
								</tr>
								<tr>
									<td><img id="article-img" src="" style="width: 100px;"></img></td>
									<td><input type="hidden" id="change_hidden" name="noChangeImage"></td>
								</tr>
							</table>
							<input type="submit" value="发布文章" id="article_input">
						</form>
					</div>
					<script type="text/javascript"></script>
					<!-- <div id="modal-footer">
						<input type="button" value="新增用户" id="windom_foot">
					</div> -->
				</div>
			</div>
			<div id="empty_div">
				<h1 id="empty_div_h1">文章信息发布表</h1>
			</div>
			<div id="user_table_div">
				<table class="table" id="dept_table">
					<tr class="table_header">
						<td>栏目ID</td>
						<td>发布人</td>
						<td>标题</td>
						<td>发布日期</td>
						<td>所属栏目</td>
						<td>点击量</td>
						<td>操作</td>
					</tr>
				</table>
				<div id="botton_div">
					<input id="botton_div_btn" type="button" value="发布文章" onclick="addArticle()">
					<input type="button" value="首页" onclick="page(1)">
					<input type="button" value="上一页" onclick="page(2)">
					<input type="button" value="下一页" onclick="page(3)">
					<input type="hidden" id="hidden_pages">
				</div>
			</div>
		</div>
	</div>
</body>
</body>
</html>