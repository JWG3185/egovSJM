package com.exam.myapp.member;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MemberController {
	
	@Resource
	private MemberService memberService;
	
	@RequestMapping(value = "/member/list.do", method = RequestMethod.GET)
	public String List(Model model){
		
		List<MemberVo> list = memberService.selectMemberList();
		
		model.addAttribute("memberList", list);
		
		return "member/memList";

	}
	
	@RequestMapping(value = "/member/add.do", method = RequestMethod.GET)
	public String addform(@ModelAttribute("mvo") MemberVo vo) {
		return "member/memAdd";
	}
	
	@RequestMapping(value = "/member/add.do", method = RequestMethod.POST)
	// 스프링에 등록된 표준 BeanValidator를 사용하여
	// 저장된 값을 검증하고 싶은 객체 매개변수 앞에 @Valid 적용
	// @Valid 매개변수 다음 위치에 검증 결과를 저장하기 위한 
	// BindingResult 또는 Errors 타입의 매개 변수를 추가
	// <annotation-driven />때문에 설정파일 수정 안해도 @Valid가 등록됨
	public String add(@ModelAttribute("mvo") @Valid MemberVo vo, BindingResult result) {
		
		if(result.hasErrors()) {	// 검증결과 오류가 있다면
			
			for (FieldError error : result.getFieldErrors()) {
				System.out.println("* 필드이름 : " + error.getField() + " *");
				for (String code : error.getCodes()) {
					System.out.println("* 오류코드 : " + code);
				}
			}
			
			return "member/memAdd";	// 회원정보 입력 화면(JSP) 출력(실행)
		}
		memberService.insertMember(vo);
		System.out.println(vo.getMemPass());
		return "redirect:/member/list.do";
		
	}
	
	@RequestMapping(value = "/member/edit.do", method = RequestMethod.GET)
	public String editform(String memId, Model model) {
		MemberVo vo = memberService.selectMember(memId);
		model.addAttribute("mvo", vo);
		return "member/memEdit";
	}
	
	@RequestMapping(value = "/member/edit.do", method = RequestMethod.POST)
	public String edit(MemberVo vo) {
		memberService.updateMember(vo);
		return "redirect:/member/list.do";
	}
	
	@RequestMapping(value = "/member/del.do", method = RequestMethod.GET)
	public String del(String memId) {
		memberService.deleteMember(memId);
		return "redirect:/member/list.do";
	}
	
	@RequestMapping(value = "/member/login.do", method = RequestMethod.GET)
	public String loginform() {
		return "member/login";
	}
	
	@RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
	public String login(MemberVo vo, HttpSession session) {
		MemberVo user = memberService.selectLogin(vo);
		if(user==null) { // 로그인 실패
			return "redirect:/member/login.do";
		}
		else { // 로그인 성공
			session.setAttribute("loginUser", user);
			return "redirect:/member/list.do";
		}
	}
	
	@RequestMapping(value = "/member/logout.do", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();						//세션객체를 제거(후 다시 생성), 모든 속성들도 함께 삭제
		return "redirect:/member/login.do";
	}
	
}
