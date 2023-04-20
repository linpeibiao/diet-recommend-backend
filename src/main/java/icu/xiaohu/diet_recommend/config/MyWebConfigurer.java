package icu.xiaohu.diet_recommend.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author xiaohu
 * @date 2023/04/03/ 19:39
 * @description
 */
@SpringBootConfiguration
public class MyWebConfigurer implements WebMvcConfigurer {
    /**
     * 配置跨越
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                //允许跨域的 cookie
                .allowCredentials(true)
                // 前端更改端口时，相应的后端需要允许新更改的端口访问，否则会出现跨域问题
                 .allowedOrigins("http://43.139.60.130:8086/", "http://localhost:8088", "http://localhost:8086")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowedHeaders("*");
    }
}
