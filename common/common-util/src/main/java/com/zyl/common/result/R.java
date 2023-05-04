package com.zyl.common.result;

import lombok.Data;

/**
 * @author: zyl
 * @date 2023/4/26 18:31
 */
@Data
public class R<T> {

    private Integer code;

    private String message;

    private T data;

    private R() {
    }

    public R(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 操作成功
     */
    public static <T> R<T> ok() {
        return R.ok(null);
    }

    public static <T> R<T> ok(T data) {
        return build(data, ResultCode.SUCCESS);
    }

    /**
     * 操作失败
     */
    public static <T> R<T> fail() {
        return R.fail(null);
    }

    public static <T> R<T> fail(T data) {
        return build(data, ResultCode.FAIL);
    }

    public static <T> R<T> fail(ResultCode resultCode) {
        return build(ResultCode.FAIL);
    }

    public static <T> R<T> fail(T data, ResultCode resultCode) {
        return build(data, resultCode);
    }

    public R<T> message(String msg) {
        this.setMessage(msg);
        return this;
    }

    public R<T> code(Integer code) {
        this.setCode(code);
        return this;
    }

    public static <T> R<T> build(ResultCode resultCode) {
        R<T> r = new R<T>();
        r.setCode(resultCode.getCode());
        r.setMessage(resultCode.getMessage());
        return r;
    }

    public static <T> R<T> build(T data) {
        R<T> r = new R<T>();
        if (data != null)
            r.setData(data);
        return r;
    }

    public static <T> R<T> build(T body, Integer code, String message) {
        R<T> r = build(body);
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    public static <T> R<T> build(T body, ResultCode resultCode) {
        R<T> r = build(body);
        r.setCode(resultCode.getCode());
        r.setMessage(resultCode.getMessage());
        return r;
    }

}
