package com.admin.exe.shop.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.exe.shop.Constant;
import com.admin.exe.shop.R;
import com.admin.exe.shop.base.SCBaseActivity;
import com.admin.exe.shop.entity.ReceiverEntity;
import com.admin.exe.shop.entity.RecordEntity;
import com.admin.exe.shop.utils.NetCallback;
import com.admin.exe.shop.utils.NetForJson;
import com.admin.exe.shop.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.example.library.utils.DateUtils;

public class RecordDetailActivity extends SCBaseActivity {
    private RelativeLayout mActivityRecordDetail;
    private LinearLayout mLl;
    private TextView mPayNo, mPayName, mPayPrice, mSnum;
    private LinearLayout mLlRoot;
    private ImageView mImgvHead;
    private TextView mTvTitle, mTvPrice, mTvTime, mTvCaozuo1, mTvCaozuo2, mTvMystype;
    private LinearLayout mLlChoose;
    private TextView mTvRname, mTvRphone, mTvRaddress;
    private Button mBtSubmit;
    private RecordEntity.RecordsBean mRecordsBean;
    private ImageView mTitleLeft;
    private TextView mTitleCenter;
    private TextView mTitleRight;
    private int pos;
    private String right = "编辑";
    private ReceiverEntity.Receiver checkEntity;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    goToActivity(RecordActivity.class);
                    break;
                case 1:
                    Toast.makeText(mActivitySelf, "已告知卖家尽早发货", Toast.LENGTH_SHORT).show();
                    if (mDialog != null) {
                        mDialog.dismiss();
                    }
                    mTvCaozuo1.setVisibility(View.INVISIBLE);
                    mTvCaozuo1.setOnClickListener(null);
                    break;
                case 2:
                    break;
            }

        }
    };
    private String mytype;
    private Dialog mDialog;


    @Override
    public int initRootLayout() {
        return R.layout.activity_record_detail;
    }

    @Override
    public void initViews() {
        mTitleLeft = (ImageView) findViewById(R.id.title_left);
        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mTitleRight = (TextView) findViewById(R.id.title_right);
        mActivityRecordDetail = (RelativeLayout) findViewById(R.id.activity_record_detail);
        mLl = (LinearLayout) findViewById(R.id.ll);
        mPayNo = (TextView) findViewById(R.id.pay_no);
        mPayName = (TextView) findViewById(R.id.pay_name);
        mPayPrice = (TextView) findViewById(R.id.pay_price);
        mSnum = (TextView) findViewById(R.id.snum);
        mLlRoot = (LinearLayout) findViewById(R.id.ll_root);
        mImgvHead = (ImageView) findViewById(R.id.imgv_head);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        mTvCaozuo1 = (TextView) findViewById(R.id.tv_caozuo1);
        mTvCaozuo2 = (TextView) findViewById(R.id.tv_caozuo2);
        mTvMystype = (TextView) findViewById(R.id.tv_mystype);
        mLlChoose = (LinearLayout) findViewById(R.id.ll_choose);
        mTvRname = (TextView) findViewById(R.id.tv_rname);
        mTvRphone = (TextView) findViewById(R.id.tv_rphone);
        mTvRaddress = (TextView) findViewById(R.id.tv_raddress);
        mBtSubmit = (Button) findViewById(R.id.bt_submit);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            mRecordsBean = (RecordEntity.RecordsBean) bundle.getSerializable("entity");
            pos=bundle.getInt("pos");
            if (mRecordsBean != null) {
                doPrepare();
            }
        }
        mDialog = new Dialog(mActivitySelf);
        mDialog.setContentView(R.layout.dig_ing);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
    }

    private void doPrepare() {
        Constant.mRecordsBean = mRecordsBean;
        mPayNo.setText(mRecordsBean.getLid());
        mPayName.setText(mRecordsBean.getSname());
        mPayPrice.setText("￥" + mRecordsBean.getBprice());
        mSnum.setText(mRecordsBean.getBnum());
        //mTvTitle, mTvPrice, mTvTime, mmTvCaozuo1, mmTvCaozuo2, mTvMystype;
        mTvTitle.setText(mRecordsBean.getSname());
        Glide.with(mActivitySelf).load(mRecordsBean.getPic()).into(mImgvHead);
        mTvPrice.setText("￥" + mRecordsBean.getBprice());
        mTvTime.setText(DateUtils.getDateString("yyyy-MM-dd HH:mm", mRecordsBean.getLtime()));
        switch (mRecordsBean.getMyType()) {
            case RecordEntity.WEIFAHUO:
                mytype = "待发货";
                mTvMystype.setTextColor(mActivitySelf.getResources().getColor(R.color.yellow));
                mTvCaozuo1.setText("提醒发货");

                mTvCaozuo2.setText("取消订单");
                mTvCaozuo1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mDialog.show();
                        mHandler.sendEmptyMessageDelayed(1, 3000);
                    }
                });
                mTvCaozuo2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mActivitySelf);
                        builder.setMessage("确定要取消订单吗？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        doCheckRecord();
                                    }
                                })
                                .setNegativeButton("取消", null);
                        builder.create().show();
                    }
                });
                break;
            case RecordEntity.DAISHOUHUO:
                mytype = "待收货";
                mTvMystype.setTextColor(mActivitySelf.getResources().getColor(R.color.red));
                mTvCaozuo1.setText("查看物流");
                mTvCaozuo2.setText("确认收货");
                break;
            case RecordEntity.WEIPINGJIA:
                mytype = "未评价";
                mTvMystype.setTextColor(mActivitySelf.getResources().getColor(R.color.blue));
                mTvCaozuo1.setText("前去评价");
                mTvCaozuo1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToActivity(EvaluteActivity.class);
                    }
                });
                mTvCaozuo2.setVisibility(View.INVISIBLE);
                break;
            case RecordEntity.YISHOUHUO:
                mytype = "已收货";
                mTvCaozuo1.setText("删除订单");
                mTvCaozuo2.setVisibility(View.INVISIBLE);
                mTvMystype.setTextColor(mActivitySelf.getResources().getColor(R.color.green));
                break;

        }
        mTvMystype.setText(mytype);
        //mTvRname, mTvRphone, mTvRaddress;
        mTvRname.setText(mRecordsBean.getCname());
        mTvRaddress.setText(mRecordsBean.getAddress());
        mTvRphone.setText(mRecordsBean.getCphone());
    }

    private void doCheckRecord() {
        NetForJson netForJson = new NetForJson(Constant.URL_RECORD, new CheckCall());
        netForJson.addParam("stype", "delete");
        netForJson.addParam("bnum", Constant.mRecordsBean.getBnum());
        netForJson.addParam("sid", Constant.mRecordsBean.getSid());
        netForJson.addParam("lid", Constant.mRecordsBean.getLid());
        netForJson.execute();
    }

    @Override
    public void initDatas() {
        mTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mTitleCenter.setText("订单详情");
        mTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (right.equals("编辑")) {
                    right = "取消";
                    Toast.makeText(RecordDetailActivity.this, "您现在可以编辑收货人了", Toast.LENGTH_SHORT).show();
                    mLlChoose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Constant.from = 1;
                            goToActivity(AddressChooseActivity.class);
                        }
                    });
                    mBtSubmit.setVisibility(View.VISIBLE);
                } else {
                    right = "编辑";

                    mBtSubmit.setVisibility(View.GONE);
                }
                mTitleRight.setText(right);
            }
        });
        mBtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUpdate();
            }
        });
    }

    private void doUpdate() {
        NetForJson netForJson = new NetForJson(Constant.URL_RECORD, new ResCall());
        netForJson.addParam("stype", "update");
        netForJson.addParam("lid", Constant.mRecordsBean.getLid());
        netForJson.addParam("rid", checkEntity.getRid());
        netForJson.execute();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle = intent.getBundleExtra("bundle1");
        if (bundle != null) {
            checkEntity = (ReceiverEntity.Receiver) bundle.getSerializable("entity");
            if (checkEntity != null) {
                mTvRname.setTextColor(mActivitySelf.getResources().getColor(R.color.red));
                mTvRaddress.setTextColor(mActivitySelf.getResources().getColor(R.color.red));
                mTvRphone.setTextColor(mActivitySelf.getResources().getColor(R.color.red));
                mTvRname.setText(checkEntity.getRname());
                mTvRaddress.setText(checkEntity.getRaddress());
                mTvRphone.setText(checkEntity.getRphone());
            }
        } else {
            Log.e("xx", "bundle1为空");
        }
    }


    @Override
    public void initOthers() {


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, RecordActivity.class);
        intent.putExtra("pos", pos);
        startActivity(intent);
    }

    class ResCall extends NetCallback<RecordEntity> {

        @Override
        public void onSuccess(RecordEntity recordEntity) {
            if (recordEntity.getSuccess().equals("2")) {
                Toast.makeText(mActivitySelf, "修改成功", Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessageDelayed(0, 1500);
            } else {
                Toast.makeText(mActivitySelf, "修改失败", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(Throwable t) {
            Log.e("xx", t.toString());
            ToastUtils.getToastOnFailure(mActivitySelf);
        }

        @Override
        public void onFinished() {

        }
    }

    class CheckCall extends NetCallback<RecordEntity> {

        @Override
        public void onSuccess(RecordEntity recordEntity) {
            if (recordEntity.getSuccess().equals("1")) {
                Toast.makeText(mActivitySelf, "订单删除成功", Toast.LENGTH_SHORT).show();
                goToActivity(RecordActivity.class);
                mActivitySelf.finish();
            }
        }

        @Override
        public void onError(Throwable t) {
            Log.e("xx", t.toString());
            ToastUtils.getToastOnFailure(mActivitySelf);
        }

        @Override
        public void onFinished() {

        }
    }
}
