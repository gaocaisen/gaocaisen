<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>超级用户后台管理页面</title>
<link rel="stylesheet" href="zTree/css/demo.css" type="text/css">
<link rel="stylesheet" href="zTree/css/metroStyle/metroStyle.css" type="text/css">
<script type="text/javascript" src="zTree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="zTree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="zTree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="zTree/js/jquery.ztree.exedit.js"></script>

<link rel="stylesheet" href="superUser/css/index.css">
<link rel="stylesheet" href="superUser/js/lib/layui/css/layui.css">
<script src="superUser/js/lib/layui/layui.js"></script>
<script src="superUser/js/define/index.js"></script>
<script src="superUser/js/define/common.js"></script>
<link rel="stylesheet" href="superUser/css/superUser.css" type="text/css">
<link rel="stylesheet" type="text/css" href="test/css/style.css" />
<link rel="stylesheet" type="text/css" href="superUser/css/tanchuang.css" />
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
    			idKey: "dId",
    			pIdKey: "sId",
    			rootPId: null
            },
            key:{
            	name: "dName"
            }
        },
        callback: {
            //beforeRemove: beforeRemove,
            //beforeRename: beforeRename,
            //onRename: zTreeOnRename,
           	onClick: zTreeOnClick
        }
    };
	
	/* 鼠标点击事件 */
    function zTreeOnClick(event, treeId, treeNode) {
    	$.ajax({
	        type: "post",//请求类型
	        url: "dept/getDeptsBySid.do",//请求的url
	        data: {
	            sId: treeNode.dId
	        }, 
	        dataType: "json",//ajax接口（请求url）返回的数据类型
	        success: function (list) {//data：返回数据（json对象）
	        	$("#dept_div_id").empty();
	        	var id = treeNode.dId;
	        	var html ='<table class="table" id="dept_table">'
	        	+'<h1>部门信息表</h1>'
	        	+'<tr class="table_header">'
					+'<td>部门ID</td>'
					+'<td>部门名称</td>'
					+'<td>上级部门</td>'
					+'<td>操作</td>'
				+'</tr></table><button onclick="addDeptBtn('+id+')">新增部门</button>';
        		$("#dept_div_id").append(html);
        		for(var i=0;i<list.length;i++){
        			html ='<tr class="table_header">'
						+'<td>'+list[i].dId+'</td>'
						+'<td>'+list[i].dName+'</td>'
						+'<td>'+treeNode.dName+'</td>'
						+'<td><a onclick="changeDept('+list[i].dId+')" class="changeBtn">修改</a> &nbsp;&nbsp;'
						+'<a onclick="deleteDept('+list[i].dId+","+list[i].sId+')" class="deletBtn">删除</a></td>'
					+'</tr>';
	        		$("#dept_table").append(html);
        		}
	        }
	    });
	}
	
	/* 修改部门信息 */
	function changeDept(dId){
		var Ppname;
		$.ajax({
	        type: "post",//请求类型
	        url: "dept/getDeptsByDid.do",//请求的url
	       data: {
	            dId: dId
	        },
	        dataType: "json",//ajax接口（请求url）返回的数据类型
	        success: function (json) {//data：返回数据（json对象）
	        	Ppname = prompt("请输入新部门名称！",json.dName);
	        	if (Ppname == null) {
	                return;
	            } else if (Ppname == "") {
	                alert("节点名称不能为空");
	            } else{
	            	$.ajax({
	        	        type: "post",//请求类型
	        	        url: "dept/changeDept.do",//请求的url
	        	        data: {
	        	        	dId:dId,
	        	        	dName:Ppname
	        	        },
	        	        dataType: "text",//ajax接口（请求url）返回的数据类型
	        	        success: function (json) {//data：返回数据（json对象）
	        	        	if(json=="true"){
	        	        		alert("修改成功！");
	        	        		//刷新树信息
	        	        		initZtree();
	        	        		//刷新部门信息
	        	        		//根据did去查询部门
	    		        		$.ajax({
	    		        	        type: "post",//请求类型
	    		        	        url: "dept/getDeptsByDid.do",//请求的url
	    		        	        data: "dId="+dId,
	    		        	        dataType: "json",//ajax接口（请求url）返回的数据类型
	    		        	        success: function (zdept) {//data：返回数据（json对象）
	    		        	        	//zdept为子部门
	    		        	        	$.ajax({
	    		        	    	        type: "post",//请求类型
	    		        	    	        url: "dept/getDeptsByDid.do",//请求的url
	    		        	    	        data: "dId="+zdept.sId,
	    		        	    	        dataType: "json",//ajax接口（请求url）返回的数据类型
	    		        	    	        success: function (sdept) {//data：返回数据（json对象）
	    		        	    	        	//sdept为上级部门
	    		        	    	        	$.ajax({
	    		        	    	    	        type: "post",//请求类型
	    		        	    	    	        url: "dept/getDeptsBySid.do",//请求的url
	    		        	    	    	        data: {
	    		        	    	    	            sId: sdept.dId
	    		        	    	    	        }, 
	    		        	    	    	        dataType: "json",//ajax接口（请求url）返回的数据类型
	    		        	    	    	        success: function (list) {//data：返回数据（json对象）
	    		        	    	    	        	$("#dept_div_id").empty();
	    		        	    	    	        	var html ='<table class="table" id="dept_table">'
	    		        	    	    	        	+'<h1>部门信息表</h1>'
	    		        	    	    	        	+'<tr class="table_header">'
	    		        	    	    					+'<td>部门ID</td>'
	    		        	    	    					+'<td>部门名称</td>'
	    		        	    	    					+'<td>上级部门</td>'
	    		        	    	    					+'<td>操作</td>'
	    		        	    	    				+'</tr></table><button onclick="addDeptBtn('+sdept.dId+')">新增部门</button>';
	    		        	    	            		$("#dept_div_id").append(html);
	    		        	    	            		for(var i=0;i<list.length;i++){
	    		        	    	            			html ='<tr class="table_header">'
	    		        	    	    						+'<td>'+list[i].dId+'</td>'
	    		        	    	    						+'<td>'+list[i].dName+'</td>'
	    		        	    	    						+'<td>'+sdept.dName+'</td>'
	    		        	    	    						+'<td><a onclick="changeDept('+list[i].dId+')" class="changeBtn">修改</a> &nbsp;&nbsp;'
	    		        	    	    						+'<a onclick="deleteDept('+list[i].dId+","+list[i].sId+')" class="deletBtn">删除</a>'
	    		        	    	    						+'</td>'
	    		        	    	    					+'</tr>';
	    		        	    	    	        		$("#dept_table").append(html);
	    		        	    	            		}
	    		        	    	    	        }
	    		        	    	    	    });
	    		        	    	        	
	    		        	    	        }
	    		        	    	    });
	    		        	        	
	    		        	        }
	    		        	    });
	        	        		
	        	        	}else{
	        	        		return false;
	        	        	}
	        	        }
	        	    });
	            }
	        }
	    });
		
		
		
		
	}
	
	
	/* 点击新增部门按钮，弹出窗口 */
	function addDeptBtn(id){
		$.ajax({
	        type: "post",//请求类型
	        url: "dept/getDeptsByDid.do",//请求的url
	        data: {
	            dId: id
	        },
	        dataType: "json",//ajax接口（请求url）返回的数据类型
	        success: function (json) {//data：返回数据（json对象）
	    		$("#myModal")[0].style.display = "block";
	    		//修改弹窗头部的信息
	    		$("#windom_head").text("新增部门");
	    		$("#super_dept").text(json.dName);
	    		$("#windom_foot").val("新增部门")
	    		$("#windom_hideen").val(json.dId);
	        }
	    });
	}
	
	/* 弹出的新增窗口中的新增部门 */
	function addDept(){
		var dName = $("#dName_id").val();
		if(dName==""){
			alert("新部门名称不能为空");
			delWindow();
			return false;
		}else{
			$.ajax({
		        type: "post",//请求类型
		        url: "dept/addDept.do",//请求的url
		        data: $("#windom_form").serialize(),
		        dataType: "text",//ajax接口（请求url）返回的数据类型
		        success: function (dId) {//data：返回数据（json对象）
		        	if(dId){
		        		alert("添加部门成功");
		        		//关闭弹窗
		        		delWindow();
		        		//刷新部门树形表
		        		initZtree();
		        		//根据did去查询部门
		        		$.ajax({
		        	        type: "post",//请求类型
		        	        url: "dept/getDeptsByDid.do",//请求的url
		        	        data: "dId="+dId,
		        	        dataType: "json",//ajax接口（请求url）返回的数据类型
		        	        success: function (zdept) {//data：返回数据（json对象）
		        	        	//zdept为子部门
		        	        	$.ajax({
		        	    	        type: "post",//请求类型
		        	    	        url: "dept/getDeptsByDid.do",//请求的url
		        	    	        data: "dId="+zdept.sId,
		        	    	        dataType: "json",//ajax接口（请求url）返回的数据类型
		        	    	        success: function (sdept) {//data：返回数据（json对象）
		        	    	        	//sdept为上级部门
		        	    	        	$.ajax({
		        	    	    	        type: "post",//请求类型
		        	    	    	        url: "dept/getDeptsBySid.do",//请求的url
		        	    	    	        data: {
		        	    	    	            sId: sdept.dId
		        	    	    	        }, 
		        	    	    	        dataType: "json",//ajax接口（请求url）返回的数据类型
		        	    	    	        success: function (list) {//data：返回数据（json对象）
		        	    	    	        	$("#dept_div_id").empty();
		        	    	    	        	var html ='<table class="table" id="dept_table">'
		        	    	    	        	+'<h1>部门信息表</h1>'
		        	    	    	        	+'<tr class="table_header">'
		        	    	    					+'<td>部门ID</td>'
		        	    	    					+'<td>部门名称</td>'
		        	    	    					+'<td>上级部门</td>'
		        	    	    					+'<td>操作</td>'
		        	    	    				+'</tr></table><button onclick="addDeptBtn('+sdept.dId+')">新增部门</button>';
		        	    	            		$("#dept_div_id").append(html);
		        	    	            		for(var i=0;i<list.length;i++){
		        	    	            			html ='<tr class="table_header">'
		        	    	    						+'<td>'+list[i].dId+'</td>'
		        	    	    						+'<td>'+list[i].dName+'</td>'
		        	    	    						+'<td>'+sdept.dName+'</td>'
		        	    	    						+'<td><a onclick="changeDept('+list[i].dId+')" class="changeBtn">修改</a>&nbsp;&nbsp;'
		        	    	    						+'<a onclick="deleteDept('+list[i].dId+","+list[i].sId+')" class="deletBtn">删除</a>'
		        	    	    						+'</td>'
		        	    	    					+'</tr>';
		        	    	    	        		$("#dept_table").append(html);
		        	    	            		}
		        	    	    	        }
		        	    	    	    });
		        	    	        	
		        	    	        }
		        	    	    });
		        	        	
		        	        }
		        	    });
		        	}else{
		        		alert("添加部门失败");
		        	}
		        }
		    });
		}
	}
	
	/* 删除部门 */
	function deleteDept(dId,sId){
		if (confirm("确认删除该部门吗?")) {
			$.ajax({
		        type: "post",//请求类型
		        url: "dept/deleteDept.do",//请求的url
		        data: "dId="+dId,
		        dataType: "text",//ajax接口（请求url）返回的数据类型
		        success: function (result) {//data：返回数据（json对象）
		        	if(result=="false"){
		        		alert("删除部门成功");
		        		//刷新部门树形表
		        		initZtree();
		        		//把sid作为上级部门的did然后去查询上级部门
		        		$.ajax({
		        	        type: "post",//请求类型
		        	        url: "dept/getDeptsByDid.do",//请求的url
		        	        data: "dId="+sId,
		        	        dataType: "json",//ajax接口（请求url）返回的数据类型
		        	        success: function (sdept) {//data：返回数据（json对象）
		        	        	//sdept上级部门
		        	        	$.ajax({
        	    	    	        type: "post",//请求类型
        	    	    	        url: "dept/getDeptsBySid.do",//请求的url
        	    	    	        data: {
        	    	    	            sId: sdept.dId
        	    	    	        }, 
        	    	    	        dataType: "json",//ajax接口（请求url）返回的数据类型
        	    	    	        success: function (list) {//data：返回数据（json对象）
        	    	    	        	$("#dept_div_id").empty();
        	    	    	        	var html ='<table class="table" id="dept_table">'
        	    	    	        	+'<h1>部门信息表</h1>'
        	    	    	        	+'<tr class="table_header">'
        	    	    					+'<td>部门ID</td>'
        	    	    					+'<td>部门名称</td>'
        	    	    					+'<td>上级部门</td>'
        	    	    					+'<td>操作</td>'
        	    	    				+'</tr></table><button onclick="addDeptBtn('+sdept.dId+')">新增部门</button>';
        	    	            		$("#dept_div_id").append(html);
        	    	            		for(var i=0;i<list.length;i++){
        	    	            			html ='<tr class="table_header">'
        	    	    						+'<td>'+list[i].dId+'</td>'
        	    	    						+'<td>'+list[i].dName+'</td>'
        	    	    						+'<td>'+sdept.dName+'</td>'
        	    	    						+'<td><a onclick="changeDept('+list[i].dId+')" class="changeBtn">修改</a> &nbsp;&nbsp;'
        	    	    						+'<a onclick="deleteDept('+list[i].dId+","+list[i].sId+')" class="deletBtn">删除</a>'
        	    	    						+'</td>'
        	    	    					+'</tr>';
        	    	    	        		$("#dept_table").append(html);
        	    	            		}
        	    	    	        }
        	    	    	    });
		        	        }
		        	    });
		        	}else{
		        		alert("添加部门失败");
		        	}
		        }
		    });
		}else{
			
			return false;
		}
	}
	
	/* 部门dId查询该部门的sId */
	function getSidBydId(dId){
		$.ajax({
	        type: "post",//请求类型
	        url: "dept/getDeptsByDid.do",//请求的url
	        data: "dId="+dId,
	        dataType: "json",//ajax接口（请求url）返回的数据类型
	        success: function (json) {//data：返回数据（json对象）
	        	
	        	
	        }
	    });
	}
	
	/* 点击“x”隐藏弹窗 */
	function delWindow(){
		$("#myModal")[0].style.display = "none"; 
	}
	
	/* 展开树的所有节点 */
	function openAllTreenode(){
	    // 获取树对象
	    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	    /* 获取所有树节点 */
	    var nodes = treeObj.transformToArray(treeObj.getNodes());
	    // 展开除第一级之外的其他节点
	    for (var i = 0, length_1 = nodes.length; i < length_1; i++) {
	        if(nodes[i].level == 0){
	            continue;
	        }
	        nodes[i].open = true;
	    }
	    //展开第一级节点
	    treeObj.expandNode(nodes[0], true);

	}
	
	function initZtree(){
		$.ajax({
	        type: "post",//请求类型
	        url: "dept/getDeptDetail.do",//请求的url
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
	
	$(document).ready(function () {
		initZtree();
		
		$.ajax({
	        type: "post",//请求类型
	        url: "dept/getDeptDetail.do",//请求的url
	       /*  data: {
	            method: "getDepartmentTree"
	        }, */
	        dataType: "json",//ajax接口（请求url）返回的数据类型
	        success: function (list) {//data：返回数据（json对象）
	        	$("#dept_table").empty();
	        	var html = '<tr class="table_header">'
					+'<td>部门ID</td>'
					+'<td>部门名称</td>'
					+'<td>上级部门编号</td>'
				+'</tr>';
        		$("#dept_table").append(html);
        		for(var i=0;i<list.length;i++){
        			var html = '<tr class="table_header">'
						+'<td>'+list[i].dId+'</td>'
						+'<td>'+list[i].dName+'</td>'
						+'<td>'+list[i].sId+'</td>'
					+'</tr>';
	        		$("#dept_table").append(html);	
        		}
	        }
	    });
		
	});
	
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
								<tr>
									<td>上级部门：<span id="super_dept"></span></td>
									
									<td><input type="hidden" id="windom_hideen" name="sId"></td>
								</tr>
								<tr class="empty_tr"></tr>
								<tr>
									<td>新部门名称:<input type="text" placeholder="请输入新部门名称" name="dName" id="dName_id"></td><br>
								</tr>
								<tr class="empty_tr"></tr>
							</table>
						</form>
					</div>
					<div class="modal-footer">
						<input type="button" value="新增部门" id="windom_foot" onclick="addDept()">
					</div>
				</div>
			</div>
			<div class="dept_div">
				<div class="myZtree">
					<h1 class="tree_p">部门结构树形表</h1>
					<ul id="treeDemo" class="ztree"></ul>
				</div>
				<div class="dept_div1" id="dept_div_id">
					<table class="table" id="dept_table">
						<h1>部门信息表</h1>
						<tr class="table_header">
							<td>部门ID</td>
							<td>部门名称</td>
							<td>上级部门</td>
							<td>操作</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</body>
</html>