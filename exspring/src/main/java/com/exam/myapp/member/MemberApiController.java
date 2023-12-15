package com.exam.myapp.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController		// 클래스 내부의 모든 요청처리 메서드에 @ResponseBody 일괄 적용
@RequestMapping("/api")
public class MemberApiController {
	
	@Resource
	private MemberService memberService;
	
	//@ResponseBody
	//@RequestMapping(value = "/member/list", method = RequestMethod.GET)
	@GetMapping("/member/list")
	public List<MemberVo> List(){
		
		List<MemberVo> list = memberService.selectMemberList();
		
		return list;

	}
	
	//@ResponseBody
	//@RequestMapping(value = "/member/add", method = RequestMethod.POST)
	@PostMapping("/member/add")
	// 스프링에 등록된 표준 BeanValidator를 사용하여
	// 저장된 값을 검증하고 싶은 객체 매개변수 앞에 @Valid 적용
	// @Valid 매개변수 다음 위치에 검증 결과를 저장하기 위한 
	// BindingResult 또는 Errors 타입의 매개 변수를 추가
	// <annotation-driven />때문에 설정파일 수정 안해도 @Valid가 등록됨
	public Map<String, Object> add(@ModelAttribute("mvo") @Valid MemberVo vo, BindingResult result) {
		
		if(result.hasErrors()) {	// 검증결과 오류가 있다면
			
			for (FieldError error : result.getFieldErrors()) {
				System.out.println("* 필드이름 : " + error.getField() + " *");
				for (String code : error.getCodes()) {
					System.out.println("* 오류코드 : " + code);
				}
			}
			
			throw new RuntimeException("회원 정보 검증 오류");	// 회원정보 입력 화면(JSP) 출력(실행)
		}
		int n = memberService.insertMember(vo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num", n);
		
		return map;
		
	}
	
	//@ResponseBody
	//@RequestMapping(value = "/member/edit", method = RequestMethod.GET)
	@GetMapping("/member/edit")
	public MemberVo editform(String memId) {
		MemberVo vo = memberService.selectMember(memId);
		return vo;
	}
	
	//@ResponseBody
	//@RequestMapping(value = "/member/edit", method = RequestMethod.POST)
	@PostMapping("/member/edit")
	public Map<String, Object> edit(MemberVo vo) {
		int n = memberService.updateMember(vo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num", n);
		
		return map;
	}
	
	//@ResponseBody
	//@RequestMapping(value = "/member/del", method = RequestMethod.GET)
	@GetMapping("/member/del")
	public Map<String, Object> del(String memId) {
		int n = memberService.deleteMember(memId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num", n);
		
		return map;
	}
	
	
	/*
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
	*/
	
}
