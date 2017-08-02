package com.admin.exe.shop.ui.activity;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.exe.shop.Constant;
import com.admin.exe.shop.R;
import com.admin.exe.shop.base.SCBaseActivity;
import com.admin.exe.shop.entity.CollectEntity;
import com.admin.exe.shop.entity.EvaluteEntity;
import com.admin.exe.shop.entity.ShopDetailEntity;
import com.admin.exe.shop.ui.adapter.ItemLvEvaluteAdapter;
import com.admin.exe.shop.ui.adapter.VpShopDetail;
import com.admin.exe.shop.ui.views.OldPriceTV;
import com.admin.exe.shop.ui.views.PopBuy;
import com.admin.exe.shop.utils.DensityUtil;
import com.admin.exe.shop.utils.LogerUtils;
import com.admin.exe.shop.utils.NetCallback;
import com.admin.exe.shop.utils.NetForJson;
import com.admin.exe.shop.utils.ToastUtils;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class BannerShopDetailActivity extends SCBaseActivity implements OnLoadMoreListener, View.OnClickListener {
    private SwipeToLoadLayout mLayoutListFragFile;
    private ListView mSwipeTarget;
    private ViewPager mVpHead;
    private TextView mTvNone, mTvTitle, mTvNewPrice, mTitleCenter;
    private LinearLayout mLlShare;
    private LinearLayout mLlCollent, mLlAddshopcar, mLlBuy;
    private OldPriceTV mTvOldPrice;
    private String sid;
    private SparseArray recordSp = new SparseArray(0);//设置容器大小，默认是10
    private float mIsShowTitleHeight;
    private int mCurrentfirstVisibleItem;
    private RelativeLayout mRlTitle;
    private ImageView mTitleLeft;
    private ItemLvEvaluteAdapter mItemLvEvaluteAdapter;
    private VpShopDetail mVpShopDetail;
    private int mCount = 1, num = 0;
    private Timer mTimer;
    private boolean mCanBannerPlay = false;
    private ImageView mImgvShare;
    private ShopDetailEntity mShopDetailEntity;
    private PopBuy mPopBuy, mPopAdd;
    private String uid, sprice, spic;
    private CheckBox mCb;


    @Override
    public int initRootLayout() {
        return R.layout.activity_banner_shop_detail;
    }


    @Override
    protected void onResume() {
        super.onResume();
        mTimer = new Timer();
    }

    @Override
    public void initViews() {
        sid = getIntent().getStringExtra("sid");
        mIsShowTitleHeight = DensityUtil.dip2px(mActivitySelf, 70);
        View pagerView = LayoutInflater.from(mActivitySelf).inflate(R.layout.layout_shop_detail_head, mSwipeTarget, false);
        mVpHead = (ViewPager) pagerView.findViewById(R.id.vp_head);
        mTvTitle = (TextView) pagerView.findViewById(R.id.tv_title);
        mLlShare = (LinearLayout) pagerView.findViewById(R.id.ll_share);
        mImgvShare = (ImageView) pagerView.findViewById(R.id.imgv_share);
        mTvOldPrice = (OldPriceTV) pagerView.findViewById(R.id.tv_old_price);
        mTvNone = (TextView) pagerView.findViewById(R.id.tv_none);
        mTvNewPrice = (TextView) pagerView.findViewById(R.id.tv_new_price);
        mLayoutListFragFile = (SwipeToLoadLayout) findViewById(R.id.layout_list_frag_file);
        mLayoutListFragFile.setOnLoadMoreListener(this);
        mSwipeTarget = (ListView) findViewById(R.id.swipe_target);
        mSwipeTarget.addHeaderView(pagerView);
        mLlCollent = (LinearLayout) findViewById(R.id.ll_collent);
        mLlAddshopcar = (LinearLayout) findViewById(R.id.ll_addshopcar);
        mLlBuy = (LinearLayout) findViewById(R.id.ll_buy);
        mRlTitle = (RelativeLayout) findViewById(R.id.rl_title);
        mRlTitle.setVisibility(View.GONE);
        mTitleLeft = (ImageView) findViewById(R.id.title_left);
        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mCb = (CheckBox) findViewById(R.id.cb);

        mItemLvEvaluteAdapter = new ItemLvEvaluteAdapter(mActivitySelf, new ArrayList<EvaluteEntity.EvaluteEntitiesBean>());
        mSwipeTarget.setAdapter(mItemLvEvaluteAdapter);
        Glide.with(mActivitySelf).load(R.drawable.share).into(mImgvShare);
        mImgvShare.setColorFilter(mActivitySelf.getResources().getColor(R.color.app_style));
        uid = LogerUtils.getUid(mActivitySelf);
    }

    @Override
    public void initDatas() {
        doNet();
        getEvalute();
        mLlShare.setOnClickListener(this);
        mLlCollent.setOnClickListener(this);
        mLlAddshopcar.setOnClickListener(this);
        mLlBuy.setOnClickListener(this);
        mLlAddshopcar.setOnClickListener(this);
        mTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelf.finish();
            }
        });
        mSwipeTarget.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mCurrentfirstVisibleItem = firstVisibleItem;

                View firstView = view.getChildAt(0);//获取当前最顶部的item
                if (firstView != null) {
                    ItemRecod itemRecord = (ItemRecod) recordSp.get(firstVisibleItem);
                    if (itemRecord == null) {
                        itemRecord = new ItemRecod();
                    }
                    itemRecord.height = firstView.getHeight();//获取当前最顶部Item的高度
                    itemRecord.top = firstView.getTop();//获取对应item距离顶部的距离
                    recordSp.append(firstVisibleItem, itemRecord);//添加键值对设置值

                    int ScrollY = getScrollY();
                    if (ScrollY <= 0) {
                        mRlTitle.setBackgroundColor(Color.argb(0, 255, 255, 255));

                    } else if (ScrollY > 0 && ScrollY <= mIsShowTitleHeight) {
                        float scale = (float) ScrollY / mIsShowTitleHeight;
                        float alpha = (255 * scale);
                        //FF8200
                        mRlTitle.setBackgroundColor(Color.argb((int) alpha, 255, 130, 00));
                        if (alpha >= 10) {
                            mRlTitle.setVisibility(View.VISIBLE);
                        } else {
                            mRlTitle.setVisibility(View.INVISIBLE);
                        }
                        if (alpha >= 100) {
                            mTitleCenter.setVisibility(View.VISIBLE);
                            mTitleCenter.setText("商品详情");
                        } else {
                            mTitleCenter.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        //滑动距离大于mIsShowTitleHeight就设置为不透明
                        mRlTitle.setBackgroundColor(Color.argb(255, 255, 130, 00));
                    }
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_share:
                //分享
                showShare();
                break;
            case R.id.ll_collent:
                //收藏
                doCollect();
                break;

            case R.id.ll_buy:
                mPopBuy.showAtLocation(mSwipeTarget, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.ll_addshopcar:
                mPopAdd.showAtLocation(mSwipeTarget, Gravity.BOTTOM, 0, 0);
                break;
        }

    }


    private void showShare() {

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        if(mShopDetailEntity!=null) {
            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
            oks.setTitle(mShopDetailEntity.getSname());
            // titleUrl是标题的网络链接，QQ和QQ空间等使用
            oks.setTitleUrl("http://sharesdk.cn");
            // text是分享文本，所有平台都需要这个字段
            oks.setText("我好喜欢这个商品哦");
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl("http://sharesdk.cn");
            // comment是我对这条分享的评论，仅在人人网和QQ空间使用

            // site是分享此内容的网站名称，仅在QQ空间使用
            oks.setSite(getString(R.string.app_name));
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
            oks.show(this);
        }
    }

    @Override
    public void initOthers() {

    }

    private int getScrollY() {
        int height = 0;
        for (int i = 0; i < mCurrentfirstVisibleItem; i++) {
            ItemRecod itemRecod = (ItemRecod) recordSp.get(i);
            if (itemRecod != null) {
                height += itemRecod.height;
            }
        }
        ItemRecod itemRecod = (ItemRecod) recordSp.get(mCurrentfirstVisibleItem);
        if (null == itemRecod) {
            itemRecod = new ItemRecod();
        }
        return height - itemRecod.top;
    }

    //获取商品详情
    public void doNet() {
        NetForJson netForJson = new NetForJson(Constant.URL_SHOPDETAIL, new ShopCallback());
        netForJson.addParam("type", "banner");
        netForJson.addParam("data", sid);
        netForJson.execute();
    }

    //获取评价
    public void getEvalute() {
        NetForJson netForJson = new NetForJson(Constant.URL_EVALUTE, new EvaCallback());
        netForJson.addParam("sid", sid);
        netForJson.addParam("mcount", mCount);
        netForJson.execute();
    }

    //收藏
    private void doCollect() {
        if (!Constant.isLogin) {
            goToActivity(CommonLoginActivity.class);
        }
        if (sprice == null || spic == null) {
            ToastUtils.getToastOnFailure(mActivitySelf);
            return;
        }
        NetForJson netForJson = new NetForJson(Constant.URL_COLLECT, new CollectCallback());
        netForJson.addParam("stype", "add");
        netForJson.addParam("uid", uid);
        netForJson.addParam("sid", sid);
        netForJson.addParam("sprice", sprice);
        netForJson.addParam("spic", spic);
        netForJson.addParam("ctime", String.valueOf(System.currentTimeMillis()));


        netForJson.execute();
    }

    @Override
    public void onLoadMore() {
        mCount++;
        getEvalute();
    }

    //获取商品详情
    class ShopCallback extends NetCallback<ShopDetailEntity> {
        @Override
        public void onSuccess(ShopDetailEntity shopDetailEntity) {
            sprice = shopDetailEntity.getLprice().equals("") ? shopDetailEntity.getSprice() : shopDetailEntity.getLprice();
            spic = shopDetailEntity.getSpic();
            mTvTitle.setText(shopDetailEntity.getSname());
            mShopDetailEntity = shopDetailEntity;
            if (mPopBuy == null ) {
                mPopBuy = new PopBuy(mActivitySelf, mShopDetailEntity, "buy");
                mPopAdd = new PopBuy(mActivitySelf, mShopDetailEntity, "add");

            }
            String newPrice = shopDetailEntity.getLprice();
            if (newPrice == null || "".equals(newPrice)) {
                mTvOldPrice.setVisibility(View.INVISIBLE);
                newPrice = shopDetailEntity.getSprice();
            } else {
                mTvOldPrice.setText("￥" + shopDetailEntity.getSprice());
            }
            mTvNewPrice.setText("￥" + newPrice);

            Log.e("xx", shopDetailEntity.toString());
            initVp(shopDetailEntity);
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

    private void initVp(ShopDetailEntity shopDetailEntity) {

        mVpShopDetail = new VpShopDetail(mActivitySelf, shopDetailEntity);
        mVpHead.setAdapter(mVpShopDetail);

        mVpShopDetail.setVPNoEnd(mVpHead);
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mActivitySelf.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mCanBannerPlay) {
                            mVpHead.setCurrentItem(mVpHead.getCurrentItem() + 1);
                        }
                    }
                });
            }
        }, 1000, 5000);
        mVpHead.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int realPs = position % 3;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mCanBannerPlay = state == ViewPager.SCROLL_STATE_IDLE;
            }
        });


    }

    //获取评价
    class EvaCallback extends NetCallback<EvaluteEntity> {

        @Override
        public void onSuccess(EvaluteEntity evaluteEntity) {
            Log.e("xx", evaluteEntity.toString());
            mLayoutListFragFile.setLoadingMore(false);
            if (evaluteEntity.getEvaluteEntities().size() > 0) {
                mTvNone.setVisibility(View.GONE);
                mSwipeTarget.setVisibility(View.VISIBLE);
                mItemLvEvaluteAdapter.addEntity(evaluteEntity.getEvaluteEntities());
            } else {
                if (mCount != 1) {
                    Toast.makeText(mActivitySelf, "没有更多评论了", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onError(Throwable throwable) {
            mLayoutListFragFile.setLoadingMore(false);
            ToastUtils.getToastOnFailure(mActivitySelf);
            Log.e("xx", throwable.toString());
        }

        @Override
        public void onFinished() {
            mLayoutListFragFile.setLoadingMore(false);
        }
    }

    //添加收藏
    class CollectCallback extends NetCallback<CollectEntity> {

        @Override
        public void onSuccess(CollectEntity collectEntity) {
            if (collectEntity.getSuccess().equals("1")) {

                Toast.makeText(mActivitySelf, "收藏成功", Toast.LENGTH_SHORT).show();
            }
            if (collectEntity.getSuccess().equals("2")) {
                Toast.makeText(mActivitySelf, "您已收藏过", Toast.LENGTH_SHORT).show();
            }
            mCb.setChecked(true);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }

    private class ItemRecod {
        int height = 0;
        int top = 0;
    }
}
