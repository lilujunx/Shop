package com.admin.exe.shop.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.exe.shop.R;
import com.admin.exe.shop.base.SCBaseActivity;
import com.admin.exe.shop.db.DBConfig;
import com.admin.exe.shop.entity.ShopCarEntity;
import com.admin.exe.shop.ui.adapter.ItemLvShopcarAdapter;

import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyShopCarActivity extends SCBaseActivity {
    private LinearLayout mLlNone;
    private ListView mLvShopcar;
    private Button mBtSubmit;
    private ItemLvShopcarAdapter mItemLvShopcarAdapter;
    private ImageView mTitleLeft;
    private TextView mTitleCenter;
    private TextView mTitleRight;
    private String right = "全选";
    private double totalPrice;
    private List<ShopCarEntity> all = new ArrayList<>();
    private List<ShopCarEntity> mChoose = new ArrayList<>();

    @Override
    public int initRootLayout() {
        return R.layout.activity_my_shop_car;
    }

    @Override
    public void initViews() {
        mLlNone = (LinearLayout) findViewById(R.id.ll_none);
        mLvShopcar = (ListView) findViewById(R.id.lv_shopcar);
        mBtSubmit = (Button) findViewById(R.id.bt_submit);
        mTitleLeft = (ImageView) findViewById(R.id.title_left);
        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mTitleRight = (TextView) findViewById(R.id.title_right);

        try {
            all = x.getDb(DBConfig.getDaoConfig()).findAll(ShopCarEntity.class);
            if (all.size() != 0) {
                mLvShopcar.setVisibility(View.VISIBLE);
                mLlNone.setVisibility(View.GONE);
                mItemLvShopcarAdapter = new ItemLvShopcarAdapter(mActivitySelf, all);
                mItemLvShopcarAdapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (int) v.getTag();
                        CheckBox cb = (CheckBox) v;
                        if (cb.isChecked()) {
                            mChoose.add(all.get(pos));
                        } else {
                            mChoose.remove(all.get(pos));
                        }
                        if (mChoose.size() != 0) {
                            mBtSubmit.setVisibility(View.VISIBLE);

                            mBtSubmit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    doAdds();
                                }
                            });
                        } else {
                            mBtSubmit.setVisibility(View.GONE);
                        }
                    }

                });


                mLvShopcar.setAdapter(mItemLvShopcarAdapter);
            } else {
                mLlNone.setVisibility(View.VISIBLE);
                mLlNone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        //所有商品
                        bundle.putString("stype", "0");
                        goToActivity(ShopSclassActivity.class, "bundle", bundle);
                    }
                });
                mLvShopcar.setVisibility(View.GONE);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    private void doAdds() {
        totalPrice = 0;
        Iterator<ShopCarEntity> iterator = mChoose.iterator();
        while (iterator.hasNext()) {
            ShopCarEntity next = iterator.next();
            totalPrice = totalPrice + Double.parseDouble(next.getTotalprice());
        }
        Toast.makeText(mActivitySelf, "totalPrice:￥" + totalPrice + ",," + mChoose.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initDatas() {
        mTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelf.finish();
            }
        });
        mTitleCenter.setText("购物车");
        if (all.size() != 0) {
            mTitleRight.setText(right);
            mTitleRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (right.equals("全选")) {
                        right = "取消全选";
                        mChoose.clear();
                        Iterator<ShopCarEntity> iterator = all.iterator();
                        while (iterator.hasNext()) {
                            ShopCarEntity next = iterator.next();
                            mChoose.add(next);
                        }
                        for (int i = 0; i < mItemLvShopcarAdapter.cbs.size(); i++) {
                            mItemLvShopcarAdapter.cbs.get(i).setChecked(true);
                        }
                        mBtSubmit.setVisibility(View.VISIBLE);
                        mBtSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                doAdds();
                            }
                        });
                    } else {
                        right = "全选";
                        for (int i = 0; i < mItemLvShopcarAdapter.cbs.size(); i++) {
                            mItemLvShopcarAdapter.cbs.get(i).setChecked(false);
                        }
                        mBtSubmit.setVisibility(View.GONE);
                        mChoose.clear();
                        Toast.makeText(mActivitySelf, "all.size():" + all.size(), Toast.LENGTH_SHORT).show();
                    }
                    mTitleRight.setText(right);
                }
            });
        } else {
            mTitleRight.setText("");
        }
    }

    @Override
    public void initOthers() {

    }

    @Override
    public boolean isUseTitleBar() {
        return false;
    }
}