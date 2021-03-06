<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>more</title>
<link href="superUser/index/index-header.css" rel="stylesheet" type="text/css">
<link href="superUser/index/index-article.css" rel="stylesheet" type="text/css">
<link href="superUser/index/more.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="superUser/index/js/jquery-1.4.2.js"></script>
<script type="text/javascript">
	
	//分页变量
	var count = 1;
	
	//控制总分页总页数的变量
	var pages;
	
	$(document).ready(function () {
		//所有的文章
		listAllArticles(count);
		//导航栏
		listAllModels();
		
		//调用计算总页数的方法
		totalPages();
		
		//给pages赋值
		pages = $("#hidden_pages").val();
		
	});
	
	//获取文章总共有多少页
	function totalPages(){
		$.ajax({
	        type: "post",//请求类型
	        url: "index/getTotalRowsFromArticle.exc",//请求的url
	        dataType: "text",//ajax接口（请求url）返回的数据类型
	        success: function (result) {//data：返回数据（json对象）
	    		if(result!=null || result!=""){
	    			//设置没页10个，计算总页数
	    			pages = Math.ceil(result/27);
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
			if(count<pages){
				count = count + 1;
			}
		}
		listAllArticles(count);
	}
	
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
	
	/*  所有的文章 */
	function listAllArticles(count){
		$("#article_div111_ul").empty();
		$.ajax({
	        type: "post",//请求类型
	        url: "index/listArticlesInAllMore.exc",//请求的url
	        data: "count="+count, 
	        dataType: "json",//ajax接口（请求url）返回的数据类型
	        success: function (list) {//data：返回数据（json对象）
	        	for(var i=0;i<list.length;i++){
	        		$("#article_div111_ul").append('<li><a href="article.jsp?aid='+list[i].aid+'" onclick="addAccount('+list[i].aid+')">'+list[i].title+'</a></li>')
	        	}
	        }
		}); 
	}
	
	function addAccount(aid){
		$.ajax({
	        type: "post",//请求类型
	        url: "index/addaddAccount.exc",//请求的url
	        data: {
	        	aid:aid
	        }, 
	        dataType: "text" //ajax接口（请求url）返回的数据类型
		}); 
	}
	
	function test(){
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
		<div id="article_div111">
			<h1 align="center">最新的报道都在这里</h1>
			<ul id="article_div111_ul">
				<li><a>最新的报道都在这里</a></li>
			</ul>
			<div id="article_div111_div1">
				<input type="button" value="首页" onclick="page(1)">
				<input type="button" value="上一页" onclick="page(2)">
				<input type="button" value="下一页" onclick="page(3)">
				<input type="hidden" id="hidden_pages">
			</div>
		</div>
	</article>
	
</body>
</html>