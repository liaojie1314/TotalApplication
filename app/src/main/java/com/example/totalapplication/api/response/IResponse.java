package com.example.totalapplication.api.response;

public interface IResponse<T> {

    T getData();

    String getMsg();

    String getCode();

    boolean isSuccess();

}
