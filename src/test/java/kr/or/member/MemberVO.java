package kr.or.member;

public class MemberVO {//클래스 안에 변수 넣고 source-generate get set클릭
	private String name;
	private int age;
	private String phoneNum;
	
	public int getAge() {//get으로 인트형 변수 age 빼내오기
		return age;//함수값으로 반환
	}
	public void setAge(int age) {//set으로 인트형 변수 age 입력
		this.age = age;
	}
	
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
