package com.live.webapi;

/**
 * 枚举了一些常用API操作码
 * Created by zhanglei on 2019/4/19.
 */
public enum  ResultCode implements IErrorCode {
    SUCCESS(0, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(506, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    TYPE_ERROR(501,"类型错误"),
    START_PUBLISH(1,"开播成功"),

    UNPUBLISH_TEMP(503,"暂时停播状态"),
    PUBLISH_FAIL(502,"没有创建直播间信息，请先编辑直播间信息!");
    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
