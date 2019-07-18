package reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class TestCase {

	public static void main(String[] args) 
			throws ClassNotFoundException,
			InstantiationException, 
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Scanner scanner = 
				new Scanner(System.in);
		String className = 
				scanner.nextLine();
		System.out.println("className:" + className);
		
		//������
		Class clazz = Class.forName(className);
		
		//ʵ����
		Object object = clazz.newInstance();
		System.out.println("object:" + object);
		
		//�ҳ����з���
		Method[] methods = 
				clazz.getDeclaredMethods();
		
		//�����������ҳ�����@Testע��ķ�����Ȼ��ִ�С�
		for(Method mh : methods){
			
			Test test = 
					mh.getDeclaredAnnotation(
							Test.class);
			System.out.println("test:" + test);
			
			if(test != null){
				
				//��ȡע�������ֵ
				String v1 = test.value();
				System.out.println("v1:" + v1);
				
				Object returnVal = 
						mh.invoke(object);
				System.out.println("returnVal:"
						+ returnVal);
			}
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
