package com.admin.exe.shop.ui.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Service;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.admin.exe.shop.Constant;
import com.admin.exe.shop.R;
import com.admin.exe.shop.base.SCBaseFragment;
import com.admin.exe.shop.ui.activity.ShopListActivity;
import com.admin.exe.shop.ui.adapter.ItemLvSearchHistoryAdapter;
import com.admin.exe.shop.ui.views.WordWrapView;
import com.admin.exe.shop.utils.DensityUtil;
import com.example.library.utils.AnimatorString;
import com.example.library.utils.EdtUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends SCBaseFragment implements SensorEventListener {
    private LinearLayout mRlTitle;
    private EditText mEditSearch;
    private LinearLayout mLlSearch;
    private TextView mTextView;
    private WordWrapView mWordwrap;
    private ListView mLvHistory;
    private ItemLvSearchHistoryAdapter mItemLvSearchHistoryAdapter;
    private TextView mTvClear;
    private String search;
    private SensorManager mSensorManager;
    private int index = Constant.random.nextInt(100) + 5;
    private boolean canY = true;
    String[] strs = Constant.recommend;

    @Override
    public int initRootLayout() {
        return R.layout.fragment_search;
    }

    @Override
    public void initViews() {
        mRlTitle = (LinearLayout) findViewById(R.id.rl_title);
        mEditSearch = (EditText) findViewById(R.id.edit_search);
        mLlSearch = (LinearLayout) findViewById(R.id.ll_search);
        mTextView = (TextView) findViewById(R.id.textView);
        mWordwrap = (WordWrapView) findViewById(R.id.wordwrap);
        mLvHistory = (ListView) findViewById(R.id.lv_history);
        mItemLvSearchHistoryAdapter = new ItemLvSearchHistoryAdapter(mActivitySelf, Constant.history);
        mLvHistory.setAdapter(mItemLvSearchHistoryAdapter);
        mTvClear = (TextView) findViewById(R.id.tv_clear);
        mSensorManager = (SensorManager) mActivitySelf.getSystemService(Context.SENSOR_SERVICE);
        Sensor defaultSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, defaultSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onResume() {
        super.onResume();
        mItemLvSearchHistoryAdapter.setEntities(Constant.history);
        getRecommend(strs);
        if (Constant.history.size() == 0) {
            mTvClear.setVisibility(View.GONE);
        } else {
            mTvClear.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void initDatas() {
        //历史搜索点击监听
        mItemLvSearchHistoryAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Bundle bundle = new Bundle();
                bundle.putString("search", Constant.history.get(position));
                goToActivity(ShopListActivity.class, "bundle", bundle);
            }
        });
        //搜索按钮点击监听
        mLlSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = EdtUtil.getEdtText(mEditSearch);
                if (!Constant.history.contains(search)) {
                    Constant.history.add(search);
                } else {
                    Constant.history.remove(search);
                    Constant.history.add(0, search);
                }

                Bundle bundle = new Bundle();
                bundle.putString("search", search);
                goToActivity(ShopListActivity.class, "bundle", bundle);
            }
        });
        //清除按钮点击监听
        mTvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.history.clear();
                mItemLvSearchHistoryAdapter.setEntities(Constant.history);
            }
        });
    }

    //获取推荐列表  +  点击监听
    public void getRecommend(String[] strs) {
        mWordwrap.removeAllViews();
        for (int i = 0; i < strs.length; i++) {
            String next = strs[i];
            TextView textView = new TextView(mActivitySelf);
            textView.setText(next);
            textView.setTag(next);
            textView.setTextSize(12);
            textView.setBackgroundResource(R.drawable.tv_shape);
            mWordwrap.addView(textView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    search = (String) v.getTag();
                    if (!Constant.history.contains(search)) {
                        Constant.history.add(search);
                    } else {
                        Constant.history.remove(search);
                        Constant.history.add(0, search);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("search", search);
                    goToActivity(ShopListActivity.class, "bundle", bundle);
                }
            });
        }


    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        if (canY && (values[0] >= 20 || values[1] >= 20 || values[2] >= 20)) {
            Vibrator vibrator = (Vibrator) mActivitySelf.getSystemService(Service.VIBRATOR_SERVICE);
            vibrator.vibrate(1000);
            canY = false;
            Log.e("xx", index % 3 + ",,,," + index);

            switch (index % 3) {
                case 0:
                    strs = Constant.recommend;
                    break;
                case 1:
                    strs = Constant.recommend1;
                    break;
                case 2:
                    strs = Constant.recommend2;
                    break;
            }
            index++;
            getRecommend(strs);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mWordwrap, AnimatorString.translationX, DensityUtil.getScreenWidth(mActivitySelf), 0);
            BounceInterpolator b=new BounceInterpolator();
            objectAnimator.setInterpolator(b);
            objectAnimator.setDuration(1000);
            objectAnimator.start();
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    canY = true;
                }
            });
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
