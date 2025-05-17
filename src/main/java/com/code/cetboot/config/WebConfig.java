package com.code.cetboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : IMG
 * @create : 2025/4/28
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.filePath}")
    private String filePath;

    @Value("${webPath.file}")
    private String fileWebPath;

    @Value("${file.audioPath}")
    private String audioPath;

    @Value("${webPath.audio}")
    private String audioWebPath;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(fileWebPath + "**")
                .addResourceLocations("file:///" + filePath);
        registry.addResourceHandler(audioWebPath + "**")
                .addResourceLocations("file:///" + audioPath);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600);
    }
}
