package com.hone;

import com.hone.pc.web.socket.WebSocketServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@SpringBootApplication
@MapperScan("com.hone.dao")
@EnableScheduling
@EnableAsync
@ServletComponentScan
public class HongoneApplication     {
//public class HongoneApplication  extends SpringBootServletInitializer{
	public static void main(String[] args) {
		ConfigurableApplicationContext configurableApplicationContext =SpringApplication.run(HongoneApplication.class, args);
		//解决WebSocket不能注入的问题
		WebSocketServer.setApplicationContext(configurableApplicationContext);
	}

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(HongoneApplication.class);
//	}
//
//	@Override
//	public void onStartup(ServletContext servletContext) throws ServletException {
//		super.onStartup(servletContext);
//		WebSocketServer.setApplicationContext(WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext));
//	}

}
