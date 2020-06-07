package com.store.bookshopadmin.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class TimeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("there is preHandle");
//        System.out.println(((HandlerMethod)handler).getBean().getClass().getName());
//        System.out.println(((HandlerMethod)handler).getMethod().getName());
//        request.setAttribute("startTime", new Date().getTime());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        System.out.println("there is postHandle");
//        System.out.println("service time" + (new Date().getTime() - (Long)request.getAttribute("startTime")));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        System.out.println("there is afterCompletion");
//        System.out.println("service time" + (new Date().getTime() - (Long)request.getAttribute("startTime")));
    }
}
