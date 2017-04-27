package com.e7code.common.api.exception;

/**
 * Created by ssr on 2017/3/21.
 */
public class ValidateException extends RuntimeException {
    private static final long serialVersionUID = -7319532573008583652L;

    //具体异常码
    protected int code;
    //异常信息
    protected String msg;

    public ValidateException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
        this.msg = String.format(msgFormat, args);
    }

    public ValidateException() {
        super();
    }

    public String getMessage() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    /**
     * 实例化异常
     * @param msgFormat
     * @param args
     * @return
     */
    public ValidateException newInstance(String msgFormat, Object... args) {
        return new ValidateException(this.code, msgFormat, args);
    }

    public ValidateException(String message, Throwable cause) {
        super(message, cause);
        this.msg = message;
    }

    public ValidateException(Throwable cause) {
        super(cause);
        this.msg = this.getMessage();
    }

    public ValidateException(String message) {
        super(message);
        this.msg = message;
    }

    public ValidateException(Integer code) {
        this.code = code;
        //TODO:get message;
    }

}
