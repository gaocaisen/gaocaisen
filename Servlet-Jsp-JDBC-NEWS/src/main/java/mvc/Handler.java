package mvc;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

/**
 * handler：处理器
 * 建立method和对应的controller对象的映射
 * 提供一个可以动态执行该method的方法
 * @author 28049
 *
 */
public class Handler {
	private Object controller; //子控制器对象
	private Method method; //子控制器的一个方法
	
	//无参构造器
	public Handler() {
		super();
	}
	//带参构造器
	public Handler(Object controller, Method method) {
		super();
		this.controller = controller;
		this.method = method;
	}
	
	@Override
	public String toString() {
		return "Handler [controller=" + controller + ", method=" + method + "]";
	}
	
	//测试使用
	public Object execute() throws Exception {
		return method.invoke(controller);
	}
	
	//反射调用Controller的Method方法
	public String execute(HttpServletRequest request) throws Exception {
		return (String)method.invoke(controller, request);
	}
	
	
	
}
