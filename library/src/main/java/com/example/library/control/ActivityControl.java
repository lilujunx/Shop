package com.example.library.control;


import android.app.Activity;

import com.example.library.base.BaseActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/10/21.
 */
public class ActivityControl {
    private static List<Activity> mActivities=new ArrayList<>();
    
    public static void add(Activity baseActivity){
        mActivities.add(baseActivity);
        
    }
    
    public static void remove(Activity baseActivity){
        mActivities.remove(baseActivity);
        
    }
    
    public static <T> T getActivity(Class<T> activity){
        for (Activity baseActivity : mActivities) {
            if (baseActivity.getClass()==activity) {
                return (T)baseActivity;
            }
        }
        return null;
    }
    
    public static void killAll(){
        Iterator<Activity> iterator=mActivities.iterator();
        while (iterator.hasNext()){
            iterator.next().finish();
            
        }
    }
}
