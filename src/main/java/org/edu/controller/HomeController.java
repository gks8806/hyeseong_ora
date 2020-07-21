package org.edu.controller;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.edu.service.IF_BoardService;
import org.edu.service.IF_MemberService;
import org.edu.util.FileDataUtil;
import org.edu.vo.BoardVO;
import org.edu.vo.MemberVO;
import org.edu.vo.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication; //Authentication은 인증 정보를 의미하는 인터페이스다
import org.springframework.security.core.GrantedAuthority;//GrantedAuthority를 ​​"허가"또는 "권리"라고 생각하십시오. 이러한 "권한"은 (보통) 문자열로 표현됩니다 (getAuthority() 메소드 사용). 이러한 문자열을 통해 사용 권한을 식별하고 유권자가 무언가에 대한 액세스 권한을 부여할지 여부를 결정할 수 있습니다.
import org.springframework.security.core.context.SecurityContextHolder;//모든 접근 주체는 Authentication을 생성한다.이것은 SecuriyContext에 접근 주체(Authentication)와 인증정보(GrantedAuthority)을 담겨져 사용된다.현재 로그인한 사용자 정보를 가지고 있는 바스켓(?)이라 생각하면 될 듯 하다.ThreadLocal에 보관되며, SecurityContextHolder를 통해 접근할 수 있다.
import org.springframework.security.core.userdetails.UserDetails;//아이디,암호 체크
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Inject
	private IF_MemberService memberService; 
	
	@Inject
	private IF_BoardService boardService;
	
	@Inject
	private FileDataUtil fileDataUtil;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * 게시물관리 > 삭제 입니다.
	 * @throws Exception 
	 */
	@RequestMapping(value = "/board/delete", method = RequestMethod.POST)
	public String boardDelete(@RequestParam("bno") Integer bno, Locale locale, RedirectAttributes rdat) throws Exception {
		List<String> files = boardService.selectAttach(bno);
		boardService.deleteBoard(bno);
		//첨부파일 삭제(아래)
		for(String fileName : files) {
			//삭제 명령문 추가(아래)
			File target = new File(fileDataUtil.getUploadpath(), fileName);
			if(target.exists()) {
				target.delete();
			}
		}
		
		rdat.addFlashAttribute("msg", "삭제");
		return "redirect:/board/list";
	}
	
	/**
	 * 게시물관리 > 수정 입니다.
	 * @throws Exception 
	 */
	@RequestMapping(value = "/board/update", method = RequestMethod.GET)
	public String boardUpdate(@ModelAttribute("pageVO") PageVO pageVO, @RequestParam("bno") Integer bno, Locale locale, Model model) throws Exception {
		BoardVO boardVO = boardService.viewBoard(bno);
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("pageVO",pageVO);
		return "/board/board_update";
	}
	@RequestMapping(value = "/board/update", method = RequestMethod.POST)
	public String boardUpdate(@ModelAttribute("pageVO") PageVO pageVO, MultipartFile file, @Valid BoardVO boardVO,Locale locale, RedirectAttributes rdat) throws Exception {
		if(file.getOriginalFilename() == "") {//조건:첨부파이 전송 값이 없다면
			boardService.updateBoard(boardVO);
		}else {
			//이전 첨부파일 삭제처리(아래)
			List<String> delFiles = boardService.selectAttach(boardVO.getBno());
			for(String fileName : delFiles) {
				//삭제 명령문 추가(아래)
				File target = new File(fileDataUtil.getUploadpath(), fileName);
				if(target.exists()) { //조건 :해당경로에 파일명이 존재하면
					target.delete(); //퍼알석재
				}//End if
			}//End for
			//아래서 부터 신규 파일 업로드
			String[] files = fileDataUtil.fileUpload(file);//실제 파일업로드후 파일명 리턴
			boardVO.setFiles(files);//데이터베이스 <-> VO(get,set) <-> DAO클래스
			boardService.updateBoard(boardVO);
		}//End if
		
		
		rdat.addFlashAttribute("msg", "수정");
		return "redirect:/board/view?bno=" + boardVO.getBno() + "&page=" + pageVO.getPage();
	}
	
	/**
	 * 게시물관리 > 등록 입니다.
	 * @throws Exception 
	 */
	@RequestMapping(value = "/board/write", method = RequestMethod.GET)
	public String boardWrite(Locale locale, Model model) throws Exception {
		
		return "board/board_write";
	}
	@RequestMapping(value = "/board/write", method = RequestMethod.POST)
	public String boardWrite(MultipartFile file,@Valid BoardVO boardVO,Locale locale, RedirectAttributes rdat) throws Exception {
		//System.out.println("===첨부파일없이저장=-=" +file)
		if(file.getOriginalFilename() == "") {
			//첨부파일 없이 저장
			boardService.insertBoard(boardVO); 
		}else {
			String[] files = fileDataUtil.fileUpload(file);
			boardVO.setFiles(files);
			boardService.insertBoard(boardVO);	
		}
		rdat.addFlashAttribute("msg", "입력");
		return "redirect:/board/list";
	}
	
	/**
	 * 게시물관리 상세보기 입니다.
	 * @throws Exception 
	 */
	 @RequestMapping(value = "/board/view", method = RequestMethod.GET)
	   public String boardView(@ModelAttribute("pageVO") PageVO pageVO, @RequestParam("bno") Integer bno,Locale locale, Model model) throws Exception {
	      BoardVO boardVO = boardService.viewBoard(bno);
	      //여기서 부터 첨부파일명 때문에 추가
	      List<String> files = boardService.selectAttach(bno);
			String[] filenames = new String[files.size()];
			int cnt = 0;
			for(String fileName : files) {
				filenames[cnt++] = fileName; 
			}
	      
	      //여러개 파일에서 1개 파일만 받는 것으로 변경
	      //String[] filenames = new String[] {files};
	      boardVO.setFiles(filenames);//String[]
	      //여기까지 첨부파일때문에 추가
	      model.addAttribute("boardVO", boardVO);
	      model.addAttribute("pageVO", pageVO);
	      model.addAttribute("extNameArray", fileDataUtil.getExtNameArray());
	      return "board/board_view";
	   }
	 
	/**
	 * 게시물관리 리스트 입니다.
	 * @throws Exception 
	 */
	@RequestMapping(value = "/board/list", method = RequestMethod.GET)
	public String boardList(@ModelAttribute("pageVO") PageVO pageVO, Locale locale, Model model) throws Exception {
		//PageVO pageVO = new PageVO(); //매개변수로 받기전에 테스트용
		if(pageVO.getPage() == null) {
			pageVO.setPage(1);//초기 page변수값 지정
		}
		pageVO.setPerPageNum(10); //1페이지당 보여줄 게시물 강제지정
		pageVO.setTotalCount(boardService.countBno(pageVO));//강제로 입력한 값을 쿼리로 대체할 예정
		List<BoardVO> list = boardService.selectBoard(pageVO);
		//모델클래스로 jsp화면으로 boardService에서 셀렉트한 list값을 boardList변수명으로 보낸다.
		//model { list -> boardList -> jsp }
		model.addAttribute("boardList", list);
		model.addAttribute("pageVO", pageVO);
		return "board/board_list";
	}
	
	/**
	 * 스프링 시큐리티 security-context.xml설정한 로그인 처리 결과 화면
	 * @throws Exception 
	 */
	
	@RequestMapping(value = "/login_success", method = RequestMethod.GET)
		public String login_success(Locale locale,HttpServletRequest request, RedirectAttributes rdat) throws Exception {
			logger.info("Welcome login_success! The client locale is {}.", locale);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			//Authentication 1. 인증처리 (enabled을 통해 아이디 암호비교) , 2 권한 체크 
			String userid = "";//유저아이디
			String levels = "";//ROLE_ANONYMOUS
			Boolean enabled = false;
			Object principal = authentication.getPrincipal();//Principal 구현
			if (principal instanceof UserDetails) {
				//인증이 처리되는 로직(아이디,암호를 스프링 시큐리티에 던져주고 인증은 스프링에서 알아서 해줌)
				enabled = ((UserDetails)principal).isEnabled();
			}
			HttpSession session = request.getSession(); //세션을 초기화 시켜줌.
			if (enabled) { //인증처리가 완료된 사용자의 권한체크(아래)
				Collection<? extends GrantedAuthority>  authorities = authentication.getAuthorities();
				if(authorities.stream().filter(o -> o.getAuthority().equals("ROLE_ANONYMOUS")).findAny().isPresent())
				{levels = "ROLE_ANONYMOUS";}
				if(authorities.stream().filter(o -> o.getAuthority().equals("ROLE_USER,")).findAny().isPresent())
				{levels = "ROLE_USER,";}
				if(authorities.stream().filter(o -> o.getAuthority().equals("ROLE_ADMIN")).findAny().isPresent())
				{levels = "ROLE_ADMIN";}
				userid =((UserDetails)principal).getUsername();
				
				//로그인 세션 저장 
				session.setAttribute("session_enabled", enabled);//인증확인
				session.setAttribute("session_userid", userid);//사용자아이디
				session.setAttribute("session_levels", levels);//사용자권한
				
				//=========== 삳단은 스프링시큐리티에서 기본제공하는 세션 변수처리
				//=========== 하단은 우리가 추가하는 세션 변수처리
				//회원이름 구하기 추가
				String username = "";//이름
				MemberVO memberVO = memberService.viewMember(userid);
				session.setAttribute("session_username", memberVO.getUser_name());//사용자명
	        	}
			rdat.addFlashAttribute("msg", "로그인");//result 데이터를 숨겨서 전송
			return "redirect:/";//새로고침 자동 등록 방지를 위해서 아래처럼 처리
		}
	
	
	/**
	 * 로그인 페이지 파일 입니다.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Locale locale, Model model) {
		
		return "login";
	}
	
	/**
	 * slide 페이지 파일 입니다.
	 */
	@RequestMapping(value = "/sample/slide", method = RequestMethod.GET)
	public String slide(Locale locale, Model model) {
		
		return "sample/slide";
	}
	/**
	 * contactus 페이지 파일 입니다.
	 */
	@RequestMapping(value = "/sample/contactus", method = RequestMethod.GET)
	public String contactus(Locale locale, Model model) {
		
		return "sample/contactus";
	}
	/**
	 * blog 페이지 파일 입니다.
	 */
	@RequestMapping(value = "/sample/blog", method = RequestMethod.GET)
	public String blog(Locale locale, Model model) {
		
		return "sample/blog";
	}
	/**
	 * work 페이지 파일 입니다.
	 */
	@RequestMapping(value = "/sample/work", method = RequestMethod.GET)
	public String work(Locale locale, Model model) {
		
		return "sample/work";
	}
	/**
	 * weare 페이지 파일 입니다.
	 */
	@RequestMapping(value = "/sample/weare", method = RequestMethod.GET)
	public String weare(Locale locale, Model model) {
		
		return "sample/weare";
	}
	
	/**
	 * html5테스트 파일 입니다.
	 */
	@RequestMapping(value = "/sample/HTMLTEST", method = RequestMethod.GET)
	public String HTMLTEST(Locale locale, Model model) {
		
		return "sample/HTMLTEST";
	}
	
	/**
	 * 샘플 파일 홈 입니다.
	 */
	@RequestMapping(value = "/sample", method = RequestMethod.GET)
	public String sample(Locale locale, Model model) {
		
		return "sample/home";
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws Exception 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) throws Exception {
		PageVO pageVO = new PageVO();
		if(pageVO.getPage() == null) {
			pageVO.setPage(1);//초기 page변수값 지정
		}
		pageVO.setPerPageNum(5); //1페이지당 보여줄 게시물 강제지정
		pageVO.setTotalCount(boardService.countBno(pageVO));//강제로 입력한 값을 쿼리로 대체할 예정
		List<BoardVO> list = boardService.selectBoard(pageVO);
		//첨부파일 출력 때문에 추가 Start
		List<BoardVO> boardListFiles = new ArrayList<BoardVO>();
		for(BoardVO vo:list) {
			List<String> files = boardService.selectAttach(vo.getBno());
			String[] filenames = new String[files.size()];
			int cnt = 0;
			for(String fileName : files) {
				filenames[cnt++] = fileName;
			}
			vo.setFiles(filenames);//여기까지는 view 상세보기와 똑같다
			boardListFiles.add(vo);//상세보기에서 추가된 항목
		}
		model.addAttribute("extNameArray", fileDataUtil.getExtNameArray());//첨부파일이 이미지인지 문서파일인지 구분하는 jsp
		//첨부파일 출력 때문에 추가 End 
		 
		
		model.addAttribute("boardList", boardListFiles);//첨부파일 출력때문에 추가 
		return "home";
	}
	
}

