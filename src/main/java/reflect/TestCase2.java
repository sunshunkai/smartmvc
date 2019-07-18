package reflect;

import java.lang.reflect.Method;

public class TestCase2 {

	public static void main(String[] args)
			throws Exception {
			Class clazz = 
					Class.forName("reflect.B");
			Object obj = clazz.newInstance();
			
			Method[] methods = 
					clazz.getDeclaredMethods();
			
			for(Method mh : methods){
				
				//获得方法的参数类型
				Class[] types = 
						mh.getParameterTypes();
				if(types.length > 0){
					//该方法带参
					Object[] params = 
							new Object[types.length];
					for(int i = 0; i < types.length; i++){
						if(types[i] == String.class){
							params[i] = "李白";
						}
						if(types[i] == int.class){
							params[i] = 100;
						}
					}
					mh.invoke(obj, params);
					
				}else{
					//该方法不带参
					mh.invoke(obj);
				}
				
			}
			
			
			
	}

}
