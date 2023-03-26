package icu.xiaohu.backen_base.model.result;

/**
 * @author xiaohu
 * @date 2022/11/22/ 15:48
 * @description 统一返回状态码
 */
public enum ResultCode {
    SUCCESS(20000, "ok", ""),
    FAIL(40000, "请求失败", ""),
    NULL_ERROR(40001, "请求数据为空", ""),
    PARAMS_ERROR(40002, "请求参数错误", ""),
    NOT_LOGIN(40100, "未登录", ""),
    NO_AUTH(40101, "无权限", ""),
    NOT_FOUND(40004, "未找到资源", ""),
    SYSTEM_ERROR(50000, "系统内部异常", "");

    private final int code;

    /**
     * 状态码信息
     */
    private final String message;

    /**
     * 状态码描述（详情）
     */
    private final String description;

    ResultCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
