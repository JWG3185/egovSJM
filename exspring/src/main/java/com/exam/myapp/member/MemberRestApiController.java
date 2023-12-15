package com.exam.myapp.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController		// 클래스 내부의 모든 요청처리 메서드에 @ResponseBody 일괄 적용
@RequestMapping("/rest")
public class MemberRestApiController {
	
	@Resource
	private MemberService memberService;
	
	@GetMapping("/members")
	public List<MemberVo> List(){
		
		List<MemberVo> list = memberService.selectMemberList();
		
		return list;

	}
	
	@PostMapping("/members")
	public ResponseEntity<Map<String, Object>> add(@RequestBody @Valid MemberVo vo, BindingResult result) {
		
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
		
//		return map;
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.CREATED);	// 상태코드를 변경 후 전송하고 싶을 때 ResponseEntity 사용
		
	}
	
	@GetMapping("/members/{memId}")	//경로내에서 경로변수로 저장하고 싶은 부분을 {경로변수명}으로 지정
	public MemberVo editform(@PathVariable("memId") String memId) {
		MemberVo vo = memberService.selectMember(memId);
		return vo;
	}
	
	@PatchMapping("/members/{memId}")
	//@RequestBody: 요청메시지의 본문 내용을 자바 객체로 변환하여 매개변수에 저장
	public Map<String, Object> edit(@RequestBody MemberVo vo) {
		int n = memberService.updateMember(vo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num", n);
		
		return map;
	}
	
	@DeleteMapping("/members/{memId}")
	// 경로변수명과 매개변수명이 동일하면 @PathVariable("memId")의 ("memId") 생략 가능
	public Map<String, Object> del(@PathVariable String memId) {
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
