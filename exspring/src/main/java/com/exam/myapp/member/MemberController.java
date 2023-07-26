package com.exam.myapp.member;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String addform() {
		return "member/memAdd";
	}
	
	@RequestMapping(value = "/member/add.do", method = RequestMethod.POST)
	public String add(MemberVo vo) {
		memberService.insertMember(vo);
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
