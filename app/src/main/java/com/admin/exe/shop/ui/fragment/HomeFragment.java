package com.admin.exe.shop.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.admin.exe.shop.Constant;
import com.admin.exe.shop.R;
import com.admin.exe.shop.base.SCBaseFragment;
import com.admin.exe.shop.entity.HomeEntity;
import com.admin.exe.shop.ui.activity.CommonLoginActivity;
import com.admin.exe.shop.ui.activity.ShopListActivity;
import com.admin.exe.shop.ui.activity.ShopSclassActivity;
import com.admin.exe.shop.ui.adapter.ItemGvBrHomeAdapter;
import com.admin.exe.shop.ui.adapter.ItemGvHomeLimitAdapter;
import com.admin.exe.shop.ui.adapter.ItemLvHomeStarmostAdapter;
import com.admin.exe.shop.ui.adapter.VpHeadAdapter;
import com.admin.exe.shop.utils.DensityUtil;
import com.admin.exe.shop.utils.NetCallback;
import com.admin.exe.shop.utils.NetForJson;
import com.admin.exe.shop.utils.ToastUtils;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.example.library.utils.SharedPrefrencesUtil;
import com.example.library.view.MeasureGridView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends SCBaseFragment implements OnRefreshListener, View.OnClickListener {
    private SwipeToLoadLayout mLayoutListFragFile;
    private ListView mSwipeTarget;
    private VpHeadAdapter mVpHeadAdapter;
    private ViewPager mViewPager;
    private boolean mCanBannerPlay;
    private Timer mTimer;
    private int mCurrentfirstVisibleItem;
    private SparseArray recordSp = new SparseArray(0);//设置容器大小，默认是10
    private float mIsShowTitleHeight;
    private LinearLayout mLl;
    private MeasureGridView mGvHome;
    private ItemGvHomeLimitAdapter mItemGvHomeLimitAdapter;
    private ItemGvBrHomeAdapter mItemGvBrHomeAdapter;
    private ItemLvHomeStarmostAdapter mItemLvHomeStarmostAdapter;
    private RelativeLayout mRlTitle;
    private ImageView mTitleLeft;
    private TextView mTitleCenter;
    private RadioGroup mRgVpHome;
    private MeasureGridView mGvBr;
    private HorizontalScrollView hor;
    private LinearLayout mLlSclass;
    private LinearLayout mLlSclass0, mLlSclass1, mLlSclass2, mLlSclass3, mLlSclass4, mLlSclass5;
    private ImageView mImgvSclass0, mImgvSclass1, mImgvSclass2, mImgvSclass3, mImgvSclass4, mImgvSclass5;
    private TextView mTvSclass0, mTvSclass1, mTvSclass2, mTvSclass3, mTvSclass4, mTvSclass5;
    private LinearLayout[] lls;
    private ImageView[] imgvs;
    private TextView[] tvs;
    private int[] imgs = {R.drawable.sclass_naifen, R.drawable.sclass_niaoku, R.drawable.sclass_yule,
            R.drawable.sclass_mama, R.drawable.sclass_yinshi, R.drawable.sclass_yongpin,};
    private String[] strs = {"奶粉专区", "尿裤专区", "宝宝娱乐", "妈妈专区", "宝宝饮食", "宝宝用品"};

    @Override
    public int initRootLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void initViews() {
        Constant.isLogin = SharedPrefrencesUtil.getData(mActivitySelf, "shop", "isLogin", false);
        mIsShowTitleHeight = DensityUtil.dip2px(mActivitySelf, 70);
        View pagerView = LayoutInflater.from(mActivitySelf).inflate(R.layout.layout_vp_head, mSwipeTarget, false);
        mViewPager = (ViewPager) pagerView.findViewById(R.id.vp_head);
        mRgVpHome = (RadioGroup) pagerView.findViewById(R.id.rg_vp_home);
        mLl = (LinearLayout) pagerView.findViewById(R.id.ll);
        mGvBr = (MeasureGridView) pagerView.findViewById(R.id.gv_br);
        mGvHome = (MeasureGridView) pagerView.findViewById(R.id.gv_home);
        hor = (HorizontalScrollView) pagerView.findViewById(R.id.hor);
        mLlSclass0 = (LinearLayout) pagerView.findViewById(R.id.ll_sclass0);
        mImgvSclass0 = (ImageView) pagerView.findViewById(R.id.imgv_sclass0);
        mTvSclass0 = (TextView) pagerView.findViewById(R.id.tv_sclass0);
        mLlSclass1 = (LinearLayout) pagerView.findViewById(R.id.ll_sclass1);
        mImgvSclass1 = (ImageView) pagerView.findViewById(R.id.imgv_sclass1);
        mTvSclass1 = (TextView) pagerView.findViewById(R.id.tv_sclass1);
        mLlSclass2 = (LinearLayout) pagerView.findViewById(R.id.ll_sclass2);
        mImgvSclass2 = (ImageView) pagerView.findViewById(R.id.imgv_sclass2);
        mTvSclass2 = (TextView) pagerView.findViewById(R.id.tv_sclass2);
        mLlSclass = (LinearLayout) pagerView.findViewById(R.id.ll_sclass);
        mLlSclass3 = (LinearLayout) pagerView.findViewById(R.id.ll_sclass3);
        mImgvSclass3 = (ImageView) pagerView.findViewById(R.id.imgv_sclass3);
        mTvSclass3 = (TextView) pagerView.findViewById(R.id.tv_sclass3);
        mLlSclass4 = (LinearLayout) pagerView.findViewById(R.id.ll_sclass4);
        mImgvSclass4 = (ImageView) pagerView.findViewById(R.id.imgv_sclass4);
        mTvSclass4 = (TextView) pagerView.findViewById(R.id.tv_sclass4);
        mLlSclass5 = (LinearLayout) pagerView.findViewById(R.id.ll_sclass5);
        mImgvSclass5 = (ImageView) pagerView.findViewById(R.id.imgv_sclass5);
        mTvSclass5 = (TextView) pagerView.findViewById(R.id.tv_sclass5);
        lls = new LinearLayout[]{mLlSclass0, mLlSclass1, mLlSclass2, mLlSclass3, mLlSclass4, mLlSclass5};
        imgvs = new ImageView[]{mImgvSclass0, mImgvSclass1, mImgvSclass2, mImgvSclass3, mImgvSclass4, mImgvSclass5};
        tvs = new TextView[]{mTvSclass0, mTvSclass1, mTvSclass2, mTvSclass3, mTvSclass4, mTvSclass5};
        mLayoutListFragFile = (SwipeToLoadLayout) findViewById(R.id.layout_list_frag_file);
        mSwipeTarget = (ListView) findViewById(R.id.swipe_target);
        mRlTitle = (RelativeLayout) findViewById(R.id.rl_title);
        mTitleLeft = (ImageView) findViewById(R.id.title_left);
        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mSwipeTarget.addHeaderView(pagerView);
        mLayoutListFragFile.setOnRefreshListener(this);
        mLayoutListFragFile.setRefreshing(true);
        mRlTitle.setVisibility(View.INVISIBLE);
        mItemGvBrHomeAdapter = new ItemGvBrHomeAdapter(mActivitySelf, Constant.brs);
        mGvBr.setAdapter(mItemGvBrHomeAdapter);
    }

    @Override
    public void initDatas() {
        mLlSclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Constant.isLogin) {
                    Bundle bundle = new Bundle();
                    bundle.putString("source", "mine");
                    goToActivity(CommonLoginActivity.class, "from", bundle);

                    return;
                }
                Bundle bundle = new Bundle();
                //所有商品
                bundle.putString("stype", "0");
                goToActivity(ShopSclassActivity.class, "bundle", bundle);
            }
        });
        for (int i = 0; i < imgvs.length; i++) {
            Glide.with(mActivitySelf).load(imgs[i]).into(imgvs[i]);
            tvs[i].setText(strs[i]);
            lls[i].setTag(i);
            lls[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!Constant.isLogin) {
                        Bundle bundle = new Bundle();
                        bundle.putString("source", "mine");
                        goToActivity(CommonLoginActivity.class, "from", bundle);
                        return;
                    }
                    int pos = (int) v.getTag();
                    Bundle bundle = new Bundle();
                    //分类
                    bundle.putString("stype", "2");
                    bundle.putInt("sclass", pos);
                    goToActivity(ShopSclassActivity.class, "bundle", bundle);

                }
            });
        }

        mTitleLeft.setVisibility(View.GONE);
        mLl.setOnClickListener(this);
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
                            mTitleCenter.setText("妈咪乐");
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

    public void doNet() {
        NetForJson netForJson = new NetForJson(Constant.URL_HOME, new HomeCallback());
        netForJson.execute();
    }

    @Override
    public void onRefresh() {
        doNet();
    }

    @Override
    public void onClick(View v) {
        if (!Constant.isLogin) {
            Bundle bundle = new Bundle();
            bundle.putString("source", "mine");
            goToActivity(CommonLoginActivity.class, "from", bundle);
            return;
        }
        Bundle bundle = new Bundle();
        //限时抢购

        bundle.putString("stype", "1");
        goToActivity(ShopListActivity.class, "bundle", bundle);
    }


    class HomeCallback extends NetCallback<HomeEntity> {

        @Override
        public void onSuccess(HomeEntity homeEntity) {
            mLayoutListFragFile.setRefreshing(false);
            initVp(homeEntity);
            initGv(homeEntity);
            initLv(homeEntity);
        }


        @Override
        public void onError(Throwable throwable) {
            mLayoutListFragFile.setRefreshing(false);
            ToastUtils.getToastOnFailure(mActivitySelf);
            Log.e("xx", throwable.toString());
        }

        @Override
        public void onFinished() {

        }
    }

    private void initGv(HomeEntity homeEntity) {
        mItemGvHomeLimitAdapter = new ItemGvHomeLimitAdapter(mActivitySelf, homeEntity.getLimitBuy());
        mGvHome.setAdapter(mItemGvHomeLimitAdapter);
    }

    private void initVp(HomeEntity homeEntity) {
        List<HomeEntity.HomeBannerBean> homeBanner = homeEntity.getHomeBanner();
        mVpHeadAdapter = new VpHeadAdapter(homeBanner, mActivitySelf);
        mViewPager.setAdapter(mVpHeadAdapter);
        mVpHeadAdapter.setVPNoEnd(mViewPager);
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mActivitySelf.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mCanBannerPlay) {
                            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                        }
                    }
                });
            }
        }, 1000, Constant.EVERY);
        ((RadioButton) mRgVpHome.getChildAt(0)).setChecked(true);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int realPs = position % 5;
                ((RadioButton) mRgVpHome.getChildAt(realPs)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mCanBannerPlay = state == ViewPager.SCROLL_STATE_IDLE;
            }
        });
    }

    private void initLv(HomeEntity homeEntity) {
        mItemLvHomeStarmostAdapter = new ItemLvHomeStarmostAdapter(mActivitySelf, homeEntity.getStarmost());
        mSwipeTarget.setAdapter(mItemLvHomeStarmostAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    private class ItemRecod {
        int height = 0;
        int top = 0;
    }
}
