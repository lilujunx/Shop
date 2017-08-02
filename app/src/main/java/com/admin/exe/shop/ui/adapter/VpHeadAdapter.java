package com.admin.exe.shop.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.admin.exe.shop.R;
import com.admin.exe.shop.entity.HomeEntity;
import com.admin.exe.shop.ui.activity.BannerShopDetailActivity;
import com.bumptech.glide.Glide;
import com.example.library.adapter.SimplePagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */

public class VpHeadAdapter extends SimplePagerAdapter<HomeEntity.HomeBannerBean> {

    public VpHeadAdapter(List<HomeEntity.HomeBannerBean> entities, Context context) {
        super(entities, context);
    }

    @Override
    public int setLayouResID() {
        return R.layout.item_vp_head;
    }

    @Override
    public void initView(View itemView, ViewGroup container, int position, final HomeEntity.HomeBannerBean entity) {
        ImageView imgv = (ImageView) itemView.findViewById(R.id.imgv_vp_head);
        Glide.with(mContext).load(entity.getPic()).
                placeholder(R.drawable.product_loading).dontAnimate().into(imgv);
        imgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BannerShopDetailActivity.class);
                intent.putExtra("sid", entity.getSid());
                mContext.startActivity(intent);
            }
        });
    }


}
