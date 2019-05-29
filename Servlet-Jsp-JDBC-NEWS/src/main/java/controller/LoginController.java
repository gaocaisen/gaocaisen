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
				//�����û���¼�ɹ�-�ض��򵽳����û�ҳ��
				request.getSession().setAttribute("user", user);
				
				return "redirect:/superUser-dept.jsp";
				
			}else if(user.getAdmin()==0) {
				//��ͨ�û����棬�ض�����ͨ�û�����
				request.getSession().setAttribute("user", user);
				return "redirect:/normalUser-article.jsp";
			}
		}
		
		request.setAttribute("msg", "�û������������!");
		return "login";
	}
	
}
