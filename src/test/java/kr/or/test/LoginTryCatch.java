package kr.or.test;

class NotExistIDException extends Exception {
	//아아디가 존재하지 않을 때 예외사항 처리 내용
	public NotExistIDException() {}//생성자 메서드 클래스와 똑같은이름의 메서드 
	public NotExistIDException(String message) {
		
	}
}
class WrongPasswordException extends Exception {
	//패스워드 입력 오류에 대한 예외사항 처리 내용
	public WrongPasswordException() {}
	public WrongPasswordException(String message) {}
	
}
public class LoginTryCatch {

	public static void main(String[] args) {
		try {
			login("white","12345");//로그인1
		} catch (NotExistIDException e) {
			e.printStackTrace();
		} catch (WrongPasswordException e) {
			e.printStackTrace();
		}
		try {
			login("blue","54321");//로그인2
		} catch (NotExistIDException e) {
			e.printStackTrace();
		} catch (WrongPasswordException e) {
			e.printStackTrace();
		}
	}
	
	public static void login(String id, String password) throws NotExistIDException, WrongPasswordException {
		//id가 'blue'가 아니라면 NotExistIDException 발생시킴
		if(!id.equals("blue")) {
			//강제 오류예외발생 - NotExistIDException
			throw new NotExistIDException("아이디가 존재하지 않습니다.");
		}
		//password가 "12345"가 아니라면 WrongPasswordException 발생시킴
		if(!password.equals("12345")) {
			//강제 오류예외발생 - NotExistIDException
			throw new WrongPasswordException("패스워드가 일치하지 않습니다.");
		}
	}

}