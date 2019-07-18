package demo;

import base.annotation.RequestMapping;

/**
 * 处理器：
 * 		负责业务逻辑的处理，当然，也可以
 * 	调用其它类来实现比较复杂的业务逻辑。
 *
 */
public class HelloController {
	
	
	@RequestMapping("/hello.do")
	public String hello(){
		System.out.println(
				"HelloController的hello方法");
		return "hello";
	}
	
	
	
}









