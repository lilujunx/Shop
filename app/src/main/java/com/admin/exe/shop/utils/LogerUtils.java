package com.admin.exe.shop.utils;

import android.content.Context;

import com.example.library.utils.EdtUtil;
import com.example.library.utils.SharedPrefrencesUtil;

/**
 * Created by Administrator on 2017/5/21.
 */

public class LogerUtils {
    public static String getUid(Context context){
        return  SharedPrefrencesUtil.getData(context, "shop", "uid", "");
    }
}
