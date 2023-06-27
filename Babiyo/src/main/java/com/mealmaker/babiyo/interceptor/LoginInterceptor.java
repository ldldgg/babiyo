package com.mealmaker.babiyo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mealmaker.babiyo.member.model.MemberDto;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    		throws Exception {
    	
    	HttpSession session = request.getSession();
    	MemberDto memberDto = (MemberDto) session.getAttribute("_memberDto_");

        //@RequestMapping: HandlerMethod가 넘어온다.
        //정적 리소스: ResourcehttpRequesthandler가 넘어온다.
        if (memberDto == null) {
        	
        	String uri = request.getRequestURI().substring(8);
        	
        	response.sendRedirect("/babiyo/auth/login?uri=" + uri);
        	
        	return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler
    		, ModelAndView modelAndView) throws Exception {
    	
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response
    		, Object handler, Exception ex) throws Exception {
    }
}
