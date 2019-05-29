package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;

import dao.DeptDAO;
import entity.Department;
import mvc.RequestMapping;

public class DeptController {
	
	@RequestMapping("/dept/addDept.do")
	public String addDept(HttpServletRequest request) {
		String dName = request.getParameter("dName");
		int sId = Integer.valueOf(request.getParameter("sId"));
		
		DeptDAO dao = new DeptDAO();
		Department dept = new Department();
		dept.setdName(dName);
		dept.setsId(sId);
		String id = dao.addDept(dept);
		if(id!=null&&id!="") {
			return "json:"+id;
		}
		
		return "fail";
	}
	
	@RequestMapping("/dept/deleteDept.do")
	public String delete(HttpServletRequest request) {
		int dId = Integer.valueOf(request.getParameter("dId"));
		DeptDAO dao = new DeptDAO();
		boolean flag = dao.delete(dId);
		
		return flag?"json:false":"json:''";
	}
	
	@RequestMapping("/dept/changeDept.do")
	public String changeDept(HttpServletRequest request) {
		String dName = request.getParameter("dName");
		int dId = Integer.valueOf(request.getParameter("dId"));
		DeptDAO dao = new DeptDAO();
		boolean flag = dao.changeDept(dId, dName);
		
		return flag?"json:true":"json:''";
	}
	
	@RequestMapping("/dept/getDeptDetail.do")
	public String getDeptDetail(HttpServletRequest request) {
		DeptDAO dao = new DeptDAO();
		List<Department> list = dao.listDept();
		request.setAttribute("list", list);
		
		return "json:"+getJson(list);
	}
	
	@RequestMapping("/dept/getDeptsBySid.do")
	public String getDeptsBySid(HttpServletRequest request) {
		String json = "";
		if(request.getParameter("sId")!=null) {
			int sId = Integer.valueOf(request.getParameter("sId"));
			DeptDAO dao = new DeptDAO();
			List<Department> list = dao.getDeptsBySid(sId);
			json = getJson(list);
		}
		
		return "json:"+json;
	}
	
	@RequestMapping("/dept/getDeptsByDid.do")
	public String getDeptsByDid(HttpServletRequest request) {
		String json = "";
		if(request.getParameter("dId")!=null) {
			int dId = Integer.valueOf(request.getParameter("dId"));
			DeptDAO dao = new DeptDAO();
			Department dept = dao.getDeptsByDid(dId);
			json = getJson(dept);
		}
		
		return "json:"+json;
	}
	
	private String getJson(Object obj) {
		String jsonOutput = "";
		if(obj!=null) {
			 jsonOutput= JSON.toJSONString(obj);
		}
		
		return jsonOutput;
	}

}
