package com.admin.exe.shop.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
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

public class AddressChooseActivity extends SCBaseActivity {
    private ListView mLvAddress;
    private LinearLayout mLlNone;
    private ItemLvAddressAdapter mItemLvAddressAdapter;
    private String uid;
    private Button mBtSubmit;


    @Override
    public int initRootLayout() {
        return R.layout.activity_address_choose;
    }

    @Override
    public void initViews() {
        mLvAddress = (ListView) findViewById(R.id.lv_address);
        mLlNone = (LinearLayout) findViewById(R.id.ll_none);
        uid = LogerUtils.getUid(mActivitySelf);
        mBtSubmit = (Button) findViewById(R.id.bt_submit);
        mBtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(NewAddressActivity.class);
            }
        });
    }

    @Override
    public void initDatas() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        NetForJson netForJson = new NetForJson(Constant.URL_RECEIVER, new ReceiverCallback());
        netForJson.addParam("stype", "select");
        netForJson.addParam("uid", uid);
        netForJson.execute();
    }

    @Override
    public void initOthers() {
        setTitleCenter("选择收货人");
        setTitleLeft("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelf.finish();
            }
        });
        setTitleRight("确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReceiverEntity.Receiver checkEntity = mItemLvAddressAdapter.checkEntity;
                if (checkEntity != null) {
                    Log.e("xx", checkEntity.toString());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("entity", checkEntity);

                    if (Constant.from == 0) {
                        goToActivity(PayDetailActivity.class, "bundle", bundle);
                    } else {
                        goToActivity(RecordDetailActivity.class, "bundle1", bundle);
                    }
                    mActivitySelf.finish();
                } else {
                    Toast.makeText(mActivitySelf, "尚未选择收件人", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean isUseTitleBar() {
        return true;
    }

    class ReceiverCallback extends NetCallback<ReceiverEntity> {

        @Override
        public void onSuccess(final ReceiverEntity receiverEntity) {
            if (receiverEntity.getSuccess().equals("1")) {
                if (receiverEntity.getReceivers().size() != 0) {
                    mItemLvAddressAdapter = new ItemLvAddressAdapter(mActivitySelf, receiverEntity.getReceivers());
                    mItemLvAddressAdapter.setShow(true);
                    mItemLvAddressAdapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = (int) v.getTag();
                            for (int i = 0; i < mItemLvAddressAdapter.cbs.size(); i++) {
                                mItemLvAddressAdapter.cbs.get(i).setChecked(false);
                            }
                            mItemLvAddressAdapter.cbs.get(pos).setChecked(true);
                            mItemLvAddressAdapter.checkEntity = receiverEntity.getReceivers().get(pos);
                        }
                    });
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
