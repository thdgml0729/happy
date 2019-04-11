package com.happy.prj.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class Interceptor extends HandlerInterceptorAdapter {
	
	private Logger logger = LoggerFactory.getLogger(Interceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		 logger.info("============인터셉터 시작===============");
		 
		 try {
			if(request.getSession().getAttribute("mem")==null) { // 세션에 있는 로그인 정보 
				 response.sendRedirect("./loginForm.do"); // 서버사이드 안쪽이라서 화면으로 보낼수 없다. 
				 return false;
			 }
		} catch (Exception e) {
				e.printStackTrace();
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// ModelAndView : 이객체가 dto처럼?
		 logger.info("============인터셉터 끝===============");
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		 
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		 
		super.afterConcurrentHandlingStarted(request, response, handler);
	}
	

}
