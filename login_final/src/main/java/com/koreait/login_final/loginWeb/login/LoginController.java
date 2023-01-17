package com.koreait.login_final.loginWeb.login;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koreait.login_final.loginWeb.session.SessionConst;
import com.koreait.login_final.member.Member;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;
	
	/*
	 * 1. 로그인 으로 이동 
	 * 사용자가 "login" URI 에서 요청시
	 * "login/loginForm" 위치로 이동 
	 */
	
	@GetMapping("login")
	public String loginForm(@ModelAttribute("loginForm") LoginForm loginform) {
		return "login/loginForm";
	}
	
	/*
	 * 2-1. 로그인 하기 
	 * 
	 * Cookie 사용 시 
	 */
	
//	@PostMapping("/login")
	public String login (@ModelAttribute LoginForm form,
			Model model, RedirectAttributes redirectAttributes, HttpServletResponse response) {
		
		Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
		
		// 로그인 실패 시 - "login/loginForm" 으로 이동
		if ( loginMember == null) {
			model.addAttribute("msg", "로그인 실패");
			return "login/loginForm";
		}
		
		// 로그인 성공 시 
		Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
		response.addCookie(idCookie);
		
		redirectAttributes.addFlashAttribute("msg", "로그인 성공");
		
		return "redirect:/";
	}
	
	/*
	 * 2-2 로그인 하기 
	 * Session 사용시 
	 */
	
//	@PostMapping("/login")
	public String login2(@ModelAttribute LoginForm form,
			Model model, RedirectAttributes redirectAttributes, HttpServletRequest req) {

		Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
		
		// 로그인 실패 
		if( loginMember == null) {
		
			model.addAttribute("msg", "로그인 실패");
			return "login/loginForm";
		}
		
		// 로그인 성공 
		HttpSession session = req.getSession();
		
		// 세션에 로그인 회원 정보 보관
		session.setAttribute( SessionConst.LOGIN_MEMBER, loginMember);
		
		redirectAttributes.addFlashAttribute("msg", "로그인 성공");
		return "redirect:/";
		
	}
	
	@PostMapping("/login")
	public String login3 (@ModelAttribute LoginForm form,
			Model model, RedirectAttributes redirectAttributes
			, HttpServletRequest req
			, @RequestParam(defaultValue = "/")String redirectURL) {
		
		Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
		
		// 로그인 실패 
		if( loginMember == null) {
			model.addAttribute("msg", "로그인 실패");
			return "login/loginForm";
		}
		
		// 로그인 성공
		HttpSession session = req.getSession();
		
		session.setAttribute( SessionConst.LOGIN_MEMBER, loginMember);
		
		redirectAttributes.addFlashAttribute("msg", "로그인 성공");
		return "redirect:/";
	}
	
	/*
	 *  3-1. 로그아웃 ( 쿠키 사용 ) 
	 *  로그아웃 시 이전화면 이동 + 동시에 쿠키 값 리셋 
	 */
//	@PostMapping("/logout")
	public String logout( HttpServletResponse response) {
		
		// cookie 안에 memberId 에 null 을 할당 
		Cookie cookie = new Cookie("memberId", null);
		
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		
		return "redirect:/";
	}
	
	/*
	 * 3-2 로그아웃 ( 세션 사용 ) 
	 * 
	 * 로그아웃 시 이전 화면 이동 + 동시에 세션 값 삭제 
	 */
	
	@PostMapping("/logout")
	public String logout2( HttpServletRequest req) {
		
		/*
		 *  req.getSession(true)
		 *  	세션이 있으면 기존 세션을 반환
		 *  	단, 세션이 없으면 새로운 세션을 생성해서 반환한다 
		 *  
		 *  req.getSession(false)
		 *  	세션이 있으면 기존 세션을 반환
		 *  	세션이 없으면 새로운 세션을 생성하지 않고 null 로 반환 
		 */
		HttpSession session = req.getSession(false);
		
		// 만약 session이 삭제 되지 않는다면 
		// 세션 무효화 = .invalidate() 통해 삭제 
		if( session != null) {
			session.invalidate();
		}
		return "redirect:/";
	}
}
