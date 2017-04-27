package com.endos.mmall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * service层通用response返回类型封装
 * Created by Endos on 2017/04/26.
 */
// 保证序列化json的时候,如果是null的对象,key也会消失
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServiceResponse<T> implements Serializable {
    private int status;
    private String msg;
    private T data;

    private ServiceResponse(int status) {
        this.status = status;
    }

    private ServiceResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServiceResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ServiceResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    @JsonIgnore// 使之不在json序列化结果中
    public boolean isSuccess() {
        return this.status == 0;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public static <T> ServiceResponse<T> createBySuccess() {
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServiceResponse<T> createBySuccess(T data) {
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> ServiceResponse<T> createBySuccess(String msg, T data) {
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    /**
     * 当需要msg当做data传入的时候，就用createBySuccess(T data)
     * 当只需要msg的时候，就用createBySuccessMessage(String msg)
     * @param msg
     * @param <T>
     * @return
     * @see public static <T> ServiceResponse<T> createBySuccess(T data)
     */
    public static <T> ServiceResponse<T> createBySuccessMessage(String msg) {
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> ServiceResponse<T> createByError() {
        return new ServiceResponse<T>(ResponseCode.ERROR.getCode());
    }

    public static <T> ServiceResponse<T> createByErrorMessage(String errorMsg) {
        return new ServiceResponse<T>(ResponseCode.ERROR.getCode(), errorMsg);
    }

    public static <T> ServiceResponse<T> createByError(int errorCode, String errorMsg) {
        return new ServiceResponse<T>(errorCode, errorMsg);
    }
}
