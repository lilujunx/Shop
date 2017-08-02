package com.admin.exe.shop.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.exe.shop.Constant;
import com.admin.exe.shop.R;
import com.admin.exe.shop.base.SCBaseActivity;
import com.admin.exe.shop.entity.ReceiverEntity;
import com.admin.exe.shop.ui.adapter.ItemLvAddressAdapter;
import com.admin.exe.shop.utils.LogerUtils;
import com.admin.exe.shop.utils.NetCallback;
import com.admin.exe.shop.utils.NetForJson;
import com.admin.exe.shop.utils.ToastUtils;

import java.util.List;

public class AddressActivity extends SCBaseActivity {
    private RelativeLayout mActivityAddress;
    private RelativeLayout mRlTitle;
    private ImageView mTitleLeft;
    private TextView mTitleCenter;
    private ListView mLvAddress;
    private Button mBtSubmit;
    private String uid;
    private ItemLvAddressAdapter mItemLvAddressAdapter;
    private LinearLayout mLlNone;
    private boolean isShow = false;

    @Override
    public int initRootLayout() {
        return R.layout.activity_address;
    }

    @Override
    public void initViews() {
        uid = LogerUtils.getUid(mActivitySelf);
        mActivityAddress = (RelativeLayout) findViewById(R.id.activity_address);
        mRlTitle = (RelativeLayout) findViewById(R.id.rl_title);
        mTitleLeft = (ImageView) findViewById(R.id.title_left);
        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mLvAddress = (ListView) findViewById(R.id.lv_address);
        mBtSubmit = (Button) findViewById(R.id.bt_submit);
        mLlNone = (LinearLayout) findViewById(R.id.ll_none);

    }

    @Override
    public void initDatas() {
        mTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(MainActivity.class);
            }
        });
        mBtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(NewAddressActivity.class);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        NetForJson netForJson = new NetForJson(Constant.URL_RECEIVER, new ReceiverCallback());
        netForJson.addParam("stype", "select");
        netForJson.addParam("uid", uid);
        Log.e("xx", "uid:" + uid);
        isShow = getIntent().getBooleanExtra("isShow", false);
        netForJson.execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mActivitySelf.finish();
        goToActivity(MainActivity.class);
    }

    @Override
    public void initOthers() {


    }

    class ReceiverCallback extends NetCallback<ReceiverEntity> {

        @Override
        public void onSuccess(ReceiverEntity receiverEntity) {
            if (receiverEntity.getSuccess().equals("1")) {
                if (receiverEntity.getReceivers().size() != 0) {
                    mItemLvAddressAdapter = new ItemLvAddressAdapter(mActivitySelf, receiverEntity.getReceivers());
                    mItemLvAddressAdapter.setShow(isShow);
                    if (isShow) {
                        setTitleRight("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ReceiverEntity.Receiver checkEntity = mItemLvAddressAdapter.checkEntity;
                                if (checkEntity != null) {
                                    Log.e("xx", checkEntity.toString());
                                    Bundle bundle=new Bundle();
                                    bundle.putSerializable("entity",checkEntity);
                                    goToActivity(RecordDetailActivity.class,"bundle",bundle);
                                }
                            }
                        });
                    }

                    mLvAddress.setAdapter(mItemLvAddressAdapter);
                    mLlNone.setVisibility(View.GONE);
                    mLvAddress.setVisibility(View.VISIBLE);
                } else {
                    mLlNone.setVisibility(View.VISIBLE);
                    mLvAddress.setVisibility(View.GONE);
                }
            } else {
                Toast.makeText(mActivitySelf, "您的网络有问题", Toast.LENGTH_SHORT).show();

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
