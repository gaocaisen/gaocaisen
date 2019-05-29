package mvc;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
/**
  *   前端（核心）控制器，接受用户对所有Servlet的请求
  *   将复杂的web处理代码封装在前端控制器内部
 * @author 28049
 *
 */
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HandlerMapping handlerMapping = new HandlerMapping();
	public void init() throws ServletException {
		//初始化handlerMapping
		try {
			SAXReader reader = new SAXReader();
			String fileName = getInitParameter("fileName");
			InputStream ips = getClass().getClassLoader().getResourceAsStream(fileName);
			Document doc = reader.read(ips);
			Element root = doc.getRootElement();
			List<Element> ele = root.elements("bean");
			if(ele!=null) {
				for (Element element : ele) {
					String className = element.attributeValue("class");
					handlerMapping.parseController(className);
				}
			}
			//System.out.println("handler："+handlerMapping);
			
		} catch (Exception e) {
			//异常处理原则，能处理尽量处理，处理不了向上抛
			e.printStackTrace();
			throw new ServletException("控制器解析异常",e);
		}
		
	}
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String encode = getServletContext().getInitParameter("encode");
		//处理乱码问题
		response.setContentType("text/html;charset="+encode);
		/*response.setCharacterEncoding(encode);*/
		request.setCharacterEncoding(encode);
		try {
			String url = request.getRequestURI();
			
			//截取请求url
			url = url.substring(request.getContextPath().length());
			
			//从handlerMapping中通过url获得handler
			Handler handler = handlerMapping.getHandler(url);
			
			//通过handler反射调用子控制器中的方法
			String path = handler.execute(request);
			//System.out.println(path);
			if(path.startsWith("redirect")) {
				//重定向 redirec
				//对path进行截取
				path = path.substring("redirect:".length());
				//判断是否请求其他网站
				if(path.startsWith("http")) {
					//如果是外部资源直接重定向
					response.sendRedirect(path);
				}else {
					//如果是内部资源需要拼接项目名  request.getContextPath
					response.sendRedirect(request.getContextPath()+path);
				}
			}else if(path.startsWith("json:")){
				path = path.substring("json:".length());
				//System.out.println(path);
				response.getWriter().write(path);
			}else if(path.startsWith("account")){
				/*System.out.println("点击量增1");*/
			}else {
				//转发
				//拼接字符串路径
				path = "/"+path+".jsp";
				
				//基于返回值请求转发
				request.getRequestDispatcher(path).forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("服务器故障："+e.getMessage());
		}
		
	}
	

}
