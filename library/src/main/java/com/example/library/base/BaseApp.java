package com.example.library.base;

import android.app.Application;
import android.content.Context;



/**
 * Created by Administrator on 2016/10/21.
 */
public abstract class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initTitleBar();
        initOthers();
    }
    
    public abstract void initOthers();
    
    public abstract void initTitleBar();
    
    public  abstract  boolean isDebugMode();
    
    



}
