package icu.xiaohu.diet_recommend;

import icu.xiaohu.diet_recommend.service.IUserMealService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author xiaohu
 * @date 2023/04/01/ 12:19
 * @description
 */

@SpringBootTest
//@RunWith(SpringRunner.class)
public class MainApplicationTests {
    @Resource
    private IUserMealService userMealService;

    @Test
    public void getRelateDtoTest(){
        System.out.println(userMealService.getUserMealRelate().size());
    }

    @Test
    public void randomTest(){
        System.out.println(Math.random() * 10);
        System.out.println(Math.random() * 10);
        System.out.println(Math.random() * 10);
        System.out.println(Math.random() * 10);
    }
}
