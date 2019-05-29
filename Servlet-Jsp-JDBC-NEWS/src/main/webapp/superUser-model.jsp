<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>超级用户后台管理页面</title>
<link rel="stylesheet" href="zTree/css/demo.css" type="text/css">
<link rel="stylesheet" href="superUser/css/index.css">
<link rel="stylesheet" href="superUser/js/lib/layui/css/layui.css">
<link rel="stylesheet" href="zTree/css/metroStyle/metroStyle.css" type="text/css">
<link rel="stylesheet" href="superUser/css/superUser.css" type="text/css">
<link rel="stylesheet" type="text/css" href="test/css/style.css" />
<link rel="stylesheet" type="text/css" href="superUser/css/tanchuang-model.css" />
<link rel="stylesheet" type="text/css" href="superUser/css/superUser-model.css" />
<script type="text/javascript" src="zTree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="zTree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="zTree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="zTree/js/jquery.ztree.exedit.js"></script>
<script src="superUser/js/lib/layui/layui.js"></script>
<script src="superUser/js/define/index.js"></script>
<script src="superUser/js/define/common.js"></script>
<script type="text/javascript">
	var setting = {
        view: {
            expandSpeed: "",
            //addHoverDom: addHoverDom,
           	//removeHoverDom: removeHoverDom,
            selectedMulti: false
        },
        edit: {
            enable: true
        },
        data: {
            simpleData: {
            	enable: true,
    			idKey: "mid",
    			pIdKey: "sMid",
    			rootPId: null
            },
            key:{
            	name: "mName"
            }
        },
        callback: {
            //beforeRemove: beforeRemove,
            //beforeRename: beforeRename,
            //onRename: zTreeOnRename,
           	onClick: zTreeOnClick
        }
    };
	
	/* 打开页面就加载这个方法 */
	$(document).ready(function () {
		//初始化树
		initZtree();
		
		//展示所有栏目
		listModels();
		
	});
	
	/* 初始化树的方法，需要调用 */
	function initZtree(){
		$.ajax({
	        type: "post",//请求类型
	        url: "model/listAllModels.do",//请求的url
	       /*  data: {
	            method: "getDepartmentTree"
	        }, */
	        dataType: "json",//ajax接口（请求url）返回的数据类型
	        success: function (json) {//data：返回数据（json对象）
	        	$.fn.zTree.init($("#treeDemo"), setting, json);
	        	openAllTreenode();
	        }
	    });
	}
	
	/* 展开zTree所有节点 */
	function openAllTreenode(){
	    // 获取树对象
	    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	    /* 获取所有树节点 */
	  /*   var nodes = treeObj.transformToArray(treeObj.getNodes());
	    // 展开除第一级之外的其他节点
	    for (var i = 0, length_1 = nodes.length; i < length_1; i++) {
	        if(nodes[i].level == 0){
	            continue;
	        }
	        nodes[i].open = true;
	    } */
	    //展开所有节点
	    treeObj.expandAll(true);

	}
	
	/* 打开页面的时候展示所有栏目 */
	function listModels(){
		$.ajax({
	        type: "post",//请求类型
	        url: "model/listLevelOneModel.do",//请求的url
	       /*  data: {
	            method: "getDepartmentTree"
	        }, */
	        dataType: "json",//ajax接口（请求url）返回的数据类型
	        success: function (list) {//data：返回数据（json对象）
	        	$("#dept_div_id").empty();
	        	var html ='<table class="table" id="dept_table">'
	        	+'<h1>栏目信息表</h1>'
	        	+'<tr class="table_header">'
	    			+'<td>栏目ID</td>'
	    			+'<td>栏目名称</td>'
	    			+'<td>所属栏目ID</td>'
	    			+'<td>操作</td>'
	    		+'</tr></table><button onclick="addLevelOne()">新增栏目</button>';
	    		$("#dept_div_id").append(html+'&nbsp;&nbsp;<span>ps：该页面新增和删除都针对根节点栏目</span>');
        		for(var i=0;i<list.length;i++){
        			var html1 = '<tr class="table_header">'
						+'<td>'+list[i].mid+'</td>'
						+'<td>'+list[i].mName+'</td>'
						+'<td>'+list[i].sMid+'</td>'
						+'<td><a onclick="delLevelOne('+list[i].mid+')" class="deletBtn">删除</a></td>'
					+'</tr>';
	        		$("#dept_table").append(html1);	
	        		$.ajax({
	    		        type: "post",//请求类型
	    		        url: "model/getModelsBysMid.do",//请求的url
	    		        data: {
	    		            sMid: list[i].mid
	    		        },
	    		        async:false,
	    		        dataType: "json",//ajax接口（请求url）返回的数据类型
	    		        success: function (models) {//data：返回数据（json对象）
	    		    		for(var j=0;j<models.length;j++){
	    		    			var html2 = '<tr class="table_header">'
	    							+'<td>'+models[j].mid+'</td>'
	    							+'<td>'+models[j].mName+'</td>'
	    							+'<td>'+models[j].sMid+'</td>'
	    							+'<td></td>'
	    						+'</tr>';
	    		        		$("#dept_table").append(html2);	
	    		    		}
	    		        }
	    		    });
        		}
	        }
	    });
	}
	
	
	/* 刷新具体某个栏目模块的栏目信息 */
	function listDetailModels(sMid){
		$.ajax({
	        type: "post",//请求类型
	        url: "model/getModelsBysMid.do",//请求的url
	        data: {
	            sMid: sMid
	        }, 
	        dataType: "json",//ajax接口（请求url）返回的数据类型
	        success: function (list) {//data：返回数据（json对象）
	        	$("#dept_div_id").empty();
	        	var html ='<table class="table" id="dept_table">'
	        	+'<h1>栏目信息表</h1>'
	        	+'<tr class="table_header">'
					+'<td>栏目ID</td>'
					+'<td>栏目名称</td>'
					+'<td>所属栏目</td>'
					+'<td>操作</td>'
				+'</tr></table><button onclick="addModelBtn('+sMid+')">新增栏目</button>';
        		$("#dept_div_id").append(html);
        		for(var i=0;i<list.length;i++){
        			html ='<tr class="table_header">'
						+'<td>'+list[i].mid+'</td>'
						+'<td>'+list[i].mName+'</td>'
						+'<td>'+list[i].smName+'</td>'
						+'<td><a onclick="changeModel('+list[i].mid+","+list[i].sMid+')" class="changeBtn">修改</a> &nbsp;&nbsp;'
						+'<a onclick="delModel('+list[i].mid+","+list[i].sMid+')" class="deletBtn">删除</a>'
						+'</td>'
					+'</tr>';
	        		$("#dept_table").append(html);
        		}
	        }
	    });
	}
	
	
	/* 点击zTree事件 */
    function zTreeOnClick(event, treeId, treeNode) {
    	listDetailModels(treeNode.mid);
    	
	}
	
    /* 点击“x”关闭弹窗 */
	function delWindow(){
		$("#myModal")[0].style.display = "none"; 
	}
    
    /* 点击新增一级栏目 */
    function addLevelOne(){
    	 var Ppname = prompt("请输入一级栏目名称");
    	 if(Ppname==null ||Ppname=="" ){
    		 alert("栏目名称不能为空！");
    	 }else{
    		 $.ajax({
    		        type: "post",//请求类型
    		        url: "model/addLevelOneModel.do",//请求的url
    		        data: {
    		            mName: Ppname
    		        },
    		        dataType: "text",//ajax接口（请求url）返回的数据类型
    		        success: function (result) {//data：返回数据（json对象）
    		    		if(result!=""){
    		    			alert("新增成功");
    		    			//初始化树
    		    			initZtree();
    		    			
    		    			//展示所有栏目
    		    			listModels();
    		    		}else{
    		    			alert("新增失败");
    		    		}
    		        
    		        }
    		    });
    	 }
    	
    }
    
   	/* 新增栏目 */
    function addModelBtn(sMid){
    	//点击新增按钮，弹窗
    	$("#myModal")[0].style.display = "block";
    	//把以前的回填清空,然后栏目回填
    	$("#windom_model_id").val("");
    	//修改表头和表尾按钮的名称
    	$("#windom_head").text("新增栏目");
    	$("#windom_foot").val("新增栏目");
    	//回填所有的栏目
    	$.ajax({
	        type: "post",//请求类型
	        url: "model/getModelByMid.do",//请求的url
	       	data: {
	            mid: sMid
	        },
	        dataType: "json",//ajax接口（请求url）返回的数据类型
	        success: function (model) {//data：返回数据（json对象）
	        	$("#windom_model_dept_select").empty();
	        	$("#windom_model_dept_select").append('<option value="'+model.mid+'">'+model.mName+'</option>');
	        }
	    });
    	
    	/* 给弹窗中的新增栏目按钮动态绑定提交事件 */
    	$("#windom_foot").unbind();
    	$("#windom_foot").click(function(){
    		//判断是否输入新栏目名称
    		var model_input = $("#windom_model_id").val();
    		if(model_input=="" || model_input==null){
    			alert("栏目名为空，请输入栏目名");
    		}else{
    			$.ajax({
    		        type: "post",//请求类型
    		        url: "model/addModel.do",//请求的url
    		       	data: $("#windom_form").serialize(),
    		        dataType: "text",//ajax接口（请求url）返回的数据类型
    		        success: function (res) {//data：返回数据（json对象）
    		        	if(res!=""){
    		        		alert("新增栏目成功");
    		        		//关闭窗口
    		        		delWindow();
    		        		//刷新树和界面
    		        		initZtree();
    		        		listDetailModels(sMid);
    		        	}
    		        }
    		    });
    		}
    	});
    }
   	
   
   	
   	/* 删除具体某一个栏目中的栏目或其子节点 */
   	function delModel(mid,sMid){
   		if(confirm("确认删除该栏目？")){
			//首先判断当前一级栏目下有没子栏目
			$.ajax({
		        type: "post",//请求类型
		        url: "model/getModelsBysMid.do",//请求的url
		        data: {
		            sMid: mid
		        },
		        dataType: "json",//ajax接口（请求url）返回的数据类型
		        success: function (json) {//data：返回数据（json对象）
		    		if(json==null ||json==""){
		    			//该栏目下没有子栏目
		    			$.ajax({
		    		        type: "post",//请求类型
		    		        url: "model/delModel.do",//请求的url
		    		        data: {
		    		            mid: mid
		    		        },
		    		        dataType: "text",//ajax接口（请求url）返回的数据类型
		    		        success: function (result) {//data：返回数据（json对象）
		    		    		if(result=="true"){
		    		    			alert("删除成功");
		    		    			//刷新树和列表
		    		    			initZtree();
		    		    			listDetailModels(sMid);
		    		    		}else if(result=="false"){
		    		    			alert("删除失败");
		    		    		}
		    		        }
		    		    });
		    		}else{
		    			//该栏目下有子集,首先删除当级栏目
		    			if(confirm("该栏目下还有子栏目，确认删除该栏目及其子栏目吗")){
		    				$.ajax({
			    		        type: "post",//请求类型
			    		        url: "model/delModel.do",//请求的url
			    		        data: {
			    		            mid: mid
			    		        },
			    		        dataType: "text",//ajax接口（请求url）返回的数据类型
			    		        success: function (result) {//data：返回数据（json对象）
			    		        	//在删除其子栏目
			    		        	$.ajax({
					    		        type: "post",//请求类型
					    		        url: "model/deleteAllModelsInOneLevel.do",//请求的url
					    		        data: {
					    		            sMid: mid
					    		        },
					    		        dataType: "text",//ajax接口（请求url）返回的数据类型
					    		        success: function (result) {//data：返回数据（json对象）
					    		    		if(result=="true"){
					    		    			alert("删除成功");
					    		    			//刷新树和列表
					    		    			initZtree();
					    		    			listDetailModels(sMid);
					    		    		}else if(result=="false"){
					    		    			alert("删除失败");
					    		    		}
					    		        }
					    		    });
			    		        }
			    		    });
		    			}
		    		}
		        }
		    });
		}
   	}
   	
   	/* 删除首页中一级栏目 */
	function delLevelOne(mid){
		if(confirm("确认删除该一级栏目？")){
			//首先判断当前一级栏目下有没子栏目
			$.ajax({
		        type: "post",//请求类型
		        url: "model/getModelsBysMid.do",//请求的url
		        data: {
		            sMid: mid
		        },
		        dataType: "json",//ajax接口（请求url）返回的数据类型
		        success: function (json) {//data：返回数据（json对象）
		    		if(json==null ||json==""){
		    			//该栏目下没有子栏目
		    			$.ajax({
		    		        type: "post",//请求类型
		    		        url: "model/delModel.do",//请求的url
		    		        data: {
		    		            mid: mid
		    		        },
		    		        dataType: "text",//ajax接口（请求url）返回的数据类型
		    		        success: function (result) {//data：返回数据（json对象）
		    		    		if(result=="true"){
		    		    			alert("删除成功");
		    		    			//刷新树和列表
		    		    			initZtree();
		    		    			listModels();
		    		    		}else if(result=="false"){
		    		    			alert("删除失败");
		    		    		}
		    		        }
		    		    });
		    		}else{
		    			//该栏目下有子集,首先删除当级栏目
		    			if(confirm("该栏目下还有子栏目，确认删除该栏目及其子栏目吗")){
		    				$.ajax({
			    		        type: "post",//请求类型
			    		        url: "model/delModel.do",//请求的url
			    		        data: {
			    		            mid: mid
			    		        },
			    		        dataType: "text",//ajax接口（请求url）返回的数据类型
			    		        success: function (result) {//data：返回数据（json对象）
			    		        	//在删除其子栏目
			    		        	$.ajax({
					    		        type: "post",//请求类型
					    		        url: "model/deleteAllModelsInOneLevel.do",//请求的url
					    		        data: {
					    		            sMid: mid
					    		        },
					    		        dataType: "text",//ajax接口（请求url）返回的数据类型
					    		        success: function (result) {//data：返回数据（json对象）
					    		    		if(result=="true"){
					    		    			alert("删除成功");
					    		    			//刷新树和列表
					    		    			initZtree();
					    		    			listModels();
					    		    		}else if(result=="false"){
					    		    			alert("删除失败");
					    		    		}
					    		        }
					    		    });
			    		        }
			    		    });
		    			}
		    		}
		        }
		    });
		}
	}
   	
   	/* 修改栏目 */
   	function changeModel(mid,sMid){
   		//点击修改按钮，弹窗
    	$("#myModal")[0].style.display = "block";
    	//把以前的回填清空,然后栏目回填
    	$("#windom_model_id").val("");
    	$("#windom_model_dept_select").empty();
    	//修改表头和表尾按钮的名称
    	$("#windom_head").text("修改栏目");
    	$("#windom_foot").val("提交修改");
    	//回填 ：1.先回填当前栏目 2.然后在回填其他栏目
    	//1.回填栏目名称
    	$.ajax({
		        type: "post",//请求类型
		        url: "model/getModelByMid.do",//请求的url
		        data: {
		            mid: mid
		        },
		        dataType: "json",//ajax接口（请求url）返回的数据类型
		        success: function (resl) {//data：返回数据（json对象）
		    		$("#windom_model_id").val(resl.mName);
		        }
		});
    	//2.回填下拉选
    	$.ajax({
	        type: "post",//请求类型
	        url: "model/getModelByMid.do",//请求的url
	        data: {
	            mid: sMid
	        },
	        //async: false,//同步请求数据
	        dataType: "json",//ajax接口（请求url）返回的数据类型
	        success: function (model) {//data：返回数据（json对象）
	        	$("#windom_model_dept_select").append('<option value="'+model.mid+'">'+model.mName+'</option>');
	        	var modelName = model.mName;
	        	$.ajax({
			        type: "post",//请求类型
			        url: "model/listAllModels.do",//请求的url
			       /*  data: {
			            mid: mid
			        }, */
			        async: false,//同步请求数据
			        dataType: "json",//ajax接口（请求url）返回的数据类型
			        success: function (models) {//data：返回数据（json对象）
			        	//$("#windom_model_id").val(model);
			    		for(var i=0;i<models.length;i++){
			    			if(modelName!=models[i].mName){
			    				var html = '<option value="'+models[i].mid+'">'+models[i].mName+'</option>';
			    				$("#windom_model_dept_select").append(html);
			    			}
			    		}
			        }
			    });
	        }
	    });
    	
    	/* 给弹窗中的修改栏目按钮动态绑定提交事件 */
    	$("#mid_hidden").val(mid);
    	$("#windom_foot").unbind();
    	$("#windom_foot").click(function(){
    		var model_input = $("#windom_model_id").val();
    		if(model_input=="" || model_input==null){
    			alert("栏目名为空，请输入栏目名");
    		}else{
    			$.ajax({
    		        type: "post",//请求类型
    		        url: "model/changeModel.do",//请求的url
    		       	data: $("#windom_form").serialize(),
    		        dataType: "text",//ajax接口（请求url）返回的数据类型
    		        success: function (result) {//data：返回数据（json对象）
    		        	if(result=="true"){
    		    			alert("修改成功");
    		    			//刷新树和栏目列表
    		    			delWindow();
    		    			initZtree();
    		        		listDetailModels(sMid);
    		    		}else if(result=="false"){
    		    			alert("修改失败");
    		    		}
    		        }
    		    });
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
			<div class="dept_div">
				<!-- 弹窗 -->
				<div id="myModal" class="modal">
				<!-- 弹窗内容 -->
					<div class="modal-content">
						<div class="modal-header">
							<span class="close" onclick="delWindow()">×</span>
							<h2 id="windom_head">弹窗头部</h2>
						</div>
						<div class="modal-body">
							<form  method="post" id="windom_form">
								<table>
									<tr class="empty_class"></tr>
									<tr>
										<td>栏目名称：</td><td><input type="text" placeholder="请输入新栏目名称" name="mName" id="windom_model_id"></td>
										<td><input type="hidden" id="mid_hidden" name="mid"></td>
									</tr>
									
									<tr class="empty_class"></tr>
									<tr>
										<td>所属栏目分类：</td>
										<td>
											<select name="sMid" id="windom_model_dept_select"></select>
										</td>
									</tr>
									<tr class="empty_class"></tr>
								</table>
							</form>
						</div>
						<div class="modal-footer">
							<input type="button" value="新增" id="windom_foot">
						</div>
					</div>
				</div>
				<div class="myZtree">
					<h1 class="tree_p">栏目结构树形表</h1>
					<ul id="treeDemo" class="ztree"></ul>
				</div>
				<div class="dept_div1" id="dept_div_id">
					<table class="table" id="dept_table">
						<h1>栏目信息表</h1>
						<tr class="table_header">
							<td>栏目ID</td>
							<td>栏目名称</td>
							<td>上级栏目编号</td>
						</tr>
					</table>
				</div>
			</div>
			
		</div>
	</div>
</body>
</body>
</html>