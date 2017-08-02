package com.example.library.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.library.R;
import com.example.library.control.ActivityControl;


/**
 * Created by Administrator on 2016/10/21.
 * <p/>
 * <p/>
 * <p/>
 * <p/>
 * <p/>
 */
public abstract class BaseActivity extends FragmentActivity {
    public BaseActivity mActivitySelf;
    public LayoutInflater mLayoutInflater;
    public FragmentManager mFragmentManager;
    public TextView  mTitleCenter, mTitleRight;
    public ImageView mTitleLeft;
    
    //当前Activity是不是要标题栏
    public boolean isUseTitleBar() {
        return false;
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        ActivityControl.add(this);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mActivitySelf = this;
        mFragmentManager = this.getSupportFragmentManager();
        mLayoutInflater = this.getLayoutInflater();
        
        
        int rootLayout = initRootLayout();
        if (TitleBarConfig.isUseTitleBar && isUseTitleBar()) {
            //需要使用标题栏
            addTitleBar(rootLayout);
        } else {
            setContentView(rootLayout);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        initViews();
        initDatas();
        initOthers();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityControl.remove(this);
    }
    
    private void addTitleBar(int rootLayout) {
        LinearLayout linearLayout = new LinearLayout(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        
        
        View titleView = mLayoutInflater.inflate(TitleBarConfig.titleBarResID, linearLayout, false);
        linearLayout.addView(titleView);
        
        View oldRootLayout = mLayoutInflater.inflate(rootLayout, linearLayout, false);
        linearLayout.addView(oldRootLayout);
        
        
        setContentView(linearLayout);
        mTitleLeft = (ImageView) this.findViewById(R.id.title_left);
        mTitleCenter = (TextView) this.findViewById(R.id.title_center);
        mTitleRight = (TextView) this.findViewById(R.id.title_right);
        if (mTitleLeft != null) {
            mTitleLeft.setVisibility(View.INVISIBLE);
        }
        if (mTitleRight != null) {
            mTitleRight.setVisibility(View.INVISIBLE);
        }

    }

    public void setTitleLeft(String text, View.OnClickListener onClickListener) {
        if (mTitleLeft != null) {
            mTitleLeft.setVisibility(View.VISIBLE);
            if (onClickListener != null) {
                mTitleLeft.setOnClickListener(onClickListener);
            }

        }

    }


    public void setTitleLeft(String text) {
        setTitleLeft(text, null);
    }

    public void setTitleRight(String text, View.OnClickListener onClickListener) {
        if (mTitleRight != null) {
            mTitleRight.setVisibility(View.VISIBLE);
            mTitleRight.setText(text);
            if (onClickListener != null) {
                mTitleRight.setOnClickListener(onClickListener);
            }
        }

    }
    
    public void setTitleRight(String text) {
        setTitleRight(text, null);
    }
    
    
    public void setTitleCenter(String text) {
        if (mTitleCenter != null) {
            mTitleCenter.setText(text);
        }
    }
    
    public abstract int initRootLayout();
    
    public abstract void initViews();
    
    public abstract void initDatas();
    public abstract void initOthers();
    /**
     * 启动Activity
     **/
    public void goToActivity(Class activity) {
        goToActivity(activity, null, null);
    }
    
    public void goToActivity(Class activity, String key, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        
        if (key != null && bundle != null) {
            intent.putExtra(key, bundle);
        }
        
        this.startActivity(intent);
    }
    
    /**启动Activity**/
    
    
    /**
     * 启动Service
     **/
    public void goToService(Class service) {
        goToService(service, null, null);
    }
    
    public void goToService(Class service, String key, Bundle bundle) {
        Intent intent = new Intent(this, service);
        
        if (key != null && bundle != null) {
            intent.putExtra(key, bundle);
        }
        
        this.startService(intent);
    }
    
    /**启动Service**/
    
    
    /**
     * 快捷操作Fragmnet
     **/
    public void addFrag(int desId, BaseFragment fragment) {
        
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(desId, fragment, fragment.mTag);
        transaction.commit();

//        fragmentManager.findFragmentByTag()
    }
    
    public void removeFrag(BaseFragment fragment) {
        
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
        
    }
    
    public void replaceFrag(int desId, BaseFragment fragment) {
        
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(desId, fragment, fragment.mTag);
        transaction.commit();

//        fragmentManager.findFragmentByTag()
    }
    
    public void hideFrag(BaseFragment fragment) {
        
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(fragment);
        transaction.commit();
        
    }
    
    public void showFrag(BaseFragment fragment) {
        
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.show(fragment);
        transaction.commit();
        
    }
    /**快捷操作Fragmnet**/
}
