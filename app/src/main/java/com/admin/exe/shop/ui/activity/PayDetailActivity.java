package com.admin.exe.shop.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.exe.shop.Constant;
import com.admin.exe.shop.R;
import com.admin.exe.shop.base.SCBaseActivity;
import com.admin.exe.shop.entity.ReceiverEntity;
import com.admin.exe.shop.entity.RecordEntity;
import com.admin.exe.shop.utils.LogerUtils;
import com.admin.exe.shop.utils.NetCallback;
import com.admin.exe.shop.utils.NetForJson;
import com.admin.exe.shop.utils.ToastUtils;

public class PayDetailActivity extends SCBaseActivity implements View.OnClickListener {
    private TextView mPayNo;
    private TextView mPayName;
    private TextView mPayPrice;
    private Button mSubmit;
    private CheckBox mCbZhifubao;
    private CheckBox mCbWeixin;
    private CheckBox[] cbs;
    private String type = "0";
    private String sid, sname, snum, sprice;
    private String uid;
    private String rid;
    private TextView mSnum;
    private ReceiverEntity.Receiver entity;
    private TextView mReceiver;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mDialog != null) {
                mDialog.dismiss();
            }

            doAddRecord();
        }
    };

    private Dialog mDialog;

    @Override
    public int initRootLayout() {
        return R.layout.activity_pay_detail;
    }

    @Override
    public void initViews() {
        mSnum = (TextView) findViewById(R.id.snum);
        mReceiver = (TextView) findViewById(R.id.receiver);
        mPayNo = (TextView) findViewById(R.id.pay_no);
        mPayName = (TextView) findViewById(R.id.pay_name);
        mPayPrice = (TextView) findViewById(R.id.pay_price);
        mSubmit = (Button) findViewById(R.id.submit);
        mCbZhifubao = (CheckBox) findViewById(R.id.cb_zhifubao);
        mCbWeixin = (CheckBox) findViewById(R.id.cb_weixin);
        cbs = new CheckBox[]{mCbWeixin, mCbZhifubao};
        mCbZhifubao.setOnClickListener(this);
        mCbWeixin.setOnClickListener(this);
        mReceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.from = 0;
                goToActivity(AddressChooseActivity.class);
            }
        });
        uid = LogerUtils.getUid(mActivitySelf);
        rid = "l" + System.currentTimeMillis() / 10000 + uid.substring(7) + (Constant.random.nextInt(100) + 10);
    }


    @Override
    public void initDatas() {
        sname = getIntent().getStringExtra("sname");
        sid = getIntent().getStringExtra("sid");
        sprice = getIntent().getStringExtra("sprice");
        snum = getIntent().getStringExtra("snum");
        Constant.sname = sname;
        Constant.sid = sid;
        Constant.sprice = sprice;
        Constant.snum = snum;
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mReceiver.getText().equals("请选择")){
                    Toast.makeText(mActivitySelf, "请选择收货人", Toast.LENGTH_SHORT).show();
                    return ;
                }
                switch (type) {
                    case "0":
                        Toast.makeText(mActivitySelf, "支付宝支付", Toast.LENGTH_SHORT).show();
                        break;
                    case "1":
                        Toast.makeText(mActivitySelf, "微信支付", Toast.LENGTH_SHORT).show();
                        break;
                }
                mDialog = new Dialog(mActivitySelf);
                mDialog.setContentView(R.layout.dig_wait);
                mDialog.setCancelable(false);
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.show();
                mHandler.sendEmptyMessageDelayed(0, 3000);
            }
        });
        doPrepare();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle = intent.getBundleExtra("bundle");
        if (bundle != null) {
            Log.e("xx","bundle不为空");
            entity = (ReceiverEntity.Receiver) bundle.getSerializable("entity");
            if (entity != null) {
                Log.e("xx",entity.toString());
                StringBuffer sb = new StringBuffer();
                sb.append(entity.getRname() + "  " + entity.getRphone());
                sb.append("\r\n");
                sb.append(entity.getRaddress().replace(",", ""));
                mReceiver.setText(sb.toString());
            }
        }
        mPayNo.setText(rid);
        mPayName.setText(Constant.sname);
        mPayPrice.setText(Constant.sprice);
        mSnum.setText(Constant.snum);
    }

   

    private void doPrepare() {

        mPayNo.setText(rid);
        mPayName.setText(sname);
        mPayPrice.setText(sprice);
        mSnum.setText(snum);
    }

    @Override
    public void initOthers() {
        setTitleCenter("订单详情");
        setTitleLeft("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelf.finish();
            }
        });
    }

    @Override
    public boolean isUseTitleBar() {
        return true;
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < cbs.length; i++) {
            cbs[i].setChecked(false);
        }
        CheckBox cb = (CheckBox) v;
        if (cb.isChecked()) {
            cb.setChecked(false);
        } else {
            cb.setChecked(true);
        }
        switch (v.getId()) {
            case R.id.cb_zhifubao:
                type = "0";
                break;
            case R.id.cb_weixin:
                type = "1";
                break;
        }
    }


    private void doAddRecord() {
        NetForJson netForJson = new NetForJson(Constant.URL_RECORD, new RecordCall());
        netForJson.addParam("stype", "add");
        netForJson.addParam("uid", uid);
        netForJson.addParam("lid", rid);
        netForJson.addParam("sid", Constant.sid);
        netForJson.addParam("bnum", Constant.snum);
        netForJson.addParam("bprice", Constant.sprice.substring(1));
        netForJson.addParam("address", entity.getRaddress());
        netForJson.addParam("rid", entity.getRid());
        netForJson.addParam("ltime", String.valueOf(System.currentTimeMillis()));
        Log.e("xx", "uid:" + uid + ",lid:" + rid + ",sid:" + Constant.sid + ",bnum:" + Constant.snum +
                ",bprice:" + Constant.sprice.substring(1) + ",address:" + entity.getRaddress() + ",rid:" + entity.getRid()
        +",ltime:"+String.valueOf(System.currentTimeMillis()));
        netForJson.execute();
    }

    class RecordCall extends NetCallback<RecordEntity> {

        @Override
        public void onSuccess(RecordEntity recordEntity) {
            if (recordEntity.getSuccess().equals("1")) {
                Toast.makeText(mActivitySelf, "添加订单成功", Toast.LENGTH_SHORT).show();
                goToActivity(RecordActivity.class);
                mActivitySelf.finish();
            } else {
                Toast.makeText(mActivitySelf, "添加失败", Toast.LENGTH_SHORT).show();
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
