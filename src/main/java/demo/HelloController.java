package demo;

import base.annotation.RequestMapping;

/**
 * ��������
 * 		����ҵ���߼��Ĵ�����Ȼ��Ҳ����
 * 	������������ʵ�ֱȽϸ��ӵ�ҵ���߼���
 *
 */
public class HelloController {
	
	
	@RequestMapping("/hello.do")
	public String hello(){
		System.out.println(
				"HelloController��hello����");
		return "hello";
	}
	
	
	
}









