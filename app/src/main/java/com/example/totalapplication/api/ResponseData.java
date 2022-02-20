package com.example.totalapplication.api;

import com.example.totalapplication.api.response.IResponse;

public class ResponseData<T> implements IResponse<T> {
    private String code;
    private String msg;
    private T data;

    public String getCode() {
        return code;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    public void setCode(String code) {
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
