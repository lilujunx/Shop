package com.admin.exe.shop.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by admin on 2017/4/5.
 */

public class ToastUtils {
    public static void getToastOnFailure(Context context){
        Toast.makeText(context, "请检查您的网络，稍后再试", Toast.LENGTH_SHORT).show();

    }
}
