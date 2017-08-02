package com.admin.exe.shop.utils;

import android.widget.EditText;

import com.example.library.utils.EdtUtil;

/**
 * Created by Administrator on 2017/4/1.
 */

public class HealthUtils {
/*
        各种计算公式，Edit的处理
    */
    //做“0”开头的情况处理，未输入的情况
    public static void doSome(EditText editText) {
        String oldStr = EdtUtil.getEdtText(editText);
        if (oldStr.startsWith("0")) {
            String newStr = oldStr.substring(1, oldStr.length());
            editText.setText(newStr);
            if (editText.hasFocus()) {
                editText.setSelection(newStr.length());
            }
        }
    }



    public static String getBMI(float height,float weight){
        return String.format("%.1f", weight / ((height / 100) * (height / 100)));
    }

    public static String getArea(float height,float weight){
        return String.format("%.1f", 0.0061f * height + 0.0128f * weight - 0.1529f);
    }

    public static String getPop(float na,float k,float glucose,float n){
        //血浆渗透压=（钠+钾）×2+葡萄糖/18+尿素氮/2.8
        return String.format("%.3f", ((na+ k)*2 + glucose /18 + n/2.8));
    }

    public static   String  getMdrd(float age,float weight,float blood){
        //肌酐清除率=[(140-年龄)×体重(千克)]/[0.818×血清肌酐(umol/L)]	女性的结果×0.85
        return String.format("%.3f",(140-age)*weight/(0.818f*blood));
    }

    public static   String  getIletin(float blood,float iletin){
        //胰岛素抵抗指数		胰岛素抵抗指数=(血糖×血清胰岛素)/22.5
        return String.format("%.3f", (blood*iletin/22.5f));
    }
}
