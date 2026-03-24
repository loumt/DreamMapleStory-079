package cn.ms.dm.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultEnum {
    SUCCESS(10000, "Success"),
    FAILURE(20000, "Failure"),



    LOGIN_NO_ACCOUNT(1000001,"未找到账号"),
    LOGIN_PWD_ERROR(1000002,"登录密码错误"),
    LOGIN_NO_CHANNEL_SERVER(1000003,"没有频道服务"),

    ;

    private final int code;
    private final String msg;
}
