package com.admin.exe.shop.ui.activity;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.exe.shop.Constant;
import com.admin.exe.shop.R;
import com.admin.exe.shop.base.SCBaseActivity;
import com.admin.exe.shop.db.DBConfig;
import com.admin.exe.shop.entity.UserEntity;
import com.admin.exe.shop.utils.NetCallback;
import com.admin.exe.shop.utils.NetForJson;
import com.admin.exe.shop.utils.StringCheck;
import com.admin.exe.shop.utils.ToastUtils;
import com.example.library.utils.EdtUtil;
import com.example.library.utils.SharedPrefrencesUtil;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;


public class CommonLoginActivity extends SCBaseActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private RelativeLayout mActivityCommonLogin;
    private EditText mEditPhoneCommonloginActivity;
    private EditText mEditPwdCommonloginActivity;
    private CheckBox mImgvIsvisibelCommonloinActivity;
    private TextView mTvForgetpwdCommonloginActivity;
    private Button mBtLoginCommonloginActivity;
    private String phone, pwd;


    @Override
    public int initRootLayout() {
        return R.layout.activity_common_login;
    }

    @Override
    public void initViews() {
        String uid = getIntent().getStringExtra("uid");

        mActivityCommonLogin = (RelativeLayout) findViewById(R.id.activity_common_login);
        mEditPhoneCommonloginActivity = (EditText) findViewById(R.id.edit_phone_commonloginActivity);
        mEditPwdCommonloginActivity = (EditText) findViewById(R.id.edit_pwd_commonloginActivity);
        mImgvIsvisibelCommonloinActivity = (CheckBox) findViewById(R.id.imgv_isvisibel_commonloinActivity);
        mTvForgetpwdCommonloginActivity = (TextView) findViewById(R.id.tv_forgetpwd_commonloginActivity);
        mBtLoginCommonloginActivity = (Button) findViewById(R.id.bt_login_commonloginActivity);
        mImgvIsvisibelCommonloinActivity.setOnCheckedChangeListener(this);
        mBtLoginCommonloginActivity.setOnClickListener(this);
        if (uid != null) {
            mEditPhoneCommonloginActivity.setText(uid);
        }
    }

    @Override
    public void initDatas() {
        setTitleCenter("用户登录");
        setTitleLeft("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelf.finish();
            }
        });
        setTitleRight("注册", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(RegActivity.class);

            }
        });
        mTvForgetpwdCommonloginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(FindPwdActivity.class);
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
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            mEditPwdCommonloginActivity.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            mEditPwdCommonloginActivity.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        mEditPwdCommonloginActivity.setSelection(EdtUtil.getEdtText(mEditPwdCommonloginActivity).length());

    }

    @Override
    public void onClick(View v) {
        phone = EdtUtil.getEdtText(mEditPhoneCommonloginActivity);
        pwd = EdtUtil.getEdtText(mEditPwdCommonloginActivity);
        if (!StringCheck.checkPhone(phone)) {
            Toast.makeText(mActivitySelf, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!StringCheck.check(pwd)) {
            Toast.makeText(mActivitySelf, "请输入6-18位密码", Toast.LENGTH_SHORT).show();
            return;
        }
        doLogin();
    }

    private void doLogin() {
        NetForJson netForJson = new NetForJson(Constant.URL_USERABOUT, new LoginCallback());
        netForJson.addParam("type", "login");
        netForJson.addParam("uid", phone);
        netForJson.addParam("upwd", pwd);
        netForJson.execute();
    }

    class LoginCallback extends NetCallback<UserEntity> {

        @Override
        public void onSuccess(UserEntity userEntity) {
            if (userEntity.getUid().equals("error")) {
                Toast.makeText(mActivitySelf, "用户名或者密码错误", Toast.LENGTH_SHORT).show();

            } else {
                DbManager.DaoConfig daoConfig = DBConfig.getDaoConfig();
                try {
                    List<UserEntity> all = x.getDb(daoConfig).findAll(UserEntity.class);
                    if (all != null) {
                        if (all.contains(userEntity)) {
                            ////////////////////////////
                        } else {
                            x.getDb(daoConfig).save(userEntity);
                        }
                    } else {
                        x.getDb(daoConfig).save(userEntity);
                    }
                    SharedPrefrencesUtil.saveData(mActivitySelf, "shop", "isLogin", true);
                    SharedPrefrencesUtil.saveData(mActivitySelf, "shop", "uid", EdtUtil.getEdtText(mEditPhoneCommonloginActivity));
                    Toast.makeText(mActivitySelf, "登录成功", Toast.LENGTH_SHORT).show();
                    mActivitySelf.finish();
                } catch (DbException e) {
                    e.printStackTrace();
                }


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
