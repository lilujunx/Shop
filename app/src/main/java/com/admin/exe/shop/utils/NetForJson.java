package com.admin.exe.shop.utils;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/10/18.
 */
public class NetForJson implements Callback.CommonCallback<String> {
    private String mUrl;
    private Type mClass;
    private boolean mIsPost;
    private NetCallback mNetCallback;
    private RequestParams mRequestParams;
    private Gson mGson=new Gson();
    
    
    public NetForJson(String url, NetCallback netCallback,boolean isPost) {
        this.mUrl = url;
        mNetCallback = netCallback;
        mIsPost=isPost;
    
    
        //根据泛型获取Type类型，从而省略传入解析类型参数
        if (netCallback!=null){
            ParameterizedType parameterizedType = (ParameterizedType) netCallback
                    .getClass().getGenericSuperclass();
            mClass = parameterizedType.getActualTypeArguments()[0];
        }
    
        mRequestParams=new RequestParams(mUrl);
    }
    public NetForJson(String url, NetCallback netCallback) {
        this(url,netCallback,false);
    }
    
    //参数操作
    public void addParam(String key,Object value){
        if (mIsPost){
            mRequestParams.addBodyParameter(key,value+"");
        }else {
            mRequestParams.addParameter(key,value);
        }
        
    }
    public void removeParam(String...  keys){
        
        if (keys==null){
            return;
        }
        for (String key:keys){
            mRequestParams.removeParameter(key);
        }
     
    }
    
    public void replaceParam(String key,Object newValue){
        removeParam(key);
        addParam(key,newValue);
    }
    
    //请求头操作
//    public void setHeader().....
    
    public void execute() {
    
        if (mIsPost){
            x.http().post(mRequestParams,this);
        }else {
            x.http().get(mRequestParams,this);
        }
    }
    
    @Override
    public void onSuccess(String result) {
        mNetCallback.onSuccess(mGson.fromJson(result,mClass));
    }
    
    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        ex.printStackTrace();
        mNetCallback.onError(ex);
    }
    
    @Override
    public void onCancelled(CancelledException cex) {
        
    }
    
    @Override
    public void onFinished() {
        mNetCallback.onFinished();
    }
}