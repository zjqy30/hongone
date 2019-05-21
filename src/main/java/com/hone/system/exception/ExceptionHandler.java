package com.hone.system.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Lijia on 2019/4/17.
 */
@RestControllerAdvice
public class ExceptionHandler {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public ModelAndView error(Exception e, HttpServletRequest request) {
        logger.error("e",e);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.getModel().put("error",e);
        modelAndView.setViewName("error.html");
        return modelAndView;
    }

}
