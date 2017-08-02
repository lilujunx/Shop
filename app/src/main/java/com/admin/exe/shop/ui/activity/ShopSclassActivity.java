package com.admin.exe.shop.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.admin.exe.shop.Constant;
import com.admin.exe.shop.R;
import com.admin.exe.shop.base.SCBaseActivity;
import com.admin.exe.shop.entity.ShopListEntity;
import com.admin.exe.shop.ui.adapter.ItemLvShoplistAdapter;
import com.admin.exe.shop.utils.IsLoadImage;
import com.admin.exe.shop.utils.NetCallback;
import com.admin.exe.shop.utils.NetForJson;
import com.admin.exe.shop.utils.ToastUtils;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShopSclassActivity extends SCBaseActivity implements OnLoadMoreListener, View.OnClickListener {

    private CheckBox mRbPrice, mRbEvalute, mRbSale, mRbStype;
    private SwipeToLoadLayout mLayoutListFragFile;
    private ListView mSwipeTarget;
    private String stype, search;
    private int mcount = 1;
    private boolean isFirst = true;
    private CheckBox[] cbs;
    private int pos = -1;
    private String[] strs = {"奶粉专区", "尿裤专区", "宝宝娱乐", "妈妈专区", "宝宝饮食", "宝宝用品"};

    private List<ShopListEntity.ShopsBean> mShops = new ArrayList<>();
    private ItemLvShoplistAdapter mItemLvShoplistAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mItemLvShoplistAdapter.resetTime();
            mHandler.sendEmptyMessageDelayed(1, 1000);

        }
    };

    @Override
    public int initRootLayout() {
        return R.layout.activity_shop_sclass;
    }

    @Override
    public void initViews() {

        mRbPrice = (CheckBox) findViewById(R.id.rb_price);
        mRbEvalute = (CheckBox) findViewById(R.id.rb_evalute);
        mRbSale = (CheckBox) findViewById(R.id.rb_sale);
        mRbStype = (CheckBox) findViewById(R.id.rb_stype);
        cbs = new CheckBox[]{mRbPrice, mRbEvalute, mRbSale, mRbStype};
        mLayoutListFragFile = (SwipeToLoadLayout) findViewById(R.id.layout_list_frag_file);
        mSwipeTarget = (ListView) findViewById(R.id.swipe_target);
        mLayoutListFragFile.setOnLoadMoreListener(this);
        mItemLvShoplistAdapter = new ItemLvShoplistAdapter(mActivitySelf, new ArrayList<ShopListEntity.ShopsBean>());
        mSwipeTarget.setAdapter(mItemLvShoplistAdapter);
        mSwipeTarget.setOnScrollListener(new IsLoadImage());
    }


    @Override
    public void initDatas() {
        for (int i = 0; i < cbs.length; i++) {
            cbs[i].setOnClickListener(this);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            //限时抢购----更多--->到此
            stype = bundle.getString("stype");
            pos = bundle.getInt("sclass");
            if (pos != -1) {
                doSelectByStype(pos);
            } else {
                doSelectByStype(-1);
            }
        }
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < cbs.length; i++) {
            cbs[i].setChecked(false);
        }
        CheckBox cb = (CheckBox) v;
        cb.setChecked(true);
        switch (cb.getId()) {
            case R.id.rb_price:
                //单价，升序
                Collections.sort(mShops, new Comparator<ShopListEntity.ShopsBean>() {
                    @Override
                    public int compare(ShopListEntity.ShopsBean t1, ShopListEntity.ShopsBean t2) {
                        if (t1.getLprice().equals("") || t2.getLprice().equals("")) {
                            if (Double.parseDouble(t2.getSprice()) > Double.parseDouble(t1.getSprice())) {
                                return -1;
                            }
                            if (Double.parseDouble(t2.getSprice()) < Double.parseDouble(t1.getSprice())) {
                                return 1;
                            }
                        } else {
                            if (Double.parseDouble(t2.getLprice()) > Double.parseDouble(t1.getLprice())) {
                                return -1;
                            }
                            if (Double.parseDouble(t2.getLprice()) < Double.parseDouble(t1.getLprice())) {
                                return 1;
                            }
                        }
                        return 0;

                    }
                });
                break;
            case R.id.rb_sale:
                //销量，降序
                Collections.sort(mShops, new Comparator<ShopListEntity.ShopsBean>() {
                    @Override
                    public int compare(ShopListEntity.ShopsBean t1, ShopListEntity.ShopsBean t2) {
                        return t1.getSname().compareTo(t2.getSname());
                    }
                });
                break;
            case R.id.rb_stype:
                //类型，降序
                Collections.sort(mShops, new Comparator<ShopListEntity.ShopsBean>() {
                    @Override
                    public int compare(ShopListEntity.ShopsBean t1, ShopListEntity.ShopsBean t2) {
                        return -t1.getSclass().compareTo(t2.getSclass());
                    }
                });
                break;
            case R.id.rb_evalute:
                Collections.sort(mShops, new Comparator<ShopListEntity.ShopsBean>() {
                    @Override
                    public int compare(ShopListEntity.ShopsBean t1, ShopListEntity.ShopsBean t2) {
                        if (t1.getLprice().equals("") || t2.getLprice().equals("")) {
                            if (Double.parseDouble(t2.getSprice()) > Double.parseDouble(t1.getSprice())) {
                                return -1;
                            }
                            if (Double.parseDouble(t2.getSprice()) < Double.parseDouble(t1.getSprice())) {
                                return 1;
                            }
                        } else {
                            if (Double.parseDouble(t2.getLprice()) > Double.parseDouble(t1.getLprice())) {
                                return -1;
                            }
                            if (Double.parseDouble(t2.getLprice()) < Double.parseDouble(t1.getLprice())) {
                                return 1;
                            }
                        }
                        return 0;

                    }
                });
                break;
        }
        mItemLvShoplistAdapter.setEntities(mShops);
    }

    @Override
    public void initOthers() {
        setTitleCenter("更多商品");
        setTitleLeft("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelf.finish();
            }
        });
    }

    @Override
    public void onLoadMore() {
        //搜索stype=1  或者 stype=0
        if (stype != null) {
            doSelectByStype(pos);
        }
    }

    public void doSelectByStype(int pos) {
        NetForJson netForJson = new NetForJson(Constant.URL_SHOPLIST, new ShopListCallback());
        netForJson.addParam("stype", stype);
        Log.e("xx", "stype:" + stype);
      //  Error:Error converting bytecode to dex:
//        Cause: com.android.dex.DexException: Multiple dex files define Landroid/support/v4/accessibilityservice/AccessibilityServiceInfoCompatIcs;
        netForJson.addParam("sclass", strs[pos]);
        netForJson.addParam("mcount", mcount);
        netForJson.execute();
    }


    class ShopListCallback extends NetCallback<ShopListEntity> {


        @Override
        public void onSuccess(ShopListEntity shopListEntity) {
            mLayoutListFragFile.setLoadingMore(false);
            Log.e("xx", shopListEntity.toString());

            if (shopListEntity.getMShops().size() == 0) {
                Toast.makeText(mActivitySelf, "暂无更多商品", Toast.LENGTH_SHORT).show();
            } else {
                mcount++;
                mShops.addAll(shopListEntity.getMShops());
                mItemLvShoplistAdapter.addEntity(shopListEntity.getMShops());
            }
            //只发一次消息就行
            if (isFirst) {
                mHandler.sendEmptyMessageDelayed(1, 1000);
                isFirst = false;
            }
        }

        @Override
        public void onError(Throwable throwable) {
            ToastUtils.getToastOnFailure(mActivitySelf);
            Log.e("xx", throwable.toString());
            mLayoutListFragFile.setLoadingMore(false);

        }

        @Override
        public void onFinished() {

        }
    }

    @Override
    public boolean isUseTitleBar() {
        return true;
    }


}
