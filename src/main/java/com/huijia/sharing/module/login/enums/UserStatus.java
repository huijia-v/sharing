package com.huijia.sharing.module.login.enums;

/**
 * @author huijia
 * @date 2024/12/24 10:15
 */
public enum UserStatus {

    OK("0", "正常"),
    DISABLE("1", "停用"),
    DELETED("2", "删除");
    private final String code;
    private final String info;

    UserStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
