package com.admin.exe.shop.base;

import android.content.Context;

import com.admin.exe.shop.R;
import com.example.library.base.BaseApp;
import com.example.library.base.TitleBarConfig;

import org.xutils.x;

import cn.sharesdk.framework.ShareSDK;
import cn.smssdk.SMSSDK;


public class SCBaseApp extends BaseApp {

    public static Context mContextGlobal;
    @Override
    public void initOthers() {
        mContextGlobal=this;
        initXutils();
        SMSSDK.initSDK(this, "1e0792bf66966", "42c37e0d05dc78b8601db5d431f6ad3e");
        ShareSDK.initSDK(this,"1e942f27cba12");
    }

    @Override
    public void initTitleBar() {
        TitleBarConfig.titleBarResID= R.layout.titlebar;
        TitleBarConfig.isUseTitleBar=true;
    }

    @Override
    public boolean isDebugMode() {
        return true;
    }

    private void initXutils() {

        x.Ext.init(this);
        x.Ext.setDebug(isDebugMode());
    }
}
