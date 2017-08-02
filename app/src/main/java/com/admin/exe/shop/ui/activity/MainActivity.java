package com.admin.exe.shop.ui.activity;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.admin.exe.shop.Constant;
import com.admin.exe.shop.R;
import com.admin.exe.shop.base.SCBaseActivity;
import com.admin.exe.shop.base.SCBaseFragment;
import com.admin.exe.shop.ui.fragment.HomeFragment;
import com.admin.exe.shop.ui.fragment.MineFragment;
import com.admin.exe.shop.ui.fragment.SearchFragment;
import com.example.library.control.ActivityControl;
import com.example.library.utils.SharedPrefrencesUtil;

public class MainActivity extends SCBaseActivity implements RadioGroup.OnCheckedChangeListener {
    private FrameLayout mFragment;
    private RadioGroup mRgMainActivity;
    public  RadioButton mRbHome, mRbSearch, mRbMine;
    private RadioButton[] rbs;
    private Drawable[] drs;
    private HomeFragment mHomeFragment = new HomeFragment();
    private MineFragment mMineFragment = new MineFragment();
    private SearchFragment mSearchFragment = new SearchFragment();
    private SCBaseFragment[] mAll = {mHomeFragment, mSearchFragment, mMineFragment};
    private SCBaseFragment mShowFragment;
    private long mTimeBackPress1;
    private boolean mExit;


    @Override
    public int initRootLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {
        mFragment = (FrameLayout) findViewById(R.id.fragment);
        mRgMainActivity = (RadioGroup) findViewById(R.id.rg_mainActivity);
        mRbHome = (RadioButton) findViewById(R.id.rb_home);
        mRbSearch = (RadioButton) findViewById(R.id.rb_search);
        mRbMine = (RadioButton) findViewById(R.id.rb_mine);
        rbs = new RadioButton[]{mRbHome, mRbSearch, mRbMine};
        mRgMainActivity.setOnCheckedChangeListener(this);
        addFrag(R.id.fragment, mHomeFragment);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Constant.isLogin = SharedPrefrencesUtil.getData(mActivitySelf, "shop", "isLogin", false);
    }

    @Override
    public void initDatas() {
        for (RadioButton rb : rbs) {
            drs = rb.getCompoundDrawables();
            Rect r = new Rect(0, 0, drs[1].getMinimumWidth() / 3, drs[1].getMinimumHeight() / 3);
            drs[1].setBounds(r);
            rb.setCompoundDrawables(null, drs[1], null, null);
        }
    }

    @Override
    public void initOthers() {

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        changeTextColor();
        Constant.isLogin = SharedPrefrencesUtil.getData(mActivitySelf, "shop", "isLogin", false);
        switch (checkedId) {
            case R.id.rb_home:
                mShowFragment = mHomeFragment;
                break;
            case R.id.rb_search:
                mShowFragment = mSearchFragment;
                break;

            case R.id.rb_mine:
                if (!Constant.isLogin) {
                    Bundle bundle = new Bundle();
                    bundle.putString("source", "mine");
                    goToActivity(CommonLoginActivity.class, "from", bundle);
                    mRbHome.setChecked(true);
                    return;
                } else {
                    mShowFragment = mMineFragment;
                    break;
                }
        }
        changeFragHS(mShowFragment);
    }


    //移除，添加
    public void changeFragHS(SCBaseFragment showFragment) {
        if (!showFragment.isAdded()) {
            addFrag(R.id.fragment, showFragment);
        }
        for (SCBaseFragment fragment : mAll) {
            if (fragment != showFragment) {
                removeFrag(fragment);
            }
        }
        showFrag(showFragment);
    }

    //改变字体颜色
    private void changeTextColor() {
        for (int i = 0; i < rbs.length; i++) {
            if (rbs[i].isChecked()) {
                rbs[i].setTextColor(getResources().getColor(R.color.app_style));
            } else {
                rbs[i].setTextColor(getResources().getColor(R.color.tv_gray));
            }
        }
    }

    //3秒内点击两次退出
    @Override
    public void onBackPressed() {
        long timeNow = System.currentTimeMillis();
        if (timeNow - mTimeBackPress1 <= 3000) {
            mExit = true;
        } else {
            Toast.makeText(mActivitySelf, "再次点击退出", Toast.LENGTH_SHORT).show();
        }
        mTimeBackPress1 = timeNow;
        if (mExit) {
            ActivityControl.killAll();
        }
    }
}
