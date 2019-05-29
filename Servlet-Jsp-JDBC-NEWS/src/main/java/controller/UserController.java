package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;

import dao.UserDAO;
import entity.User;
import mvc.RequestMapping;

public class UserController {
	
	@RequestMapping("/user/addUser.do")
	public String addUser(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Integer dId = Integer.valueOf(request.getParameter("dId"));
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setdId(dId);
		
		UserDAO dao = new UserDAO();
		boolean flag = dao.addUser(user);
		
		return flag?"json:true":"json:''";
	}
	
	@RequestMapping("/user/delUser.do")
	public String delUser(HttpServletRequest request) {
		boolean flag = false;
		if (request.getParameter("uId") != null) {
			int uId = Integer.valueOf(request.getParameter("uId"));
			UserDAO dao = new UserDAO();
			flag = dao.delUser(uId);
			
		}
		return flag?"json:true":"json:false";
	}
	
	@RequestMapping("/user/modifyUser.do")
	public String modifyUser(HttpServletRequest request) {
		int uId = Integer.valueOf(request.getParameter("uId"));
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Integer dId = Integer.valueOf(request.getParameter("dId"));
		// System.out.println(id);
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setdId(dId);
		UserDAO dao = new UserDAO();
		boolean flag = dao.modifyUser(uId, user);
		
		return flag?"json:true":"json:false";
	}
	
	
	
	@RequestMapping("/user/listUser.do")
	public String listUser(HttpServletRequest request) {
		int count = Integer.parseInt(request.getParameter("count"));
		UserDAO dao = new UserDAO();
		List<User> list = dao.listUser(count);
		request.setAttribute("list", list);

		return "json:"+getJson(list);
	}
	
	@RequestMapping("/user/getUserByUid.do")
	public String getUserById(HttpServletRequest request) {
		User user = null;
		if (request.getParameter("uId") != null) {
			int uId = Integer.valueOf(request.getParameter("uId"));
			UserDAO dao = new UserDAO();
			user = dao.getUserByUid(uId);
		}

		return "json:"+getJson(user);
	}
	
	@RequestMapping("/user/getUserByUAP.do")
	public String getUserByUAP(HttpServletRequest request) {
		String username = request.getParameter("username");
		UserDAO dao = new UserDAO();
		boolean flag = dao.getUserByUAP(username);
		
		return flag?"json:true":"json:false";
	}
	
	@RequestMapping("/user/getTotalRowsFromUser.do")
	public String getTotalRowsFromUser(HttpServletRequest request) {
		UserDAO dao = new UserDAO();
		int rows = dao.getTotalRowsFromUser();
		
		return "json:"+rows;
	}
	
	private String getJson(Object obj) {
		String jsonOutput = "";
		if (obj != null) {
			jsonOutput = JSON.toJSONString(obj);
		}

		return jsonOutput;
	}
	
	
	
	
	
	
}
