package mvc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 用来管理Map集合的类
 * 
 * 1.Map集合建立了url和Handler的映射关系
 * 2.提供对Map进行初始化的方法
 * 3.提供了使用url从Map中查询Handler的方法
 * @author 28049
 *
 */
public class HandlerMapping {
	Map<String,Handler> mapping = new HashMap<String,Handler>();
	/**
	 * 1.加载className对应的类-》Controller
	 * 2.遍历该类的所有方法和方法的注解
	 * 3.将注解和方法的对应关系保存在Map中
	 * 
	 * @param className 为Controller的包名.类名
	 * @throws Exception 
	 */
	public void parseController(String className) throws Exception {
		Class cls = Class.forName(className);
		
		Object obj = cls.newInstance();//反射创建controller对象
		
		Method[] methods = cls.getDeclaredMethods();
		for (Method method : methods) {
			//从该方法上获取requestMapping注解
			RequestMapping rm = method.getAnnotation(RequestMapping.class);
			if(rm!=null) {
				String url = rm.value();//获取该注解的值
				Handler handler = new Handler(obj,method);//创建handler对象
				mapping.put(url, handler);
			}
		}
	}
	
	@Override
	public String toString() {
		return "HandlerMapping [mapping=" + mapping + "]";
	}
	/**
	 * 根据用户请求的url查找对应的Handler方法
	 * @param url 用户请求的url
	 * @return
	 */
	public Handler getHandler(String url) {
		return mapping.get(url);
	}
	
	public static void main(String[] args) {
		HandlerMapping hand = new HandlerMapping();
		try {
			hand.parseController("mvc.Demo");
			System.out.println(hand);
			
			Handler handler = hand.getHandler("/listUser.do");
			//测试用
			Object result = handler.execute();
			System.out.println(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
