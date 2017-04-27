package com.e7code.common.api.bean;

import com.e7code.common.api.constant.ErrorMessages;

import java.io.Serializable;

/***
 * 通用返回结果
 * @author ssr
 *
 * @param <T> 结果数据类型
 */
public class Result<T> implements Serializable {
	private static final long serialVersionUID = 737534186172728433L;

	public static final int SUCCESS = 0;
	public static final int FAIL = 1;

	private Integer code;	//结果代码
	private String msg;		//结果消息
	private T data;			//结果数据
	
	public Result(){}
	
	public Result(Integer code){
		this.code = code;
	}
	
	public Result(Integer code, String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public Result(Integer code, String msg, T data){
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public boolean isSuccess(){
		if(this.code == 0){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isFail(){
		return !isSuccess();
	}

	/***
	 * 创建成功结果
	 * @return
	 */
	public static <T> Result<T> createSuccessResult(){
		return new Result<T>(Result.SUCCESS, null, null);
	}
	
	/***
	 * 创建成功结果
	 * @param t 结果数据
	 * @return
	 */
	public static <T> Result<T> createSuccessResult(T t){
		return new Result<T>(Result.SUCCESS, null, t);
	}
	
	/***
	 * 创建失败结果
	 * @param msg 错误消息
	 * @return
	 */
	public static <T> Result<T> createFailResult(String msg){
		return new Result<T>(Result.FAIL, msg, null);
	}

	/***
	 * 创建失败结果
	 * @param code 错误代码
	 * @return
	 */
	public static <T> Result<T> createFailResult(Integer code){
		return new Result<T>(code, ErrorMessages.getMsg(code), null);
	}

	/***
	 * 创建失败结果
	 * @param code 错误代码
	 * @param msg 错误消息
	 * @return
	 */
	public static <T> Result<T> createFailResult(Integer code, String msg){
		return new Result<T>(code, msg, null);
	}
	
	/***
	 * 创建失败结果
	 * @param code 错误代码
	 * @param msg  错误消息
	 * @param data 结果数据
	 * @return
	 */
	public static <T> Result<T> createFailResult(Integer code, String msg, T data){
		return new Result<T>(code, msg, data);
	}

	@Override
	public String toString() {
		return "Result [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}

	//------get set ------------------
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
