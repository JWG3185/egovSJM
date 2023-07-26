package com.exam.myapp.comm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.exam.myapp.member.MemberVo;

// 다수의 컨트롤러 실행 전후에 수행해야하는 공통작업들은
// 핸들러인터셉터를 사용하여 구현 가능

//public class LoginInterceptor extends HandlerInterceptorAdapter {
public class LoginInterceptor implements HandlerInterceptor {
	
	// 컨트롤러 실행 전에 실행되는 메서드
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		// handler : 현재 인터셉터 이후에 실행될 인터셉터 또는 컨트롤러
		// return값 : 이후에 실행될 인터셉터 또는 컨트롤러의 실행 여부
		
		// 요청을 보낸 사용자의 세션을 가져오고
		HttpSession session = request.getSession();
		// 세션에 로그인 정보를 꺼내와서
		MemberVo vo = (MemberVo) session.getAttribute("loginUser");
		
		// 로그인 정보가 없을 때
		if(vo==null) {
			//로그인 페이지로 이동
			response.sendRedirect(request.getContextPath() + "/member/login.do");
			return false;	// 컨트롤러 실행하지 않음
		}
		
		// 로그인 정보가 있을 때 
		return true;		// 컨트롤러 실행
	}
	
	// 컨트롤러 실행 후, 뷰(JSP) 실행 전에 실행되는 메서드
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {

	}
	
	// 뷰 렌더링(JSP 실행 및 출력) 완료 후에 실행되는 메서드
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
		throws Exception {

	}
		
}
