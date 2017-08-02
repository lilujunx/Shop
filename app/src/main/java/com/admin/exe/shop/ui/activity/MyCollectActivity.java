package com.admin.exe.shop.ui.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.admin.exe.shop.Constant;
import com.admin.exe.shop.R;
import com.admin.exe.shop.base.SCBaseActivity;
import com.admin.exe.shop.entity.CollectEntity;
import com.admin.exe.shop.ui.adapter.ItemLvCollectAdapter;
import com.admin.exe.shop.utils.LogerUtils;
import com.admin.exe.shop.utils.NetCallback;
import com.admin.exe.shop.utils.NetForJson;
import com.admin.exe.shop.utils.ToastUtils;

import java.util.ArrayList;

public class MyCollectActivity extends SCBaseActivity {
    private ListView mLvCollect;
    private LinearLayout mLlNone;
    private TextView mTitleRight;
    private ImageView mTitleLeft;
    private TextView mTitleCenter;
    private String right = "编辑";
    private Button mBtSubmit;
    private ItemLvCollectAdapter mItemLvCollectAdapter;
    private String uid;
    private ArrayList<String> cids = new ArrayList<>();

    @Override
    public int initRootLayout() {
        return R.layout.activity_my_collect;
    }

    @Override
    public void initViews() {
        mLvCollect = (ListView) findViewById(R.id.lv_collect);
        mLlNone = (LinearLayout) findViewById(R.id.ll_none);
        mTitleRight = (TextView) findViewById(R.id.title_right);
        mTitleLeft = (ImageView) findViewById(R.id.title_left);
        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mBtSubmit = (Button) findViewById(R.id.bt_submit);
        uid = LogerUtils.getUid(mActivitySelf);
    }

    @Override
    public void initDatas() {
        doGetCollect();
        mTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelf.finish();
            }
        });
        mTitleRight.setText(right);
        mTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (right.equals("编辑")) {
                    right = "取消";
                    if (mItemLvCollectAdapter != null) {
                        mItemLvCollectAdapter.setShow(true);
                    }
                    mBtSubmit.setVisibility(View.VISIBLE);
                } else {
                    right = "编辑";
                    if (mItemLvCollectAdapter != null) {
                        mItemLvCollectAdapter.setShow(false);
                    }
                    mBtSubmit.setVisibility(View.GONE);
                }
                mTitleRight.setText(right);

            }
        });
    }

    @Override
    public void initOthers() {

    }

    public void doGetCollect() {
        NetForJson netForJson = new NetForJson(Constant.URL_COLLECT, new CollectCallback());
        netForJson.addParam("stype", "select");
        netForJson.addParam("uid", uid);
        netForJson.execute();
    }


    class CollectCallback extends NetCallback<CollectEntity> {

        @Override
        public void onSuccess(CollectEntity collectEntity) {
            if (collectEntity.getCollects().size() == 0) {
                mLlNone.setVisibility(View.VISIBLE);
                mLvCollect.setVisibility(View.GONE);
            } else {
                mLlNone.setVisibility(View.GONE);
                mLvCollect.setVisibility(View.VISIBLE);
                if (mItemLvCollectAdapter == null) {
                    mItemLvCollectAdapter = new ItemLvCollectAdapter(mActivitySelf, collectEntity.getCollects());

                }
                mItemLvCollectAdapter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        String cid = (String) buttonView.getTag();
                        if (isChecked) {
                            cids.add(cid);
                        } else {
                            cids.remove(cid);
                        }
                    }
                });
                mLvCollect.setAdapter(mItemLvCollectAdapter);

            }
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
}
