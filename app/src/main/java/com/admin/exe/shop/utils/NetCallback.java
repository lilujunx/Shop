package com.admin.exe.shop.utils;


public abstract class NetCallback<T> {
    public abstract void onSuccess(T t);
    public abstract void onError(Throwable throwable);
    public abstract void onFinished();
}
