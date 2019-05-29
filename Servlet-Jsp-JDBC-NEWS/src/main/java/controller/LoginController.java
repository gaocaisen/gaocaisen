package controller;

import javax.servlet.http.HttpServletRequest;

import dao.UserDAO;
import entity.User;
import mvc.RequestMapping;

public class LoginController {
	
	@RequestMapping("/login-form.exc")
	public String login_form(HttpServletRequest request) {
		return "login";
	}
	
	@RequestMapping("/login.exc")
	public String login(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password  = request.getParameter("password");
		//System.out.println(username);
		
		UserDAO dao = new UserDAO();
		User user = dao.checkLogin(username, password);
		//System.out.println(flag);
		if(user!=null) {
			if(user.getAdmin()==1) {
				//超级用户登录成功-重定向到超级用户页面
				request.getSession().setAttribute("user", user);
				
				return "redirect:/superUser-dept.jsp";
				
			}else if(user.getAdmin()==0) {
				//普通用户界面，重定向到普通用户界面
				request.getSession().setAttribute("user", user);
				return "redirect:/normalUser-article.jsp";
			}
		}
		
		request.setAttribute("msg", "用户名或密码错误!");
		return "login";
	}
	
}
