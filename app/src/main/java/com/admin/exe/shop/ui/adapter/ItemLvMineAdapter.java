package com.admin.exe.shop.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.admin.exe.shop.Constant;
import com.admin.exe.shop.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class ItemLvMineAdapter extends BaseAdapter {

    private String[] mEntities;
   private View.OnClickListener mOnClickListener;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ItemLvMineAdapter(Context context, String[] entities) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mEntities = entities;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    public int getCount() {
        return mEntities.length;
    }

    @Override
    public String getItem(int position) {
        return mEntities[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_lv_mine, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((String) getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;
    }

    private void initializeViews(String entity, ViewHolder holder, int position) {
        //TODO implement
        holder.tvTitle.setText(entity);
        Glide.with(mContext).load(Constant.icons[position]).into(holder.imgvIcon);
        holder.llRoot.setTag(position);
        if(mOnClickListener!=null){
            holder.llRoot.setOnClickListener(mOnClickListener);
        }
    }

    protected class ViewHolder {
        private LinearLayout llRoot;
        private ImageView imgvIcon;
        private TextView tvTitle;

        public ViewHolder(View view) {
            llRoot = (LinearLayout) view.findViewById(R.id.ll_root);
            imgvIcon = (ImageView) view.findViewById(R.id.imgv_icon);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
        }
    }
}
