package com.kun.enums;

/**
 * Desc:
 * User:Administrator
 * Date:2022-11-18 20:27
 */
public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功！！！"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作！！！"),
    NO_OPERATOR_AUTH(403,"无权限操作！！！"),
    SYSTEM_ERROR(500,"出现错误！！！"),
    USERNAME_EXIST(501,"用户名已存在！！！"),
    PHONE_NUMBER_EXIST(502,"手机号已存在！！！"), EMAIL_EXIST(503, "邮箱已存在！！！"),
    REQUIRE_USERNAME(504, "必需填写用户名！！！"),
    LOGIN_ERROR(505,"用户名或密码错误！！！"),
    CONTENT_NOT_NULL(506,"内容不能为空！！！"),
    CONTENT_SAVE_FAIL(507,"添加评论失败！！！"),
    USER_NO_FIND(508,"查不到用户信息！！！"),
    FILE_TYPE_ERROR(509,"图片类型错误！！！"),
    UPDATE_FAIL(510,"更新失败！！！"),
    USERNAME_NOT_NULL(511,"用户名不能为空！！！"),
    PASSWORD_NOT_NULL(512,"用户密码不能为空！！！"),
    NICKNAME_NOT_NULL(513,"用户昵称不能为空！！！"),
    EMAIL_NOT_NULL(514,"邮箱不能为空！！！"),
    NICKNAME_EXIST(515,"昵称已存在！！！"),
    REGISTER_FAIL(516,"注册失败！！！"),
    UPDATE_VIEW_COUNT_ERROR(517, "更新访问次数失败！！！");

    final int code;
    final String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
