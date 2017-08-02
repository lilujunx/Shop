package com.admin.exe.shop.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.exe.shop.Constant;
import com.admin.exe.shop.R;
import com.admin.exe.shop.base.SCBaseActivity;
import com.admin.exe.shop.entity.RecordEntity;
import com.admin.exe.shop.ui.adapter.VpFragmentAdapter;
import com.admin.exe.shop.ui.fragment.RecordAllFragment;
import com.admin.exe.shop.ui.fragment.RecordEvaFragment;
import com.admin.exe.shop.ui.fragment.RecordFaHuoFragment;
import com.admin.exe.shop.ui.fragment.RecordShouHuoFragment;
import com.admin.exe.shop.utils.LogerUtils;
import com.admin.exe.shop.utils.NetCallback;
import com.admin.exe.shop.utils.NetForJson;
import com.admin.exe.shop.utils.ToastUtils;
import com.example.library.base.BaseFragment;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class RecordActivity extends SCBaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager mVpFragment;
    private VpFragmentAdapter mVpFragmentAdapter;
    private RecordAllFragment mRecordAllFragment = new RecordAllFragment();
    private RecordEvaFragment mRecordEvaFragment = new RecordEvaFragment();
    private RecordFaHuoFragment mRecordFaHuoFragment = new RecordFaHuoFragment();
    private RecordShouHuoFragment mRecordShouHuoFragment = new RecordShouHuoFragment();
    private BaseFragment[] mBaseFragments = {mRecordAllFragment, mRecordFaHuoFragment, mRecordShouHuoFragment, mRecordEvaFragment};
    private TextView mTv1;
    private TextView mTv2;
    private TextView mTv3;
    private TextView mTv4;
    private TextView[] tvs;
    private View mLine1;
    private View mLine2;
    private View mLine3;
    private View mLine4;
    private View[] lines;
    public int mPos;

    @Override
    public int initRootLayout() {
        return R.layout.activity_record;
    }

    @Override
    public void initViews() {

        mTv1 = (TextView) findViewById(R.id.tv1);
        mTv2 = (TextView) findViewById(R.id.tv2);
        mTv3 = (TextView) findViewById(R.id.tv3);
        mTv4 = (TextView) findViewById(R.id.tv4);
        mLine1 = (View) findViewById(R.id.line1);
        mLine2 = (View) findViewById(R.id.line2);
        mLine3 = (View) findViewById(R.id.line3);
        mLine4 = (View) findViewById(R.id.line4);
        lines = new View[]{mLine1, mLine2, mLine3, mLine4};
        tvs = new TextView[]{mTv1, mTv2, mTv3, mTv4};
        for (int i = 0; i < tvs.length; i++) {
            tvs[i].setOnClickListener(this);
        }
        mVpFragment = (ViewPager) findViewById(R.id.vp_fragment);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            mPos = bundle.getInt("pos");
        }
    }

    @Override
    public void initDatas() {
        NetForJson netForJson = new NetForJson(Constant.URL_RECORD, new RecordCall());
        netForJson.addParam("stype", "select");
        netForJson.addParam("uid", LogerUtils.getUid(mActivitySelf));
        netForJson.execute();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initDatas();
        int index = intent.getIntExtra("pos", -1);
        if (index != -1) {
            mVpFragment.setCurrentItem(index);
            changeTv(index);
        }
    }

    @Override
    public void initOthers() {
        setTitleCenter("订单相关");
        setTitleLeft("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelf.finish();
                goToActivity(MainActivity.class);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mActivitySelf.finish();
        goToActivity(MainActivity.class);
    }

    private void genData(List<RecordEntity.RecordsBean> records) {
        Constant.mAllRecord.clear();
        Constant.mWeiFa.clear();
        Constant.mDaiShow.clear();
        Constant.mWeiPingJia.clear();
        Constant.mYiPingJia.clear();
        Constant.mShouHuo.clear();
        Iterator<RecordEntity.RecordsBean> iterator = records.iterator();
        while (iterator.hasNext()) {
            RecordEntity.RecordsBean next = iterator.next();
            if (next.getRstatus().equals("-1") && next.getSstatus().equals("-1") && next.getEstatus().equals("-1")) {
                //未发货
                Constant.mWeiFa.add(next);
                next.setMyType(RecordEntity.WEIFAHUO);
            }
            if (next.getRstatus().equals("-1") && next.getSstatus().equals("0") && next.getEstatus().equals("-1")) {
                //待收货
                Constant.mDaiShow.add(next);
                next.setMyType(RecordEntity.DAISHOUHUO);
            }
            if (next.getRstatus().equals("0") && next.getSstatus().equals("0") && next.getEstatus().equals("-1")) {
                //未评价
                Constant.mWeiPingJia.add(next);
                next.setMyType(RecordEntity.WEIPINGJIA);
            }
            if (next.getRstatus().equals("0") && next.getSstatus().equals("0") && next.getEstatus().equals("0")) {
                //已收货
                Constant.mShouHuo.add(next);
                next.setMyType(RecordEntity.YISHOUHUO);
            }
        }
        Collections.sort(Constant.mWeiFa, new Comparator<RecordEntity.RecordsBean>() {
            @Override
            public int compare(RecordEntity.RecordsBean lhs, RecordEntity.RecordsBean rhs) {
                return -lhs.getLtime().compareTo(rhs.getLtime());
            }
        });
        Constant.mAllRecord.addAll(Constant.mWeiFa);
        Constant.mAllRecord.addAll(Constant.mDaiShow);
        Constant.mAllRecord.addAll(Constant.mWeiPingJia);
        Constant.mAllRecord.addAll(Constant.mShouHuo);

    }

    @Override
    public void onClick(View v) {
        int index = 0;
        switch (v.getId()) {
            case R.id.tv1:
                index = 0;
                Constant.TAG="全部";
                break;
            case R.id.tv2:
                index = 1;
                Constant.TAG="x";
                break;
            case R.id.tv3:
                index = 2;
                Constant.TAG="x";
                break;
            case R.id.tv4:
                index = 3;
                Constant.TAG="x";
                break;
        }
        changeTv(index);
        mVpFragment.setCurrentItem(index);
    }

    private void changeTv(int index) {
        for (int i = 0; i < tvs.length; i++) {
            tvs[i].setTextColor(getResources().getColor(R.color.tv_gray));
            lines[i].setBackgroundColor(getResources().getColor(R.color.gray_line));
        }
        tvs[index].setTextColor(getResources().getColor(R.color.app_style));
        lines[index].setBackgroundColor(getResources().getColor(R.color.app_style));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        changeTv(position);
        if(position==0){
            Constant.TAG="全部";
        }else{
            Constant.TAG="x";
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    class RecordCall extends NetCallback<RecordEntity> {

        @Override
        public void onSuccess(RecordEntity recordEntity) {
            List<RecordEntity.RecordsBean> records = recordEntity.getRecords();
            genData(records);
            mVpFragmentAdapter = new VpFragmentAdapter(mFragmentManager, mBaseFragments, mActivitySelf);
            mVpFragment.setAdapter(mVpFragmentAdapter);
            mVpFragment.addOnPageChangeListener(RecordActivity.this);
            mVpFragment.setOffscreenPageLimit(4);
            mVpFragment.setCurrentItem(mPos);
            changeTv(mPos);
        }

        @Override
        public void onError(Throwable throwable) {
            ToastUtils.getToastOnFailure(mActivitySelf);
            Log.e("xx", throwable.toString());
        }

        @Override
        public void onFinished() {

        }
    }

    @Override
    public boolean isUseTitleBar() {
        return true;
    }
}
