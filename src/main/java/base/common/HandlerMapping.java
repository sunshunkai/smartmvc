package base.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.annotation.RequestMapping;

/**
 * 映射处理器: 负责建立请求路径与处理器的对应关系
 *
 */
public class HandlerMapping {

	// mappings用于存放请求路径与处理器的对应关系
	private Map<String, Handler> mappings = new HashMap<String, Handler>();

	/**
	 * 依据请求路径返回Handler对象。 注: Handler对象封装了处理器对象及方法对象， 方便利用java反射来调用处理器的方法。
	 */
	public Handler getHandler(String path) {
		return mappings.get(path);
	}

	public void process(List beans) {
		for (Object bean : beans) {
			// 获得Class对象
			Class clazz = bean.getClass();
			// 获得所有方法
			Method[] methods = clazz.getDeclaredMethods();
			// 遍历所有方法
			for (Method mh : methods) {
				// 获得@RequestMapping注解
				RequestMapping rm = mh.getDeclaredAnnotation(RequestMapping.class);
				// 获得请求路径
				String path = rm.value();
				// 存放请求路径与处理器的对应关系
				mappings.put(path, new Handler(mh, bean));
			}

		}
		System.out.println("mappings:" + mappings);
	}

}
