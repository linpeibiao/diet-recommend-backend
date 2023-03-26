package backen_base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author xiaohu
 * @date 2022/07/23/ 15:29
 * @description
 */
@SpringBootTest()
public class MainApplicationTest {
    public static void main(String[] args) {
        SpringApplication.run(icu.xiaohu.backen_base.MainApplication.class, args);
    }
}
