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
	 * ��ȡ�����ļ�(smartmvc.xml)������,������ ������ʵ������Ȼ����Щ������ʵ������ HandlerMapping������ ע:
	 * HandlerMapping����������·���봦������ ��Ӧ��ϵ��
	 */
	public void init() throws ServletException {
		// ��ȡ�����ļ�λ�ü��ļ���
		String configLocation = getServletConfig().getInitParameter("configLocation");

		SAXReader sax = new SAXReader();

		InputStream in = getClass().getClassLoader().getResourceAsStream(configLocation);

		try {
			/*
			 * ����dom4j��ȡ�����ļ�������, SAXReader��read�����ķ���ֵ���� ����һ���������ǿ��ԴӸ��ڵ㿪ʼ�� һ��һ�������
			 */
			Document doc = sax.read(in);
			// �ҵ����ڵ�
			Element root = doc.getRootElement();
			// �ҳ����ڵ�������ӽڵ�
			List<Element> elements = root.elements();
			List beans = new ArrayList();
			// �����ӽڵ㣬��ȡ����������
			for (Element ele : elements) {
				String className = ele.attributeValue("class");
				System.out.println("className:" + className);
				// ��������ʵ����
				Object bean = Class.forName(className).newInstance();
				beans.add(bean);
			}
			System.out.println("beans:" + beans);

			// ��������ʵ������HandlerMapping������
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
		 * ��ȡ������Դ·����Ϊ�˷��㴦����ȡ ������Դ·����һ���֣���������·����
		 */
		String uri = request.getRequestURI();
		System.out.println("uri:" + uri);

		String contextPath = request.getContextPath();
		System.out.println("contextPath:" + contextPath);

		String path = uri.substring(contextPath.length());
		System.out.println("path:" + path);

		/*
		 * ��������·��(path)�ҵ���Ӧ�Ĵ����������� ע: �����������������װ����Handler�������档
		 */
		Handler handler = handlerMapping.getHandler(path);
		System.out.println("handler:" + handler);

		/*
		 * ����Handler���������ô������ķ���
		 */
		Method mh = handler.getMethod();
		Object bean = handler.getObj();

		Object returnVal = null;
		try {

			returnVal = mh.invoke(bean);

			System.out.println("returnVal:" + returnVal);

			/*
			 * �����ͼ������"redirect:"��ͷ�����ض��� ����ת��
			 */
			String viewName = returnVal.toString();
			if (viewName.startsWith("redirect:")) {
				/*
				 * ����ͼ��ת����һ���ض����ַ, Ȼ���ض���
				 */
				String redirectPath = contextPath + "/" + viewName.substring("redirect:".length());
				response.sendRedirect(redirectPath);

			} else {
				/*
				 * ����ͼ��ת����һ��jsp��ַ��Ȼ��ת�� ע�� jsp��ַ = "/WEB-INF/" + ��ͼ�� + ".jsp"
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
