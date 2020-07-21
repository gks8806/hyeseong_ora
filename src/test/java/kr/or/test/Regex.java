package kr.or.test;

import java.util.regex.Pattern;

public class Regex {

	public static void main(String[] args) {
		String id = "5Angel1004";
		String regExp = "[a-zA-Z0-9][a-zA-Z0-9]{7,11}";
		boolean isMatch = Pattern.matches(regExp, id);
		if(isMatch) {
			System.out.println("아이디로 사용가능 합니다.");
		}else {
			System.out.println("아이디는 최소 8자 최대 12자까지 가능합니다. 영문 소문자, 숫자와 특수기호(_),(-)만 사용가능합니다.");
		}
	}

}
