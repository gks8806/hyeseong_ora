package kr.or.test;

import java.util.HashMap;
import java.util.Map;

//제네릭 타입 클래스 생성
//제네릭타입을 통해 매개변수를 여려개 쓸수 있다 integer string 2개사용
class Container<T> {
	private T t;
	
	public void set(T t) {
		this.t=t;
		
	}
	public T get() {
		return t;
	}
}
class ContainerKV<K,V> {
	private K key;
	private V value;
	
	public void set(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public K getKey() {
		return key;
	}
	public V getValue() {
		return value;
	}
}
public class GenericTypeUser {

	public static void main(String[] args) {
		ContainerKV<String,String> containerKV1 =
				new ContainerKV<String,String>();
		containerKV1.set("name", "홍길동");
		String nameK= containerKV1.getKey();
		String nameV= containerKV1.getValue();
		System.out.println("입련된 ContainerKV변수의 키: "+ nameK);
		System.out.println("입련된 ContainerKV변수의 값: "+ nameV);
		
		Map<String,String> tableKV = new HashMap<String,String>();
		tableKV.put("userid", "user02");//age key에 값을 입력.
		tableKV.put("userpw","1234");
		tableKV.put("username","홍길동");
		System.out.println("아이디는 " + tableKV.get("userid")+ "입니다.");
		System.out.println("암호는 " + tableKV.get("userpw")+ "입니다.");
		System.out.println("이름은 " + tableKV.get("username")+ "입니다.");
		
		Container<String> container1 = 
				new Container<String> ();
		//컨테이너1 t변수: 스트링 홀길동 
		container1.set("홍길동");
		
		Container<Integer> container2 =
				new Container<Integer>();
		//컨테이너2 t변수: 정수형6
		container2.set(6);
		int age = container2.get();
		String name = container1.get();
		System.out.println("이름은:"+name
				+" 나이는:"+age+"세");
		
		
	}

}
