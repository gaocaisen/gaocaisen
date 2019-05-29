<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>article</title>
<link href="superUser/index/index-header.css" rel="stylesheet" type="text/css">
<link href="superUser/index/index-article.css" rel="stylesheet" type="text/css">
<link href="superUser/index/article.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="superUser/index/js/jquery-1.4.2.js"></script>
<script type="text/javascript">
	var aid;
	
	$(document).ready(function () {
		//导航栏
		listAllModels();
		
		aid = $("#article_hidden_aid").val();
		
		getArticleByAid(aid);
	});
	
	/* 导航栏 */
	function listAllModels(){
		//清空导航栏
		$("#article_div_div1_ul").empty();
		$.ajax({
	        type: "post",//请求类型
	        url: "index/listAllModels.exc",//请求的url
	        //data: "aid="+aid, 
	        dataType: "json",//ajax接口（请求url）返回的数据类型
	        success: function (list) {//data：返回数据（json对象）
	        	for(var i=0;i<list.length;i++){
	        		$("#article_div_div1_ul").append('<li><a>'+list[i].mName+'</a></li>');
	        	}
	        }
		}); 
	}
	
	//展示具体的文章
	function getArticleByAid(aid){
		$("#article_div222_div").empty();
		//清空发布人，发布时间，点击量
		$("#span_username").text("");
		$("#span_date").text("");
		$("#span_account").text("");
		$.ajax({
	        type: "post",//请求类型
	        url: "index/getArticleByAid.exc",//请求的url
	        data: "aid="+aid, 
	        dataType: "json",//ajax接口（请求url）返回的数据类型
	        success: function (article) {//data：返回数据（json对象）
	        	$("#article_title_h1").text(article.title);
	        	//回填发布人，发布时间，点击量
	        	$("#span_username").text(article.username);
				$("#span_date").text(article.date);
				$("#span_account").text(article.account);
	        	//回填正文部分
	        	$("#article_div222_div").append(article.article);
	        	
	        	
	        }
		}); 
	}
	
	
</script>
</head>
<body>
	<header>
		<div id="header_div1">
			<div id="header_div1_div1">
				<ul id="header_div1_ul1">
					<li><img src="superUser/index/images/fangzi.jpg"><div class="header_div1_div2_ul1_div"><a href="#">通讯员投稿</a></div></li>
					<li><img src="superUser/index/images/zuoji.jpg"><div class="header_div1_div2_ul1_div"><a href="#">值班电话：023-77777777</a></div></li>
				</ul>
			</div>
			<div id="header_div1_div2">
				<ul id="header_div1_div2_ul1">
					<li><img src="superUser/index/images/shoujiduan.jpg"><div class="header_div1_div2_ul1_div"><a href="#">移动端</a></div></li>
					<li><img src="superUser/index/images/app.jpg"><div class="header_div1_div2_ul1_div"><a href="#">客户端</a></div></li>
					<li><img src="superUser/index/images/weibo.jpg"><div class="header_div1_div2_ul1_div"><a href="#">微博</a></div></li>
					<li><img src="superUser/index/images/weixin.jpg"><div class="header_div1_div2_ul1_div"><a href="#">微信</a></div></li>
				</ul>
			</div>
		</div>
	</header>
	<article>
		<div id="article_div">
			<div>
				<img alt="" src="superUser/index/article-images/tuoping.jpg">
				<img alt="" src="superUser/index/article-images/head1.jpg">
				<img alt="" src="superUser/index/article-images/guiyang.jpg" style="width: 1000px;">
			</div>
			<div id="article_div_div1">
				<!-- 这是导航栏 -->
				<div id="article_div_div1_div1">
					<ul id="article_div_div1_ul">
						<li><a>聚焦重庆</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div id="article_div222">
			<div id="article_div222_div1">
				<h1 align="center" id="article_title_h1">这是标题</h1>
				<div id="article_div222_div1_div1">
					<span>发布人：</span><span id="span_username"></span>&nbsp;
					<span>发布时间：</span><span id="span_date"></span>&nbsp;
					<span>点击量：</span><span id="span_account"></span>
				</div>
			</div>
			<div id="article_div222_div">
				这是正文部分，放在这里
			</div>
		</div>
	</article>
	<input type="hidden" value="${param.aid}" id="article_hidden_aid"> 
</body>
</html>