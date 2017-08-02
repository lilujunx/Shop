package com.admin.exe.shop.ui.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.admin.exe.shop.Constant;
import com.admin.exe.shop.R;
import com.admin.exe.shop.entity.HomeEntity;
import com.admin.exe.shop.ui.activity.BannerShopDetailActivity;
import com.admin.exe.shop.ui.activity.CommonLoginActivity;
import com.bumptech.glide.Glide;
import com.example.library.utils.SharedPrefrencesUtil;

import java.util.List;

public class ItemLvHomeStarmostAdapter extends BaseAdapter {

    private List<HomeEntity.StarmostBean> mEntities;

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ItemLvHomeStarmostAdapter(Context context, List<HomeEntity.StarmostBean> entities) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mEntities = entities;
        Constant.isLogin = SharedPrefrencesUtil.getData(context, "shop", "isLogin", false);
    }

    @Override
    public int getCount() {
        return mEntities.size();
    }

    @Override
    public HomeEntity.StarmostBean getItem(int position) {
        return mEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_lv_home_starmost, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((HomeEntity.StarmostBean) getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;
    }

    private void initializeViews(final HomeEntity.StarmostBean entity, ViewHolder holder, int position) {
        //TODO implement
        Glide.with(mContext).load(entity.getPic()).into(holder.imgvStar);
        holder.tvTitle.setText(entity.getSname());
        int i = Integer.parseInt(entity.getCount());
        holder.tvStar.setText("好评："+(Constant.random.nextInt(100)+3)+"人");
        holder.tvSunm.setText("月销量:"+(Constant.random.nextInt(10)+3)*10+"笔");
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Constant.isLogin) {
                    Intent intent=new Intent(mContext,CommonLoginActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("source", "mine");
                    intent.putExtra("from",bundle);
                    mContext.startActivity(intent);
                    return;
                }
                Intent intent = new Intent(mContext, BannerShopDetailActivity.class);
                intent.putExtra("sid", entity.getSid());
                mContext.startActivity(intent);
            }
        });
    }

    protected class ViewHolder {
        private ImageView imgvStar;
        private TextView tvTitle;
        private TextView tvStar;
        private TextView tvSunm;
        private LinearLayout ll;


        public ViewHolder(View view) {
            imgvStar = (ImageView) view.findViewById(R.id.imgv_star);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvStar = (TextView) view.findViewById(R.id.tv_star);
            tvSunm = (TextView) view.findViewById(R.id.tv_sunm);
            ll = (LinearLayout) view.findViewById(R.id.ll);

        }
    }
}
