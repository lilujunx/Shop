package com.admin.exe.shop.ui.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.admin.exe.shop.R;
import com.admin.exe.shop.ui.activity.WebViewActivity;
import com.bumptech.glide.Glide;

public class ItemGvBrHomeAdapter extends BaseAdapter implements View.OnClickListener {

    private int[] mEntities;

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ItemGvBrHomeAdapter(Context context, int[] entities) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mEntities = entities;
    }

    @Override
    public int getCount() {
        return mEntities.length;
    }

    @Override
    public Integer getItem(int position) {
        return mEntities[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_gv_home_br, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((int) getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;
    }

    private void initializeViews(int entity, ViewHolder holder, int position) {
        //TODO implement
        holder.imgvLimit.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        Glide.with(mContext).load(entity).into(holder.imgvLimit);
        holder.tvLimit.setVisibility(View.GONE);
        holder.ll.setTag(position);
        holder.ll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra("position", position);
        mContext.startActivity(intent);

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
