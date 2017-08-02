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


public class FindPwdActivity extends SCBaseActivity implements CompoundButton.OnCheckedChangeListener {
    private RelativeLayout mActivityFindPwd;
    private RelativeLayout mRl;
    private EditText mEditPhoneFindpwdActivity;
    private EditText mEditCodeFindpwdActivity;
    private Button mBtGetcodeFindpwdActivity;
    private EditText mEditPwdFindpwdActivity;
    private CheckBox mCbIsvisibelCommonloinActivity;
    private Button mBtLoginFindpwdActivity;
    private boolean getCode;
    private int time = 30;
    private String phone, mUserpwd;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
                    if (time > 0) {
                        time--;
                        mBtGetcodeFindpwdActivity.setText(time + " S");
                        mHandler.sendEmptyMessageDelayed(2, 1000);
                    } else {
                        mBtGetcodeFindpwdActivity.setText("获取验证码");
                        mBtGetcodeFindpwdActivity.setEnabled(true);
                    }
                    break;
                default:
                    Intent intent = new Intent(mActivitySelf, CommonLoginActivity.class);
                    intent.putExtra("uid", phone);
                    mActivitySelf.startActivity(intent);
                    break;
            }

        }
    };
    private String mCode;

    @Override
    public int initRootLayout() {
        return R.layout.activity_find_pwd;
    }

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
                            doUpdate();
                        }
                    });
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getCode = true;
                            Toast.makeText(mActivitySelf, "获取验证码成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(mActivitySelf, "验证码不对", Toast.LENGTH_SHORT).show();
                }
            } else {
                ((Throwable) data).printStackTrace();
            }
        }
    };

    @Override
    public void initViews() {

        mActivityFindPwd = (RelativeLayout) findViewById(R.id.activity_find_pwd);
        mRl = (RelativeLayout) findViewById(R.id.rl);
        mEditPhoneFindpwdActivity = (EditText) findViewById(R.id.edit_phone_findpwdActivity);
        mEditCodeFindpwdActivity = (EditText) findViewById(R.id.edit_code_findpwdActivity);
        mBtGetcodeFindpwdActivity = (Button) findViewById(R.id.bt_getcode_findpwdActivity);
        mEditPwdFindpwdActivity = (EditText) findViewById(R.id.edit_pwd_findpwdActivity);
        mCbIsvisibelCommonloinActivity = (CheckBox) findViewById(R.id.cb_isvisibel_commonloinActivity);
        mBtLoginFindpwdActivity = (Button) findViewById(R.id.bt_login_findpwdActivity);
        mCbIsvisibelCommonloinActivity.setOnCheckedChangeListener(this);
        mBtLoginFindpwdActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!getCode){
                    Toast.makeText(mActivitySelf, "请先获取验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (EdtUtil.isEdtEmpty(mEditCodeFindpwdActivity)) {
                    Toast.makeText(mActivitySelf, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }

                mCode = EdtUtil.getEdtText(mEditCodeFindpwdActivity);
                SMSSDK.submitVerificationCode("86", phone, mCode);
                mBtLoginFindpwdActivity.setBackgroundColor(Color.GRAY);
                mBtLoginFindpwdActivity.setEnabled(false);
            }
        });
    }

    @Override
    public void initDatas() {
        SMSSDK.registerEventHandler(eh); //注册短信回调
        mBtGetcodeFindpwdActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doGetCode();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh); //注册短信回调
    }

    public void doGetCode() {
        phone = EdtUtil.getEdtText(mEditPhoneFindpwdActivity);

        if (StringCheck.checkPhone(phone)) {
            mBtGetcodeFindpwdActivity.setEnabled(false);
            mBtGetcodeFindpwdActivity.setText("30 S");
            mHandler.sendEmptyMessageDelayed(2, 1000);
            SMSSDK.getVerificationCode("86", phone);
        } else {
            Toast.makeText(mActivitySelf, "手机号输入错误", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void initOthers() {
        setTitleCenter("修改密码");
        setTitleLeft("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(CommonLoginActivity.class);
            }
        });
    }


    @Override
    public boolean isUseTitleBar() {
        return true;
    }

    //明文 / 密文
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            mEditPwdFindpwdActivity.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            mEditPwdFindpwdActivity.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        mEditPwdFindpwdActivity.setSelection(EdtUtil.getEdtText(mEditPwdFindpwdActivity).length());

    }

    private void doUpdate() {
        mUserpwd = EdtUtil.getEdtText(mEditPwdFindpwdActivity);
        if (!getCode) {
            Toast.makeText(mActivitySelf, "请先获取验证码", Toast.LENGTH_SHORT).show();
            mBtGetcodeFindpwdActivity.setBackground(getResources().getDrawable(R.drawable.shape_bg_button));
            mBtGetcodeFindpwdActivity.setEnabled(true);
            return;
        }
        if (!StringCheck.check(mUserpwd)) {
            Toast.makeText(mActivitySelf, "请输入6-18位密码", Toast.LENGTH_SHORT).show();
            mBtGetcodeFindpwdActivity.setBackground(getResources().getDrawable(R.drawable.shape_bg_button));
            mBtGetcodeFindpwdActivity.setEnabled(true);
            return;
        }
        NetForJson netForJson = new NetForJson(Constant.URL_USERABOUT, new UpdateCallback());
        netForJson.addParam("type", "updatepwd");
        netForJson.addParam("uid", EdtUtil.getEdtText(mEditPhoneFindpwdActivity));
        netForJson.addParam("upwd", EdtUtil.getEdtText(mEditPwdFindpwdActivity));
        netForJson.execute();
    }

    class UpdateCallback extends NetCallback<UserEntity> {

        @Override
        public void onSuccess(UserEntity userEntity) {
            if (userEntity.getUid().equals("error")) {

            } else {
                Toast.makeText(mActivitySelf, "修改成功", Toast.LENGTH_SHORT).show();
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
            mBtGetcodeFindpwdActivity.setBackground(getResources().getDrawable(R.drawable.shape_bg_button));
            mBtGetcodeFindpwdActivity.setEnabled(true);
        }
    }
}
