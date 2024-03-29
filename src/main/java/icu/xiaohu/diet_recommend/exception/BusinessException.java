package icu.xiaohu.diet_recommend.exception;

import icu.xiaohu.diet_recommend.model.result.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaohu
 * @date 2023/03/04/ 23:19
 * @description 业务异常类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessException extends RuntimeException{
    private int code;

    private String description;

    private ResultCode resultCode;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.description = resultCode.getDescription();
        this.resultCode = resultCode;
    }

    public BusinessException(ResultCode resultCode, String description) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.description = description;
        this.resultCode = resultCode;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}