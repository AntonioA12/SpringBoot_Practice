package com.koreait.login_final.loginWeb.login.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.koreait.login_final.loginWeb.session.SessionConst;

public class LoginCheckInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return HandlerInterceptor.super.preHandle(request, response, handler);
	
		String requestURI = request.getRequestURI();
		HttpSession session = request.getSession(false);
		
		if ( session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null ) {
			
			System.out.println("미 인증 사용자 요청");
			response.sendRedirect("/login?redirectURL=" + request.getRequestURI());
			return false;
		}
		return true;
	}
}
