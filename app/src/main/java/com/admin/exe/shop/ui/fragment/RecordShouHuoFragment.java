package com.admin.exe.shop.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.admin.exe.shop.Constant;
import com.admin.exe.shop.R;
import com.admin.exe.shop.base.SCBaseFragment;
import com.admin.exe.shop.entity.RecordEntity;
import com.admin.exe.shop.ui.activity.RecordDetailActivity;
import com.admin.exe.shop.ui.adapter.ItemLvRecordAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordShouHuoFragment extends SCBaseFragment{
    private ListView mLvContent;
    private ItemLvRecordAdapter mItemLvRecordAdapter;
    private LinearLayout mLlNone;

    @Override
    public int initRootLayout() {
        return R.layout.fragment_record_all;
    }

    @Override
    public void initViews() {
        mLvContent = (ListView) findViewById(R.id.lv_content);
        mLlNone = (LinearLayout) findViewById(R.id.ll_none);
        if (Constant.mDaiShow.size() == 0) {
            mLlNone.setVisibility(View.VISIBLE);
            mLvContent.setVisibility(View.GONE);
        } else {
            mLlNone.setVisibility(View.GONE);
            mLvContent.setVisibility(View.VISIBLE);
            mItemLvRecordAdapter = new ItemLvRecordAdapter(mActivitySelf, Constant.mDaiShow);
            mLvContent.setAdapter(mItemLvRecordAdapter);
            mItemLvRecordAdapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RecordEntity.RecordsBean entity = (RecordEntity.RecordsBean) v.getTag();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("entity", entity);
                    bundle.putInt("pos",2);
                    goToActivity(RecordDetailActivity.class, "bundle", bundle);
                }
            });
        }

    }

    @Override
    public void initDatas() {

    }
}