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
  *   ǰ�ˣ����ģ��������������û�������Servlet������
  *   �����ӵ�web��������װ��ǰ�˿������ڲ�
 * @author 28049
 *
 */
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HandlerMapping handlerMapping = new HandlerMapping();
	public void init() throws ServletException {
		//��ʼ��handlerMapping
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
			//System.out.println("handler��"+handlerMapping);
			
		} catch (Exception e) {
			//�쳣����ԭ���ܴ���������������������
			e.printStackTrace();
			throw new ServletException("�����������쳣",e);
		}
		
	}
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String encode = getServletContext().getInitParameter("encode");
		//������������
		response.setContentType("text/html;charset="+encode);
		/*response.setCharacterEncoding(encode);*/
		request.setCharacterEncoding(encode);
		try {
			String url = request.getRequestURI();
			
			//��ȡ����url
			url = url.substring(request.getContextPath().length());
			
			//��handlerMapping��ͨ��url���handler
			Handler handler = handlerMapping.getHandler(url);
			
			//ͨ��handler��������ӿ������еķ���
			String path = handler.execute(request);
			//System.out.println(path);
			if(path.startsWith("redirect")) {
				//�ض��� redirec
				//��path���н�ȡ
				path = path.substring("redirect:".length());
				//�ж��Ƿ�����������վ
				if(path.startsWith("http")) {
					//������ⲿ��Դֱ���ض���
					response.sendRedirect(path);
				}else {
					//������ڲ���Դ��Ҫƴ����Ŀ��  request.getContextPath
					response.sendRedirect(request.getContextPath()+path);
				}
			}else if(path.startsWith("json:")){
				path = path.substring("json:".length());
				//System.out.println(path);
				response.getWriter().write(path);
			}else if(path.startsWith("account")){
				/*System.out.println("�������1");*/
			}else {
				//ת��
				//ƴ���ַ���·��
				path = "/"+path+".jsp";
				
				//���ڷ���ֵ����ת��
				request.getRequestDispatcher(path).forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("���������ϣ�"+e.getMessage());
		}
		
	}
	

}
