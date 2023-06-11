package icu.xiaohu.diet_recommend.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaohu
 * @date 2023/06/11/ 18:31
 * @description
 */
@AllArgsConstructor
@Getter
public enum MessageStatus {
    /**
     *
     */
    NOT_READ(0),
    /**
     *
     */
    READ(1);

    private Integer status;
}
