package com.admin.exe.shop.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.admin.exe.shop.Constant;
import com.admin.exe.shop.R;
import com.admin.exe.shop.base.SCBaseActivity;
import com.admin.exe.shop.entity.UserEntity;
import com.admin.exe.shop.utils.NetCallback;
import com.admin.exe.shop.utils.NetForJson;
import com.admin.exe.shop.utils.StringCheck;
import com.admin.exe.shop.utils.ToastUtils;
import com.example.library.utils.EdtUtil;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


public class RegActivity extends SCBaseActivity implements CompoundButton.OnCheckedChangeListener {
    private RelativeLayout mRl;
    private EditText mEditPhoneRegActivity;
    private EditText mEditPwdRegActivity;
    private CheckBox mImgvIsvisibelRegActivity;
    private EditText mEditCodeRegActivity;
    private Button mBtGetcodeRegActivity;
    private Button mBtRegRegActivity;
    private String phone, mUserpwd, mCode;
    private int time = 30;
    private boolean getCode=false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
                    if (time > 0) {
                        time--;
                        mBtGetcodeRegActivity.setText(time + " S");
                        mHandler.sendEmptyMessageDelayed(2, 1000);
                    } else {
                        mBtGetcodeRegActivity.setText("获取验证码");
                        mBtGetcodeRegActivity.setEnabled(true);
                    }
                    break;
                default:
                    Intent intent = new Intent(mActivitySelf, CommonLoginActivity.class);
                    intent.putExtra("uid", EdtUtil.getEdtText(mEditPhoneRegActivity));
                    mActivitySelf.startActivity(intent);
                    break;
            }

        }
    };


    //短信回调
    EventHandler eh = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            doReg();



                        }
                    });
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getCode=true;
                            Toast.makeText(mActivitySelf, "获取验证码成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                }
            } else {
                ((Throwable) data).printStackTrace();
            }
        }
    };


    @Override
    public int initRootLayout() {
        return R.layout.activity_reg;
    }

    @Override
    public void initViews() {

        mRl = (RelativeLayout) findViewById(R.id.rl);
        mEditPhoneRegActivity = (EditText) findViewById(R.id.edit_phone_regActivity);
        mEditPwdRegActivity = (EditText) findViewById(R.id.edit_pwd_regActivity);
        mImgvIsvisibelRegActivity = (CheckBox) findViewById(R.id.imgv_isvisibel_regActivity);
        mEditCodeRegActivity = (EditText) findViewById(R.id.edit_code_regActivity);
        mBtGetcodeRegActivity = (Button) findViewById(R.id.bt_getcode__regActivity);
        mBtRegRegActivity = (Button) findViewById(R.id.bt_reg_regActivity);
        mImgvIsvisibelRegActivity.setOnCheckedChangeListener(this);
        mBtRegRegActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EdtUtil.isEdtEmpty(mEditCodeRegActivity)) {
                    Toast.makeText(mActivitySelf, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!getCode){
                    Toast.makeText(mActivitySelf, "请先获取验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                mCode = EdtUtil.getEdtText(mEditCodeRegActivity);
                SMSSDK.submitVerificationCode("86", phone, mCode);
                mBtRegRegActivity.setBackgroundColor(Color.GRAY);
                mBtRegRegActivity.setEnabled(false);
            }
        });
        mBtGetcodeRegActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doGetCode();
            }
        });

    }

    public void doGetCode() {
        phone = EdtUtil.getEdtText(mEditPhoneRegActivity);
        mUserpwd = EdtUtil.getEdtText(mEditPwdRegActivity);
        if (StringCheck.check(mUserpwd)) {
            if (StringCheck.checkPhone(phone)) {
                mBtGetcodeRegActivity.setEnabled(false);
                mBtGetcodeRegActivity.setText("30 S");
                mHandler.sendEmptyMessageDelayed(2, 1000);
                SMSSDK.getVerificationCode("86", phone);

            } else {
                Toast.makeText(mActivitySelf, "手机号输入错误", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mActivitySelf, "请选输入正确的密码", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void initDatas() {
        SMSSDK.registerEventHandler(eh); //注册短信回调
        setTitleCenter("用户注册");
        setTitleLeft("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelf.finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh); //注册短信回调
    }

    @Override
    public void initOthers() {

    }


    @Override
    public boolean isUseTitleBar() {
        return true;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            mEditPwdRegActivity.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            mEditPwdRegActivity.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        mEditPwdRegActivity.setSelection(EdtUtil.getEdtText(mEditPwdRegActivity).length());

    }

    private void doReg() {
        NetForJson netForJson = new NetForJson(Constant.URL_USERABOUT, new RegCallback());
        netForJson.addParam("type", "reg");
        netForJson.addParam("uid", EdtUtil.getEdtText(mEditPhoneRegActivity));
        netForJson.addParam("upwd", EdtUtil.getEdtText(mEditPwdRegActivity));
        netForJson.execute();
    }

    class RegCallback extends NetCallback<UserEntity> {

        @Override
        public void onSuccess(UserEntity userEntity) {
            if (userEntity.getUid().equals("error")) {
                Toast.makeText(mActivitySelf, "已有此用户，可直接登录", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mActivitySelf, "注册成功", Toast.LENGTH_SHORT).show();
                mActivitySelf.finish();
                mHandler.sendEmptyMessageDelayed(0, 1500);

            }

        }

        @Override
        public void onError(Throwable throwable) {
            ToastUtils.getToastOnFailure(mActivitySelf);
            Log.e("xx", throwable.toString());
        }

        @Override
        public void onFinished() {
            mBtRegRegActivity.setBackground(getResources().getDrawable(R.drawable.shape_bg_button));
            mBtRegRegActivity.setEnabled(true);
        }
    }
}