package com.fcs.fcsmall.config.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class FilterConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {  //Fixme 제한해야함
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));  // 모든 도메인에서의 접근을 허용하거나, 특정 도메인을 지정
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));  // 허용할 HTTP 메소드
        configuration.setAllowedHeaders(Arrays.asList("*"));  // 허용할 헤더
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);  // 브라우저가 사전 요청의 결과를 캐싱하는 시간 (초 단위)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // 모든 경로에 대한 CORS 설정 적용
        return source;
    }

}
