package com.t3h.quan_ly_ban_hang.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

@Configuration
public class StaticResourceConfiguration implements WebMvcConfigurer {
    @Value("${folder.storage.file}")
    String FOLDER;
    public static String FOLDER_STATIC ;

    @PostConstruct
    void innit() {
        FOLDER_STATIC = FOLDER;
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/image-storage/**")
                .addResourceLocations("file:/" + FOLDER);
    }
}
