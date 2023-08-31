package kr.or.comeeat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.or.comeeat.member.model.vo.Member;

public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
			HttpSession session = request.getSession();
			Member m = (Member)session.getAttribute("m");
			if(m == null) {
			response.sendRedirect("/member/loginMsg");
			return false;
		}else {
			return true;			
		}
	}



}
