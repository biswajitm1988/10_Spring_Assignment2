package com.fsd.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan({"com.fsd.spring.config","com.fsd.spring.model","com.fsd.spring.helper","com.fsd.spring.controller"})
public class AppConfig {
    @Bean
    public InternalResourceViewResolver viewresolver()
    {
        InternalResourceViewResolver vr = new InternalResourceViewResolver();
        vr.setPrefix("/WEB-INF/views/");
        vr.setSuffix(".jsp");
        return vr;
    }

}
