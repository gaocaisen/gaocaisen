package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter{

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		//判断用户是否已经登录
		HttpSession session = req.getSession(false);
		//如果登录，则放行
		//用户没有登录，或者登录session没有状态
		if(session==null || session.getAttribute("user")==null) {
			//如果没有登录，则重定向到登录页面
			((HttpServletResponse)response).sendRedirect(req.getContextPath()+"/login.jsp");
		}else {
			//如果登录,则放行
			chain.doFilter(request, response);
		}
	}

	public void destroy() {
	}

}
