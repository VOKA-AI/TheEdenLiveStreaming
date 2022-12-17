package com.live.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication()
@ServletComponentScan
@ComponentScan(value = "com.live")
@MapperScan({"com.live","com.live.mapper.xml"})
public class WebApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(WebApplication.class, args);
        System.out.println(System.in.read());
    }

}
