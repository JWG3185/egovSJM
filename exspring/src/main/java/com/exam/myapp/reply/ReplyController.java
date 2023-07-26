package com.exam.myapp.reply;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.exam.myapp.member.MemberVo;

//@Controller
@RestController		// 현재 클래스의 모든 요청처리 메서드에 @ResponseBody를 적용
@RequestMapping("/reply/")
public class ReplyController {

	@Autowired
	private ReplyService replyService;
	
	@PostMapping("add.do")	
	//@ResponseBody	// 메서드의 반환값을 그대로 응답메시지 내용으로 전송
	public Map<String, Object> add(ReplyVo vo, @SessionAttribute("loginUser") MemberVo mvo) {
		vo.setRepWriter(mvo.getMemId());
		
		int num = replyService.insertReply(vo);
//-------------------------------------------------------------------------------		
//		MemberVo v = new MemberVo();
//		v.setMemId("a001");
//		v.setMemName("고길동");
//		v.setMemPoint(10);
//		
//		// 자바스크립트로 위의 v와 동일한 데이터를 저장한 객체
//		var v = {memId : "a001", memName : "고길동", memPoint : 10};
//		
//		// JSON은 자바스크립트 객체 표현과 동일하지만 2가지 차이점 존재
//		// (1) 문자열은 반드시 큰따옴표만 가능
//		// (2) 객체의 속성이름은 반드시 문자열로 표현
//		"{\"memId\" : \"a001\", \"memName\" : \"고길동\", \"memPoint\" : 10}"
//-------------------------------------------------------------------------------	
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("ok", true);
		map.put("result", num);
		
		
//		return "redirect:/bbs/edit.do?bbsNo=" + vo.getRepBbsNo();
//		return num + "개의 댓글 저장";
//		return "{\"ok\" : true, \"result\" : " + num + "}";
		return map;
		
	}
	
	@GetMapping("list.do")	
	//@ResponseBody	// 메서드의 반환값을 그대로 응답메시지 내용으로 전송
	public List<ReplyVo> list(int repBbsNo) {

		List<ReplyVo> rlist = replyService.selectReplyList(repBbsNo);
		
		return rlist;
	}
	


	@GetMapping("del.do")	
	//@ResponseBody	// 메서드의 반환값을 그대로 응답메시지 내용으로 전송
	public Map<String, Object> del(ReplyVo vo, @SessionAttribute("loginUser") MemberVo mvo) {
		
		vo.setRepWriter(mvo.getMemId());
		//List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	
		int num = replyService.deleteReply(vo);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("num", vo.getRepNo());
		map.put("result", num);
		
		return map;
	}
	
}
