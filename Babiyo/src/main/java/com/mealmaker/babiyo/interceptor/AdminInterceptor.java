package com.mealmaker.babiyo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mealmaker.babiyo.member.model.MemberDto;

public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    		throws Exception {
    	
    	HttpSession session = request.getSession();
    	MemberDto memberDto = (MemberDto) session.getAttribute("_memberDto_");
    	
    	if(memberDto == null) {
    		response.sendError(401);
    		return false;
    	}
    	
    	int grade = memberDto.getGrade();
    	
        if (grade != 1) {
        	response.sendError(403);
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
