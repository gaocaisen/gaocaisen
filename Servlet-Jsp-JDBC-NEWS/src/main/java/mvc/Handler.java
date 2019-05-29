package mvc;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

/**
 * handler��������
 * ����method�Ͷ�Ӧ��controller�����ӳ��
 * �ṩһ�����Զ�ִ̬�и�method�ķ���
 * @author 28049
 *
 */
public class Handler {
	private Object controller; //�ӿ���������
	private Method method; //�ӿ�������һ������
	
	//�޲ι�����
	public Handler() {
		super();
	}
	//���ι�����
	public Handler(Object controller, Method method) {
		super();
		this.controller = controller;
		this.method = method;
	}
	
	@Override
	public String toString() {
		return "Handler [controller=" + controller + ", method=" + method + "]";
	}
	
	//����ʹ��
	public Object execute() throws Exception {
		return method.invoke(controller);
	}
	
	//�������Controller��Method����
	public String execute(HttpServletRequest request) throws Exception {
		return (String)method.invoke(controller, request);
	}
	
	
	
}
