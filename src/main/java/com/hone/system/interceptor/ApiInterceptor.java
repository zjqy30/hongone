package com.hone.system.interceptor;

import com.hone.system.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Lijia on 2019/4/17.
 */
public class ApiInterceptor implements HandlerInterceptor {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    private static final ThreadLocal<Long> startTimeThreadLocal =
            new NamedThreadLocal<Long>("ThreadLocal StartTime");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        long beginTime = System.currentTimeMillis();
        startTimeThreadLocal.set(beginTime);

        logger.info("开始计时：{ "+DateUtils.getNowDateTime()+" } URL:{ "+ request.getRequestURI()+" }");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        long beginTime = startTimeThreadLocal.get();
        long endTime = System.currentTimeMillis();

        logger.info("计时结束：{ "+DateUtils.getNowDateTime()+" }  耗时：{ "+ ((endTime-beginTime))+" }ms");
    }
}
