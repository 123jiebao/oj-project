package com.lzy.ojcommon.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 *
 * @param <T>

 */
@Data
public class BaseResponse<T> implements Serializable {

    // 返回码
    private int code;

    // 返回数据
    private T data;

    // 返回信息
    private String message;

    // 构造函数，传入返回码、返回数据和返回信息
    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    // 构造函数，传入返回码和返回数据，返回信息为空字符串
    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    // 构造函数，传入错误码，返回码为错误码的code，返回数据为null，返回信息为错误码的message
    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}
