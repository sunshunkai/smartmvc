package base.web;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import base.common.Handler;
import base.common.HandlerMapping;

public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private HandlerMapping handlerMapping;

	@Override
	/**
	 * 读取配置文件(smartmvc.xml)的内容,将所有 处理器实例化，然后将这些处理器实例交给 HandlerMapping来处理。 注:
	 * HandlerMapping负责建立请求路径与处理器的 对应关系。
	 */
	public void init() throws ServletException {
		// 读取配置文件位置及文件名
		String configLocation = getServletConfig().getInitParameter("configLocation");

		SAXReader sax = new SAXReader();

		InputStream in = getClass().getClassLoader().getResourceAsStream(configLocation);

		try {
			/*
			 * 利用dom4j读取配置文件的内容, SAXReader的read方法的返回值可以 想像一棵树，我们可以从根节点开始， 一层一层遍历。
			 */
			Document doc = sax.read(in);
			// 找到根节点
			Element root = doc.getRootElement();
			// 找出根节点的所有子节点
			List<Element> elements = root.elements();
			List beans = new ArrayList();
			// 遍历子节点，读取处理器类名
			for (Element ele : elements) {
				String className = ele.attributeValue("class");
				System.out.println("className:" + className);
				// 将处理器实例化
				Object bean = Class.forName(className).newInstance();
				beans.add(bean);
			}
			System.out.println("beans:" + beans);

			// 将处理器实例交给HandlerMapping来处理
			handlerMapping = new HandlerMapping();
			handlerMapping.process(beans);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}

	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 读取请求资源路径，为了方便处理，截取 请求资源路径的一部分，生成请求路径。
		 */
		String uri = request.getRequestURI();
		System.out.println("uri:" + uri);

		String contextPath = request.getContextPath();
		System.out.println("contextPath:" + contextPath);

		String path = uri.substring(contextPath.length());
		System.out.println("path:" + path);

		/*
		 * 依据请求路径(path)找到对应的处理器来处理。 注: 处理器及方法对象封装到了Handler对象里面。
		 */
		Handler handler = handlerMapping.getHandler(path);
		System.out.println("handler:" + handler);

		/*
		 * 利用Handler对象来调用处理器的方法
		 */
		Method mh = handler.getMethod();
		Object bean = handler.getObj();

		Object returnVal = null;
		try {

			returnVal = mh.invoke(bean);

			System.out.println("returnVal:" + returnVal);

			/*
			 * 如果视图名是以"redirect:"开头，则重定向， 否则，转发
			 */
			String viewName = returnVal.toString();
			if (viewName.startsWith("redirect:")) {
				/*
				 * 将视图名转换成一个重定向地址, 然后重定向。
				 */
				String redirectPath = contextPath + "/" + viewName.substring("redirect:".length());
				response.sendRedirect(redirectPath);

			} else {
				/*
				 * 将视图名转换成一个jsp地址，然后转发 注： jsp地址 = "/WEB-INF/" + 视图名 + ".jsp"
				 */
				String jspPath = "/WEB-INF/" + returnVal + ".jsp";
				request.getRequestDispatcher(jspPath).forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}

	}

}
