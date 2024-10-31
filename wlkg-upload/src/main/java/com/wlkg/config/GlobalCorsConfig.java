package com.wlkg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * CORS解决跨域问题
 *  规范化的跨域请求解决方案，安全可靠。
 *   优势：
 *   - 在服务端进行控制是否允许跨域，可自定义规则
 *   - 支持各种请求方式
 *   缺点：
 *   - 会产生额外的请求
 * 我们这里会采用cors的跨域方案。
 * 浏览器会将ajax请求分为两类，其处理方案略有差异：简单请求、特殊请求。
 */
@Configuration
public class GlobalCorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        //1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //1) 允许的域,不要写*，否则cookie就无法使用了
        config.addAllowedOrigin("http://manage.wlkg.com");
        //2) 是否发送Cookie信息
        config.setAllowCredentials(false);
        //3) 允许的请求方式
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("POST");
        config.addAllowedHeader("*");

        //2.添加映射路径，我们拦截一切请求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        //3.返回新的CorsFilter.
        return new CorsFilter(configSource);
    }
}
