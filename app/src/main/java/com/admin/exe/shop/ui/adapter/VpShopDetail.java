package com.admin.exe.shop.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.admin.exe.shop.R;
import com.admin.exe.shop.entity.ShopDetailEntity;
import com.bumptech.glide.Glide;
import com.example.library.adapter.SimplePagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */

public class VpShopDetail extends PagerAdapter{
    private Context mContext;
    private ShopDetailEntity mEntity;
    private LayoutInflater mLayoutInflater;

    public VpShopDetail(Context context, ShopDetailEntity entity) {
        mContext = context;
        mEntity = entity;
        mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 100000;
    }

    public void setVPNoEnd(ViewPager vp){
        vp.setCurrentItem(100000/2);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View itemView=mLayoutInflater.inflate(setLayouResID(),container,false);
        ImageView imgv = (ImageView) itemView.findViewById(R.id.imgv_vp_head);
        imgv.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Glide.with(mContext).load(mEntity.getSpic()).
                placeholder(R.drawable.product_loading).
                error(R.drawable.product_loading).dontAnimate().into(imgv);
        container.addView(itemView);
        return itemView;
    }

    private int setLayouResID() {
        return R.layout.item_vp_head;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
