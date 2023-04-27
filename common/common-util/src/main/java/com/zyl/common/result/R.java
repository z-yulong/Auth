package com.zyl.common.result;

public class R<T> {

    private Integer code;

    private String message;

    private T data;

    private R(){}

    public R(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    protected static <T> R<T> build(T data) {
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

    public static<T> R<T> ok(){
        return R.ok(null);
    }

    /**
     * 操作成功
     * @param data  baseCategory1List
     * @param <T>
     * @return
     */
    public static<T> R<T> ok(T data){
        return build(data, ResultCode.SUCCESS);
    }

    public static<T> R<T> fail(){
        return R.fail(null);
    }

    /**
     * 操作失败
     * @param data
     * @param <T>
     * @return
     */
    public static<T> R<T> fail(T data){
        return build(data, ResultCode.FAIL);
    }

    public R<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public R<T> code(Integer code){
        this.setCode(code);
        return this;
    }

    /**
     * 获取
     * @return code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 设置
     * @param code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 获取
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取
     * @return data
     */
    public T getData() {
        return data;
    }

    /**
     * 设置
     * @param data
     */
    public void setData(T data) {
        this.data = data;
    }

    public String toString() {
        return "R{code = " + code + ", message = " + message + ", data = " + data + "}";
    }
}
