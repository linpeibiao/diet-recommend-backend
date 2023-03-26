package icu.xiaohu.backen_base.exception;

import icu.xiaohu.backen_base.model.result.ResultCode;

/**
 * @author xiaohu
 * @date 2023/03/04/ 23:19
 * @description 业务异常类
 */
public class BusinessException extends RuntimeException{
    private final int code;

    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.description = resultCode.getDescription();
    }

    public BusinessException(ResultCode resultCode, String description) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}