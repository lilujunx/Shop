package com.admin.exe.shop.utils;

import android.content.Context;
import android.widget.AbsListView;

import com.admin.exe.shop.base.SCBaseApp;
import com.bumptech.glide.Glide;
import com.example.library.base.BaseApp;



/**
 * Created by Administrator on 0025/10/25.
 */

public class IsLoadImage implements AbsListView.OnScrollListener{

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState){
            case SCROLL_STATE_FLING:
                Glide.with(SCBaseApp.mContextGlobal).pauseRequests();
                break;
            case SCROLL_STATE_IDLE:
                Glide.with(SCBaseApp.mContextGlobal).resumeRequests();
                break;
            case SCROLL_STATE_TOUCH_SCROLL:
                Glide.with(SCBaseApp.mContextGlobal).resumeRequests();
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}

