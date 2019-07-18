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
		
		//加载类
		Class clazz = Class.forName(className);
		
		//实例化
		Object object = clazz.newInstance();
		System.out.println("object:" + object);
		
		//找出所有方法
		Method[] methods = 
				clazz.getDeclaredMethods();
		
		//遍历方法，找出带有@Test注解的方法，然后执行。
		for(Method mh : methods){
			
			Test test = 
					mh.getDeclaredAnnotation(
							Test.class);
			System.out.println("test:" + test);
			
			if(test != null){
				
				//读取注解的属性值
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
