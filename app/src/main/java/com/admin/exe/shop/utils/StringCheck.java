package com.admin.exe.shop.utils;

/**
 * Created by Administrator on 2016/10/9.
 */

public class StringCheck {
    public static boolean check(String str){
        String pa="\\w{6,18}";
        return str.matches(pa);
    }
    //^1[34578]\d{9}$
    public static boolean checkPhone(String str){
        String pa="^1[34578]\\d{9}$";
        return str.matches(pa);
    }

    public static boolean checkIdNum(String str){
        String pa="/^[1-9][0-9]{5}(19[0-9]{2}|200[0-9]|2010)(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])[0-9]{3}[0-9xX]$/";
        return  str.matches(pa);
    }
}
