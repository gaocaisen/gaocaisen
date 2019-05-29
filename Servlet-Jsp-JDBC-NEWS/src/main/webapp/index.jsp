<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>奇奇怪怪新闻网首页</title>
<link href="superUser/index/index-header.css" rel="stylesheet" type="text/css">
<link href="superUser/index/index-article.css" rel="stylesheet" type="text/css">
<link href="superUser/index/index-footer.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="superUser/index/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="superUser/index/lunbo.js"></script>
<script type="text/javascript">
	$(document).ready(function () {
		//导航栏
		listAllModels();
		
		//轮播图和最新的动态
		imagesAndTitles();
		
		//栏目和文章
		modelsAndArticles();
		
		//失去焦点时停止定时器
		onblur = function(){
			stop();
		}
		//获得焦点时开启定时器
		onfocus = function(){
			start();
		}
		
		
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
	
	/* 轮播图和最新的动态 */
	function imagesAndTitles(){
		//清空轮播图
		$("#article_div_div2_div1").empty();
		//清空最新的动态
		$("#article_div_div2_div2_ul").empty();
		$.ajax({
	        type: "post",//请求类型
	        url: "index/listArticles.exc",//请求的url
	        //data: "aid="+aid, 
	        dataType: "json",//ajax接口（请求url）返回的数据类型
	        success: function (list) {//data：返回数据（json对象）
	        	for(var i=0;i<4;i++){
	        		//填充轮播图的图片
	        		$("#article_div_div2_div1").append('<a href="article.jsp?aid='+list[i].aid+'" onclick="addAccount('+list[i].aid+')"><img src="'+list[i].image+'"></a>');
	        	}
	        	//填充轮播图的图片上的序号
	        	$("#article_div_div2_div1").append('<div id="article_div_div2_div1_div1"></div>');
	        	for(var k=0;k<4;k++){
	        		var html1 = '<span><a href="#" onclick="changeImg('+k+')" id="span'+k+'">'+(k+1)+'</a></span>&nbsp;';
	        		$("#article_div_div2_div1_div1").append(html1);
	        	}
	        	//打开页面让轮播图的序号1变色
	    		$("#span0").css("color","blue");
	       	 	//调用轮播图的方法
	        	lunbo();
	       	 	//最新的动态
	        	for(var j=0;j<list.length;j++){
	        		$("#article_div_div2_div2_ul").append('<li><a href="article.jsp?aid='+list[j].aid+'" onclick="addAccount('+list[j].aid+')">'+list[j].title+'</a></li>');
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
	
	/* 这点轮播图的序号，自动跳转到该序号的图片上去 */
	function changeImg(num){
		$("#article_div_div2_div1").find('img')[num].style.left = "0px";
		for(var i=0;i<=3;i++){
			$("#span"+i).css("color","white");
		}
		$("#span"+(num)).css("color","blue");
		k = num+1;
		if(k==4){
			k = 0;
		}
		if(num==0){
			$("#article_div_div2_div1").find('img')[1].style.left = "480px";
			$("#article_div_div2_div1").find('img')[2].style.left = "960px";
			$("#article_div_div2_div1").find('img')[3].style.left = "1440px";
		}else if(num==1){
			$("#article_div_div2_div1").find('img')[2].style.left = "480px";
			$("#article_div_div2_div1").find('img')[3].style.left = "960px";
			$("#article_div_div2_div1").find('img')[0].style.left = "1440px";
		}else if(num==2){
			$("#article_div_div2_div1").find('img')[3].style.left = "480px";
			$("#article_div_div2_div1").find('img')[0].style.left = "960px";
			$("#article_div_div2_div1").find('img')[1].style.left = "1440px";
			
		}else if(num==3){
			$("#article_div_div2_div1").find('img')[0].style.left = "480px";
			$("#article_div_div2_div1").find('img')[1].style.left = "960px";
			$("#article_div_div2_div1").find('img')[2].style.left = "1440px";
		}
		
	}
	
	/* 栏目和具体的文章 */
	function modelsAndArticles(){
		//清空栏目和具体的文章
		$("#footer_div1").empty();
		$.ajax({
	        type: "post",//请求类型
	        url: "index/listAllModels.exc",//请求的url
	        //data: "aid="+aid, 
	        dataType: "json",//ajax接口（请求url）返回的数据类型
	        success: function (list) {//data：返回数据（json对象）
	        	for(var i=0;i<list.length;i++){
	        		var html = '<div class="footer_div1_div'+(i%2)+'">'
	        					+'<h1>'+list[i].mName+'</h1>'
	        					+'<ul id="'+list[i].mName+list[i].mid+'"></ul>'
	        					+'<div><a href="modelMore.jsp?mid='+list[i].mid+'">更多...</a></div></div>';
	        		$("#footer_div1").append(html);
	        		$.ajax({
	        	        type: "post",//请求类型
	        	        url: "index/getArticlesByMid.exc",//请求的url
	        	        async:false,
	        	        data: "mid="+list[i].mid, 
	        	        dataType: "json",//ajax接口（请求url）返回的数据类型
	        	        success: function (res) {//data：返回数据（json对象）
	        	        	for(var j=0;j<res.length;j++){
	        	        		$("#"+list[i].mName+list[i].mid).append('<li><a href="article.jsp?aid='+res[j].aid+'" onclick="addAccount('+res[j].aid+')">'+res[j].title+'</a></li>');
	        	        	}
	        	        }
	        		}); 
	        	}
	        }
		}); 
	}
	
	function lunbo(){
		var arr1 = $("#article_div_div2_div1").find('img');
		for(var i=0;i<arr1.length;i++){
			arr1[i].style.left= i*480+"px";
		}
	}
	//开启定时器调用移动一张图片的方法
	var timer = setInterval(moveImg,3000);
	
	var k = 1;
	function moveImg(){
		//创建移动图片的定时器
		var moveTimer = setInterval(function(){
			//得到所有图片数组 然后遍历 
			//遍历时 先取出图片原来的left值 
			//然后让left-=2 把值再赋值回去
			var arr = $("#article_div_div2_div1").find('img');
			for(var i=0;i<arr.length;i++){
				var oldLeft = parseInt(arr[i].style.left);
				oldLeft-=10;
				//扩展：当1张图片移出屏幕时
				//把图片再次放到最后一张图片的后面实现循环滚动
				//类似 打飞机游戏的背景图片  
				//图片移出后移动到最后面
				if(oldLeft<=-480){
					oldLeft=1440;
					//一张图片移动完停止定时器
					clearInterval(moveTimer);
					for(var j=0;j<=3;j++){
						$("#span"+j).css("color","white");
					}
					$("#span"+k).css("color","blue");
					 k = k+1;
					if(k==4){
						k = 0;
					}
				}
				
				arr[i].style.left=oldLeft+"px";
			}
		},10);
	}
	
	//停止定时器的方法
	function stop(){
		clearInterval(timer);
	}
	function start(){
		//每次开启新的定时器的时候把之前的强制关闭
		//避免出现多个定时器同时执行
		stop();
		//重新创建定时器
		timer = setInterval(moveImg,3000);
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
			<div id="article_div_div2">
				<!-- 这是轮播图 -->
				<div id="article_div_div2_div1" onmouseover="stop()" onmouseout="start()">
					<!-- <img src="superUser/index/article-images/c.png"> -->
					<!-- <img src="superUser/index/article-images/g.jpg">
					<img src="superUser/index/article-images/j.jpg">
					<img src="superUser/index/article-images/l.jpg"> -->
					<div id="article_div_div2_div1_div1">
						<span><a href="#" onclick="changeImg(0)" id="span0">1</a></span>
						<span><a href="#" onclick="changeImg(1)" id="span1">2</a></span>
						<span><a href="#" onclick="changeImg(2)" id="span2">3</a></span>
						<span><a href="#" onclick="changeImg(3)" id="span3">4</a></span>
					</div>
				</div>
				<div id="article_div_div2_div2">
					<h1>最新的动态在这里</h1>
					<ul id="article_div_div2_div2_ul">
						<!-- <li><a href="#">我爱我家，卫生靠大家</a></li> -->
					</ul>
					<div id="article_div_div2_div2_div1"><a href="allMore.jsp">更多...</a></div>
				</div>
			</div>
		</div>
	</article>
	<footer>
		<div id="footer_div1">
			<div class="footer_div1_div0">
				<h1>栏目名</h1>
				<ul>
					<li><a href="#">我爱我家，卫生靠大家</a></li>
					
				</ul>
				<div><a>更多...</a></div>
			</div>
			<div class="footer_div1_div1">
				<h1>栏目名</h1>
				<ul>
					<li><a href="#">我爱我家，卫生靠大家</a></li>
				</ul>
				<div><a>更多...</a></div>
			</div>
		</div>
	</footer>
</body>
</html>