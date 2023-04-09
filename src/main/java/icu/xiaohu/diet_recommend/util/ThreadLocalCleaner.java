package icu.xiaohu.diet_recommend.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author xiaohu
 * @date 2023/04/09/ 21:06
 * @description
 */
//@ControllerAdvice
public class ThreadLocalCleaner {

//    @ModelAttribute
    public void cleanUser() {
        UserHolder.remove();
    }
}
