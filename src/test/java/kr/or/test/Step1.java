package kr.or.test;

public class Step1 {

	public static void main(String[] args) {
		System.out.println("내장변수 자료형 소스");
		//필드=변수표기 //카멜-camel-낙타등표기법
		String name; int age; String phoneNum;
		name = "홍길동"; age = 45; phoneNum="000-0000-0000";
		//메서드의 매개변수로(같은말 : 인자-파라미터) 값 전달
		printMember(name, age, phoneNum);
		name = "성춘향"; age = 100; phoneNum="111-1111-1111";
		//메서드의 매개변수로(같은말 : 인자-파라미터) 값 전달
		printMember(name, age, phoneNum);
		name = "각시탈"; age = 3; phoneNum="333-3333-3333";
		//메서드의 매개변수로(같은말 : 인자-파라미터) 값 전달
		printMember(name, age, phoneNum);
		
		System.out.println("내장변수 배열자료형 소스");//[]배열자료
		String[] names = {"홍길동","성춘향","각시탈"};
		int[] ages = {45,100,3};
		String[] phoneNums = {"000-0000-0000","111-1111-1111","333-3333-3333"};		
		printMember(names,ages,phoneNums);
	}
	
	//오버로드-같은이름의 매개변수가 다른 메서드
	private static void printMember(String[] names, int[] ages, String[] phoneNums) {
		int cntLength = names.length;
		System.out.println("매개변수 이름의 갯수는 " + cntLength + "입니다.");
		//배열을 값을 참조할 때 주소(index)를 사용하는데 0번 주소부터 시작 
		for(int cnt=0;cnt<cntLength;cnt++) {			
			System.out.println("이름은: "+ names[cnt] +"|나이는: " +ages[cnt]+ "|전화번호는: "+phoneNums[cnt]);
		}
		
	}


	private static void printMember(String name, int age, String phoneNum) {
		System.out.println("이름은: "+ name +"|나이는: " +age+ "|전화번호는: "+phoneNum);
		
	}

}
