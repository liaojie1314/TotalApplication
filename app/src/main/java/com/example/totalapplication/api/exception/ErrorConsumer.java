package com.example.totalapplication.api.exception;

import io.reactivex.functions.Consumer;

/**
 * 通用处理异常回调的Consumer
 */
public abstract class ErrorConsumer implements Consumer<Throwable> {

    @Override
    public void accept(Throwable throwable) throws Exception {
        ApiException ex;
        if (throwable instanceof ApiException) {
            ex = (ApiException) throwable;
        } else {
            ex = ApiException.handleException(throwable);
        }
        error(ex);
    }
    protected abstract void error(ApiException e);
}