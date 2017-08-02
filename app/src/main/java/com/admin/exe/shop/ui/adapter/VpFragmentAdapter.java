package com.admin.exe.shop.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.library.base.BaseFragment;

/**
 * Created by Administrator on 2017/5/22.
 */

public class VpFragmentAdapter extends FragmentPagerAdapter{

    private BaseFragment[] mEntitys;
    private Context mContext;

    public VpFragmentAdapter(FragmentManager fm, BaseFragment[] entitys, Context context) {
        super(fm);
        mEntitys = entitys;
        mContext = context;
    }

    public VpFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mEntitys[position];
    }

    @Override
    public int getCount() {
        return mEntitys.length;
    }

    
}
