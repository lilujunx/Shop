package com.admin.exe.shop.utils;

/**
 * Created by Administrator on 2016/10/24.
 */
public class FormatTime {
    public static final int HOUR=1000*60*60;
    public static final int MINUTE=1000*60;
    public static final int SECOND=1000;
    // 10时30分30秒
    public static String formatLeftTime(long timeInMillions){
        long hour=timeInMillions/HOUR;
        long time1=timeInMillions%HOUR; 
        
        long minute=time1/MINUTE;
        long time2=time1%MINUTE;
        
        long second=time2/SECOND;
        
        return  hour+"时"+minute+"分"+second+"秒";
    }
}
