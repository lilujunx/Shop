package com.admin.exe.shop.ui.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.admin.exe.shop.R;
import com.admin.exe.shop.entity.ShopListEntity;
import com.admin.exe.shop.ui.activity.BannerShopDetailActivity;
import com.admin.exe.shop.ui.views.OldPriceTV;
import com.admin.exe.shop.utils.FormatTime;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ItemLvShoplistAdapter extends BaseAdapter implements View.OnClickListener {

    private List<ShopListEntity.ShopsBean> mEntities;
    private List<TextView> mTextViews = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ItemLvShoplistAdapter(Context context, List<ShopListEntity.ShopsBean> entities) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mEntities = entities;
    }



    public void setEntities(List<ShopListEntity.ShopsBean> entities) {
        mEntities = entities;
        notifyDataSetChanged();
    }

    public void addEntity(List<ShopListEntity.ShopsBean> add){
        mEntities.addAll(add);
        notifyDataSetChanged();
    }

    public void resetTime() {
        for (int i = 0; i < mEntities.size(); i++) {
            ShopListEntity.ShopsBean shopsBean = mEntities.get(i);
            if (shopsBean.getLefttime() == 0 ) {
                continue;
            }
            shopsBean.setLefttime(shopsBean.getLefttime() - 1000);
        }
        for (TextView textView : mTextViews) {
            ShopListEntity.ShopsBean shopsBean = (ShopListEntity.ShopsBean) textView.getTag();
            textView.setText(FormatTime.formatLeftTime(shopsBean.getLefttime()));

        }

    }

    @Override
    public int getCount() {
        return mEntities.size();
    }

    @Override
    public ShopListEntity.ShopsBean getItem(int position) {
        return mEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_lv_shoplist, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((ShopListEntity.ShopsBean) getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;
    }

    private void initializeViews(ShopListEntity.ShopsBean entity, ViewHolder holder, int position) {
        //TODO implement
        Glide.with(mContext).load(entity.getSpic()).into(holder.imgvListLimitActivity);
        holder.tvTimeLimitActivity.setTag(entity);
        holder.tvTitleLimitActivity.setText(entity.getSname());
        String price = entity.getLprice();
        if ("".equals(price) || price == null) {
            price = entity.getSprice();

            holder.tvNewpriceLimitActivity.setText("￥"+price);
            holder.tvOldpriceLimitActivity.setVisibility(View.GONE);
            holder.xsqg.setText("售价:");

        }else {

            holder.tvNewpriceLimitActivity.setText("￥"+price);
            holder.tvOldpriceLimitActivity.setText("￥"+entity.getSprice());
            holder.tvOldpriceLimitActivity.setVisibility(View.VISIBLE);
            holder.xsqg.setVisibility(View.VISIBLE);
        }

        if(entity.getLefttime()==0){
            holder.tvTimeLimitActivity.setVisibility(View.GONE);
        }else{
            holder.tvTimeLimitActivity.setVisibility(View.VISIBLE);
        }
        holder.rlRoot.setTag(entity);
        holder.rlRoot.setOnClickListener(this);
        holder.mTvSalenum.setText("销量："+entity.getSalenum());
    }

    @Override
    public void onClick(View v) {
        ShopListEntity.ShopsBean entity= (ShopListEntity.ShopsBean) v.getTag();
        Intent intent=new Intent(mContext, BannerShopDetailActivity.class);
        intent.putExtra("sid",entity.getSid());
        mContext.startActivity(intent);
    }

    protected class ViewHolder {
        private RelativeLayout rlRoot;
        private ImageView imgvListLimitActivity;
        private TextView tvTitleLimitActivity;
        private OldPriceTV tvOldpriceLimitActivity;
        private TextView xsqg;
        private TextView tvNewpriceLimitActivity;
        private TextView tvTimeLimitActivity;
        private TextView mTvSalenum;


        public ViewHolder(View view) {
            rlRoot = (RelativeLayout) view.findViewById(R.id.rl_root);
            imgvListLimitActivity = (ImageView) view.findViewById(R.id.imgv_list_limit_activity);
            tvTitleLimitActivity = (TextView) view.findViewById(R.id.tv_title_limit_activity);
            tvOldpriceLimitActivity = (OldPriceTV) view.findViewById(R.id.tv_oldprice_limit_activity);
            xsqg = (TextView) view.findViewById(R.id.xsqg);
            tvNewpriceLimitActivity = (TextView) view.findViewById(R.id.tv_newprice_limit_activity);
            mTvSalenum = (TextView) view.findViewById(R.id.tv_salenum);

            tvTimeLimitActivity = (TextView) view.findViewById(R.id.tv_time_limit_activity);
            mTextViews.add(tvTimeLimitActivity);
        }
    }
}
