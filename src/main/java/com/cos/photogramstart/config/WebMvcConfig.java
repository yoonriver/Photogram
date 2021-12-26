package com.cos.photogramstart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer { // web 설정 파일

    @Value("${file.path}")
    private String uploadFolder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);


        registry.addResourceHandler("/upload/**") // jsp페이지에서 /upload/** 주소 패턴이 나오면 발동
                .addResourceLocations("file:///"+uploadFolder)
                .setCachePeriod(60*10*6) // 1시간동안 캐시  유지
                .resourceChain(true) // 캐시 사용 유무
                .addResolver(new PathResourceResolver()); // ResourceResolver 요청에 해당하는 리소스를 찾는 전략 Spring Boot를 사용한다면 기본적으로 제공하기때문에 직접 설정할 일이 없음.
    }
}
