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
import android.widget.Toast;

import com.admin.exe.shop.Constant;
import com.admin.exe.shop.R;
import com.admin.exe.shop.entity.HomeEntity;
import com.admin.exe.shop.ui.activity.BannerShopDetailActivity;
import com.admin.exe.shop.ui.activity.CommonLoginActivity;
import com.admin.exe.shop.utils.GlideUtils;
import com.example.library.utils.SharedPrefrencesUtil;

import java.util.List;

public class ItemGvHomeLimitAdapter extends BaseAdapter implements View.OnClickListener {

    private List<HomeEntity.LimitBuyBean> mEntities;

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ItemGvHomeLimitAdapter(Context context, List<HomeEntity.LimitBuyBean> entities) {
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
    public HomeEntity.LimitBuyBean getItem(int position) {
        return mEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_gv_home_limit, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((HomeEntity.LimitBuyBean) getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;
    }

    private void initializeViews(final HomeEntity.LimitBuyBean entity, ViewHolder holder, int position) {
        //TODO implement
        GlideUtils.getCircular(mContext,entity.getPic(),holder.imgvLimit);
        holder.tvLimit.setText(entity.getTitle());
        holder.ll.setTag(entity.getSid());
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

    @Override
    public void onClick(View v) {


    }

    protected class ViewHolder {
        private ImageView imgvLimit;
        private TextView tvLimit;
        private LinearLayout ll;



        public ViewHolder(View view) {
            imgvLimit = (ImageView) view.findViewById(R.id.imgv_limit);
            tvLimit = (TextView) view.findViewById(R.id.tv_limit);
            ll = (LinearLayout) view.findViewById(R.id.ll);
        }
    }
}
