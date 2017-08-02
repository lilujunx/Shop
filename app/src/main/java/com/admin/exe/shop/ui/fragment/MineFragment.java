package com.admin.exe.shop.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.exe.shop.Constant;
import com.admin.exe.shop.R;
import com.admin.exe.shop.base.SCBaseFragment;
import com.admin.exe.shop.db.DBConfig;
import com.admin.exe.shop.entity.UserEntity;
import com.admin.exe.shop.ui.activity.AddressActivity;
import com.admin.exe.shop.ui.activity.MainActivity;
import com.admin.exe.shop.ui.activity.MyCollectActivity;
import com.admin.exe.shop.ui.activity.MyShopCarActivity;
import com.admin.exe.shop.ui.activity.RecordActivity;
import com.admin.exe.shop.ui.adapter.ItemLvMineAdapter;
import com.admin.exe.shop.utils.GlideUtils;
import com.example.library.utils.SharedPrefrencesUtil;

import org.xutils.ex.DbException;
import org.xutils.x;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends SCBaseFragment implements View.OnClickListener {
    private ImageView mImgvUserhead;
    private TextView mTvUsername;
    private TextView mTvUid;
    private ItemLvMineAdapter mItemLvMineAdapter;
    private ListView mLvMine;
    private Button mBtExit;
    private UserEntity user;

    @Override
    public int initRootLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initViews() {
        mImgvUserhead = (ImageView) findViewById(R.id.imgv_userhead);
        mTvUsername = (TextView) findViewById(R.id.tv_username);
        mTvUid = (TextView) findViewById(R.id.tv_uid);
        mItemLvMineAdapter = new ItemLvMineAdapter(mActivitySelf, Constant.titles);
        mLvMine = (ListView) findViewById(R.id.lv_mine);
        mBtExit = (Button) findViewById(R.id.bt_exit);
        mLvMine.setAdapter(mItemLvMineAdapter);
        mBtExit.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        String uid = SharedPrefrencesUtil.getData(mActivitySelf, "shop", "uid", "");

        try {
            user = x.getDb(DBConfig.getDaoConfig()).selector(UserEntity.class).where("uid", "=", uid).findFirst();
            if (user != null) {
                mTvUid.setText(user.getUid());
                if (user.getUname() == null || "".equals(user.getUname())) {
                    String name = "用户" + Constant.random.nextInt(1000) + 10;
                    mTvUsername.setText(name);
                } else {
                    mTvUsername.setText(user.getUname());
                }
                Log.e("xx", user.toString());
                if (user.getUsex().equals("女")) {
                    GlideUtils.getCircular(mActivitySelf, R.drawable.woman, mImgvUserhead);
                } else {
                    GlideUtils.getCircular(mActivitySelf, R.drawable.man, mImgvUserhead);
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initDatas() {
        setTitleCenter("个人中心");
        if (mTitleLeft != null) {
            mTitleLeft.setVisibility(View.INVISIBLE);
        }
        mItemLvMineAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Bundle bundle = new Bundle();
                int pos = 0;
                switch (position) {
                    case 0:
                        //地址管理
                        goToActivity(AddressActivity.class);
                        return;
                    case 4:
                        //我的收藏
                        goToActivity(MyCollectActivity.class);
                        return;
                    case 6:
                        //我的购物车
                        goToActivity(MyShopCarActivity.class);
                        return;
                    case 1:
                        pos = 2;
                        //待收货
                        break;
                    case 2:
                        pos = 1;
                        //待发货
                        break;
                    case 3:
                        pos = 3;
                        //待付款
                        break;
                    case 5:
                        //全部订单
                        pos = 0;
                        break;
                }
                bundle.putInt("pos", pos);
                goToActivity(RecordActivity.class, "bundle", bundle);
            }
        });
    }

    @Override
    public void onClick(View v) {
        SharedPrefrencesUtil.saveData(mActivitySelf, "shop", "isLogin", false);
        Toast.makeText(mActivitySelf, "注销成功", Toast.LENGTH_SHORT).show();
        MainActivity main = (MainActivity) getActivity();
        main.mRbHome.setChecked(true);
    }

    @Override
    public boolean isUseTitleBar() {
        return true;
    }
}