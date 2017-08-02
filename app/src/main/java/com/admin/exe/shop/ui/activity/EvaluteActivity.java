package com.admin.exe.shop.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.exe.shop.Constant;
import com.admin.exe.shop.R;
import com.admin.exe.shop.base.SCBaseActivity;
import com.admin.exe.shop.entity.EvaluteEntity;
import com.admin.exe.shop.ui.views.StarBarView;
import com.admin.exe.shop.utils.LogerUtils;
import com.admin.exe.shop.utils.NetCallback;
import com.admin.exe.shop.utils.NetForJson;
import com.bumptech.glide.Glide;
import com.example.library.utils.DateUtils;
import com.example.library.utils.EdtUtil;

public class EvaluteActivity extends SCBaseActivity implements View.OnClickListener {
    private LinearLayout mActivityEvaluate;
    private StarBarView mSbvStarbar;
    private EditText mEditDetail;
    private Button mBtSubmit;
    private ImageView mImgvHead;
    private TextView mTvRid;
    private TextView mTvTitle;
    private TextView mTvPrice;
    private TextView mTvTime;


    @Override
    public int initRootLayout() {
        return R.layout.activity_evalute;
    }

    @Override
    public void initViews() {
        mActivityEvaluate = (LinearLayout) findViewById(R.id.activity_evaluate);
        mSbvStarbar = (StarBarView) findViewById(R.id.sbv_starbar);
        mEditDetail = (EditText) findViewById(R.id.edit_detail);
        mBtSubmit = (Button) findViewById(R.id.bt_submit);
        mBtSubmit.setOnClickListener(this);
        mImgvHead = (ImageView) findViewById(R.id.imgv_head);
        mTvRid = (TextView) findViewById(R.id.tv_rid);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mTvTime = (TextView) findViewById(R.id.tv_time);

        Glide.with(mActivitySelf).load(Constant.mRecordsBean.getPic()).into(mImgvHead);
        mTvRid.setText("订单编号 : " + Constant.mRecordsBean.getLid());
        mTvTitle.setText(Constant.mRecordsBean.getSname());
        if (Constant.isDebug) {
            Toast.makeText(mActivitySelf, Constant.mRecordsBean.getSid(), Toast.LENGTH_SHORT).show();
        }
        mTvPrice.setText("￥ " + Constant.mRecordsBean.getBprice());
        mTvTime.setText(DateUtils.getDateString("yyyy-MM-dd  HH:mm", Constant.mRecordsBean.getLtime()));

    }

    @Override
    public void initDatas() {
        setTitleCenter("用户评价");
        setTitleLeft("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void initOthers() {

    }

    @Override
    public boolean isUseTitleBar() {
        return true;
    }

    @Override
    public void onClick(View v) {
        NetForJson netForJson = new NetForJson(Constant.URL_EVALUTEABOUT, new EvaCall());
        netForJson.addParam("uid", LogerUtils.getUid(mActivitySelf));
        netForJson.addParam("lid", Constant.mRecordsBean.getLid());
        netForJson.addParam("sid", Constant.mRecordsBean.getSid());
        netForJson.addParam("detail", EdtUtil.getEdtText(mEditDetail));
        netForJson.addParam("star", String.valueOf(mSbvStarbar.getStarRating()));
        netForJson.addParam("time", String.valueOf(System.currentTimeMillis()));

        netForJson.execute();
    }

    class EvaCall extends NetCallback<EvaluteEntity> {

        @Override
        public void onSuccess(EvaluteEntity evaluteEntity) {
            if (evaluteEntity.getSuccess().equals("1")) {
                Toast.makeText(mActivitySelf, "评价成功", Toast.LENGTH_SHORT).show();
                goToActivity(MainActivity.class);
                mActivitySelf.finish();
            }
        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onFinished() {

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mActivitySelf.finish();
        goToActivity(RecordActivity.class);
    }
}
