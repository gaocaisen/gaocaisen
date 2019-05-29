package mvc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * ��������Map���ϵ���
 * 
 * 1.Map���Ͻ�����url��Handler��ӳ���ϵ
 * 2.�ṩ��Map���г�ʼ���ķ���
 * 3.�ṩ��ʹ��url��Map�в�ѯHandler�ķ���
 * @author 28049
 *
 */
public class HandlerMapping {
	Map<String,Handler> mapping = new HashMap<String,Handler>();
	/**
	 * 1.����className��Ӧ����-��Controller
	 * 2.������������з����ͷ�����ע��
	 * 3.��ע��ͷ����Ķ�Ӧ��ϵ������Map��
	 * 
	 * @param className ΪController�İ���.����
	 * @throws Exception 
	 */
	public void parseController(String className) throws Exception {
		Class cls = Class.forName(className);
		
		Object obj = cls.newInstance();//���䴴��controller����
		
		Method[] methods = cls.getDeclaredMethods();
		for (Method method : methods) {
			//�Ӹ÷����ϻ�ȡrequestMappingע��
			RequestMapping rm = method.getAnnotation(RequestMapping.class);
			if(rm!=null) {
				String url = rm.value();//��ȡ��ע���ֵ
				Handler handler = new Handler(obj,method);//����handler����
				mapping.put(url, handler);
			}
		}
	}
	
	@Override
	public String toString() {
		return "HandlerMapping [mapping=" + mapping + "]";
	}
	/**
	 * �����û������url���Ҷ�Ӧ��Handler����
	 * @param url �û������url
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
			//������
			Object result = handler.execute();
			System.out.println(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
