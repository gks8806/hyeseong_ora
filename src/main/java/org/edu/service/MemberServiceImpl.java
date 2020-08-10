package org.edu.service;

import java.util.List;

import javax.inject.Inject;

import org.edu.dao.IF_MemberDAO;
import org.edu.vo.MemberVO;
import org.edu.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements IF_MemberService {

	@Inject
	private IF_MemberDAO memberDAO;
	
	@Override
	public void insertMember(MemberVO memberVO) throws Exception {
		memberDAO.insertMember(memberVO);		
	}

	@Override
	public List<MemberVO> selectMember(PageVO pageVO) throws Exception {
		return memberDAO.selectMember(pageVO);
	}

	@Override
	public void updateMember(MemberVO memberVO) throws Exception {
		memberDAO.updateMember(memberVO);
	}

	@Override
	public void deleteMember(String user_id) throws Exception {
		memberDAO.deleteMember(user_id);
	}

	@Override
	public MemberVO viewMember(String user_id) throws Exception {
		return memberDAO.viewMember(user_id);
	}

	@Override
	public int countUserId(PageVO pageVO) throws Exception {
		return memberDAO.countUserId(pageVO);
	}

	@Override
	public List<MemberVO> testJobMethod() throws Exception {
		List<MemberVO> list = memberDAO.testJobMethod();//memberDAO.testJobMethod()를 List<MemberVO> list로 재정의 시간단축위해
		//실행 상황 확인
		System.out.println(list);
		//6개월동안 회원정보 수정을 안한 회원을 휴먼 계정으로 업데이트(아래)
		System.out.println("==========6개월간 회원정보수정 안한 회원 휴면계정으로 전환");
		//memberDAO.testJobMethod()에서 뽑와서 vo로 
		for(MemberVO vo:list) {
			vo.setEnabled(false);//enable = false로 셋//휴먼계정으로 변경(SET)
			memberDAO.updateMember(vo);
		}
		return memberDAO.testJobMethod();
	}

}