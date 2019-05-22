package com.hone;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.hone.dao")
public class HongoneApplication  {

	public static void main(String[] args) {
		SpringApplication.run(HongoneApplication.class, args);
	}


}
