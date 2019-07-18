package reflect;

public class A {
	
	
	public void f1(){
		System.out.println("A的f1方法");
	}
	
	@Test("hehe")
	public String f2(){
		System.out.println("A的f2方法");
		return "hello f2";
	}
	
	
	
	
	
}
