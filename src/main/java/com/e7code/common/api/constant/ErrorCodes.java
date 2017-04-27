package com.e7code.common.api.constant;

/**
 * Created by ssr on 2017/3/21.
 */
public class ErrorCodes {
    /** 参数错误*/
    public static final int PARAM_INVALID = 100;


    /** 系统异常 */
    public static final int SYSTEM_EXCEPTION = 200;
    /** 属性不存在 */
    public static final int PROPERTY_NOT_FOUND = 201;
    /** 属性复制错误 */
    public static final int PROPERTY_COPY_ERROR = 202;


    /** 数据不存在或已删除 */
    public static final int DATA_NOT_FOUND = 300;
    /** 数据更新乐观锁失败 */
    public static final int DATA_OPTIMISTIC_LOCKING_FAILURE = 301;
}
