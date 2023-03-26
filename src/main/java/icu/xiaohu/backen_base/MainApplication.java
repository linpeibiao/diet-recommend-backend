package icu.xiaohu.backen_base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xiaohu
 * @date 2023/03/23/ 22:28
 * @description
 */

@SpringBootApplication
@MapperScan("icu.xiaohu.backen_base.mapper")
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}

