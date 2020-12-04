package kr.or.connect.reservation.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HttpSession session = request.getSession();
		
		int myReservationTotalCount = (int) modelAndView.getModel().get("myReservationTotalCount");
		String reservationEmail = (String) modelAndView.getModel().get("reservationEmail");
		
		if(myReservationTotalCount == 0) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('입력하신 이메일정보에는 예매 내역이 없습니다. 다시 입력해주세요.'); "
					+ "location.href='http://localhost:8080/login';</script>");
			out.flush();
		}else {
			session.setAttribute("reservationEmail", reservationEmail);
			
		}
		
	}
}


