package com.exam.myapp.bbs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.exam.myapp.member.MemberVo;

@Controller
@RequestMapping("/bbs/")	// 현재 컨트롤러 클래스 내부의 모든 메서드들의 공통 경로 설정
public class BbsController {
	
	@Autowired
	private BbsService bbsService;
	
	@RequestMapping(value = "list.do")
	// @GetMapping("list.do")
	public String List(Model model, SearchVo svo){
		int cnt = bbsService.selectBbsCount(svo);	// 전체 레코드 수 조회
		svo.setTotalRecordCount(cnt);				// 전체 레코드 수 정보 설정
		svo.makePageHtml();							// 페이징 처리에 필요한 값들 계산
		
		List<BbsVo> list = bbsService.selectBbsList(svo);
		model.addAttribute("bbsList", list);
		return "bbs/bbsList";
	}
	
	@RequestMapping(value = "add.do", method = RequestMethod.GET)
	public String addform() {
		return "bbs/bbsAdd";
	}
	
	@RequestMapping(value = "add.do", method = RequestMethod.POST)
	// @PostMapping("add.do")
	public String add(BbsVo vo
			//, HttpSession session
			, @SessionAttribute("loginUser") MemberVo mvo	// 지정한 세션 속성값(loginUser)을 mvo 변수에 주입(전달)
			) {
		// MemberVo mvo = (MemberVo) session.getAttribute("loginUser");
		vo.setBbsWriter(mvo.getMemId());
		bbsService.insertBbs(vo);
		return "redirect:/bbs/list.do";
	}
	
	@RequestMapping(value = "edit.do", method = RequestMethod.GET)
	public String editform(int bbsNo, Model model) {
		BbsVo vo = bbsService.selectBbs(bbsNo);
		model.addAttribute("bbsVo", vo);
		return "bbs/bbsEdit";
	}
	
//	@RequestMapping(value = "edit.do", method = RequestMethod.POST)
	@PostMapping("edit.do")
	public String edit(BbsVo vo, HttpSession session, ModelMap model) {
		
		MemberVo uvo = (MemberVo) session.getAttribute("loginUser");
		
		vo.setBbsWriter(uvo.getMemId());
		
//		if(uvo == null ||uvo.getMemId() == null || uvo.getMemId().equals("")){
//			model.addAttribute("message", "작성자 본인만 수정가능합니다. 로그인 후 이용해주세요.");
//			System.out.println("작성자 본인만 수정가능합니다. 로그인후 이용해주세요.");
//			return "forward:/member/login.do";
//        }
//		
//		// 본인만 수정 가능하게
//		BbsVo realvo = bbsService.selectBbs(vo.getBbsNo());
//		
//		if(!uvo.getMemId().equals(realvo.getBbsWriter() ) ) {
//			System.out.println("작성자 본인만 수정가능합니다.");
//			model.addAttribute("message", "작성자 본인만 수정가능합니다.");
//			return "forward:/bbs/list.do";
//		}
		
		bbsService.updateBbs(vo);
		return "redirect:/bbs/list.do";
	}
	
//	@RequestMapping(value = "del.do", method = RequestMethod.GET)
	@GetMapping("del.do")
	public String del(BbsVo vo, HttpSession session, ModelMap model) {
		
		MemberVo uvo = (MemberVo) session.getAttribute("loginUser");
		
		vo.setBbsWriter(uvo.getMemId());
		
//		if(uvo == null ||uvo.getMemId() == null || uvo.getMemId().equals("")){
//			model.addAttribute("message", "작성자 본인만 삭제 가능합니다. 로그인 후 이용해주세요.");
//			System.out.println("작성자 본인만 수정가능합니다. 로그인후 이용해주세요.");
//			return "forward:/member/login.do";
//        }
//		
//		BbsVo realvo = bbsService.selectBbs(vo.getBbsNo());
//		if(!uvo.getMemId().equals(realvo.getBbsWriter() ) ) {
//			model.addAttribute("message", "작성자 본인만 삭제 가능합니다.");
//			System.out.println("작성자 본인만 삭제가능합니다.");
//			return "forward:/bbs/list.do";
//		}
		
		bbsService.deleteBbs(vo);
		
		return "redirect:/bbs/list.do";
	}
	
	// 컨트롤러 메서드가 인자로 HttpServletResponse, OutputStream, Writer를 받고,
	// 반환타입이 void이면,
	// 직접 응답을 처리(전송)했다고 판단하여 스프링은 뷰에 대한 처리를 하지 않는다.
	@GetMapping("down.do")
	public void down(int attNo, HttpServletResponse response) {
		AttachVo vo = bbsService.selectAttach(attNo);	// DB에서 다운로드할 첨부파일 정보 조회
		
		File f = bbsService.getAttachFile(vo);	// 디스크 상에서 첨부파일의 위치 가져오기
		
		response.setContentLengthLong(f.length());	// 응답메시지 본문(파일)의 크기 설정
//		response.setContentLength((int) f.length()); 
		
		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);	// 무주건 다운로드 팝업창을 띄우도록 설정
//		response.setContentType("application/octet-stream");
		
		// 다운로드 파일을 저장할 때 사용할 디폴트 파일명 설정
		// 지원하는 브라우저에 따라서 다른 처리가 필요할 수도 있다.
		String cdv = ContentDisposition.attachment().filename(vo.getAttOrgName(), StandardCharsets.UTF_8).build().toString();
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, cdv);
//		구형도 포함하는 방식
//		try {
//			String fname = URLEncoder.encode(vo.getAttOrgName(), "utf-8").replace("+", "%20");
//			response.setHeader("Content-Disposition", " attachment; filename*=UTF-8''" + fname);
//		} 
//		catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		
		try {
			// 파일 f의 내용을 응답 객체(의 출력 스트림)에 복사(전송)
			FileCopyUtils.copy(new FileInputStream(f), response.getOutputStream() );
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
