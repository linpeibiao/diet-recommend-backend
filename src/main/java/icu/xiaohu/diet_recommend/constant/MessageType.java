package icu.xiaohu.diet_recommend.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaohu
 * @date 2023/06/11/ 16:15
 * @description 消息类型
 */
@Getter
@AllArgsConstructor
public enum MessageType {
    /**
     *
     */
    ADD_CHECK("新增审核"),
    /**
     *
     */
    CHECK_RESULT("审核结果");
    private String type;
}
