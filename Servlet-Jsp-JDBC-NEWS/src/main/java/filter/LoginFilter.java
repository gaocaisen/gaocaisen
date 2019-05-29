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
		//�ж��û��Ƿ��Ѿ���¼
		HttpSession session = req.getSession(false);
		//�����¼�������
		//�û�û�е�¼�����ߵ�¼sessionû��״̬
		if(session==null || session.getAttribute("user")==null) {
			//���û�е�¼�����ض��򵽵�¼ҳ��
			((HttpServletResponse)response).sendRedirect(req.getContextPath()+"/login.jsp");
		}else {
			//�����¼,�����
			chain.doFilter(request, response);
		}
	}

	public void destroy() {
	}

}
