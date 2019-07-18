package base.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.annotation.RequestMapping;

/**
 * ӳ�䴦����: ����������·���봦�����Ķ�Ӧ��ϵ
 *
 */
public class HandlerMapping {

	// mappings���ڴ������·���봦�����Ķ�Ӧ��ϵ
	private Map<String, Handler> mappings = new HashMap<String, Handler>();

	/**
	 * ��������·������Handler���� ע: Handler�����װ�˴��������󼰷������� ��������java���������ô������ķ�����
	 */
	public Handler getHandler(String path) {
		return mappings.get(path);
	}

	public void process(List beans) {
		for (Object bean : beans) {
			// ���Class����
			Class clazz = bean.getClass();
			// ������з���
			Method[] methods = clazz.getDeclaredMethods();
			// �������з���
			for (Method mh : methods) {
				// ���@RequestMappingע��
				RequestMapping rm = mh.getDeclaredAnnotation(RequestMapping.class);
				// �������·��
				String path = rm.value();
				// �������·���봦�����Ķ�Ӧ��ϵ
				mappings.put(path, new Handler(mh, bean));
			}

		}
		System.out.println("mappings:" + mappings);
	}

}
