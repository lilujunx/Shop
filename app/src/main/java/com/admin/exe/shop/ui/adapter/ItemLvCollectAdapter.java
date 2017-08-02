package com.admin.exe.shop.ui.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.admin.exe.shop.R;
import com.admin.exe.shop.entity.CollectEntity;
import com.admin.exe.shop.ui.activity.BannerShopDetailActivity;
import com.bumptech.glide.Glide;
import com.example.library.utils.DateUtils;

import java.util.Date;
import java.util.List;

public class ItemLvCollectAdapter extends BaseAdapter {

    private List<CollectEntity.Collect> mEntities;
    private boolean isShow = false;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener;

    public ItemLvCollectAdapter(Context context, List<CollectEntity.Collect> entities) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mEntities = entities;
    }


    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListener = onCheckedChangeListener;
    }

    public void setShow(boolean show) {
        isShow = show;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mEntities.size();
    }

    @Override
    public CollectEntity.Collect getItem(int position) {
        return mEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_lv_collect, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((CollectEntity.Collect) getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;
    }

    private void initializeViews(final CollectEntity.Collect entity, ViewHolder holder, int position) {
        //TODO implement
        if (isShow) {
            holder.llCb.setVisibility(View.VISIBLE);
            holder.cb.setTag(entity.getCid());
            if (mOnCheckedChangeListener != null) {
                holder.cb.setOnCheckedChangeListener(mOnCheckedChangeListener);
            }
        } else {
            holder.llCb.setVisibility(View.GONE);
        }
        holder.llRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(mContext, BannerShopDetailActivity.class);
                inte.putExtra("sid", entity.getSid());
                mContext.startActivity(inte);
            }
        });

        Glide.with(mContext).load(entity.getSpic()).into(holder.imgvHead);
        holder.tvName.setText(entity.getSname());
        holder.tvPrice.setText("￥"+entity.getSprice());
        holder.tvTime.setText(DateUtils.getDateString("",entity.getCtime()));
    }

    protected class ViewHolder {
        private LinearLayout llRoot;
        private ImageView imgvHead;
        private TextView tvName;
        private TextView tvPrice;
        private TextView tvTime;
        private LinearLayout llCb;
        private CheckBox cb;


        public ViewHolder(View view) {
            llRoot = (LinearLayout) view.findViewById(R.id.ll_root);
            imgvHead = (ImageView) view.findViewById(R.id.imgv_head);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
            tvTime = (TextView) view.findViewById(R.id.tv_time);
            llCb = (LinearLayout) view.findViewById(R.id.ll_cb);
            cb = (CheckBox) view.findViewById(R.id.cb);

        }
    }
}
