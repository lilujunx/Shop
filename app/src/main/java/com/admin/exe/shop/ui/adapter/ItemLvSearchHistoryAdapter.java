package com.admin.exe.shop.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.admin.exe.shop.R;

import java.util.List;

public class ItemLvSearchHistoryAdapter extends BaseAdapter {

    private List<String> mEntities;

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private View.OnClickListener mOnClickListener;

    public ItemLvSearchHistoryAdapter(Context context, List<String> entities) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mEntities = entities;
    }

    public void setEntities(List<String> entities) {
        mEntities = entities;
        notifyDataSetChanged();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }
//Error:Error converting bytecode to dex:
   // Cause: com.android.dex.DexException: Multiple dex files define Landroid/support/v4/accessibilityservice/AccessibilityServiceInfoCompatIcs;
    @Override
    public int getCount() {
        return mEntities.size();
    }

    @Override
    public String getItem(int position) {
        return mEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_lv_search_history, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((String) getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;
    }

    private void initializeViews(String entity, ViewHolder holder, int position) {
        //TODO implement
        holder.llRoot.setTag(position);
        if (mOnClickListener != null) {
            holder.llRoot.setOnClickListener(mOnClickListener);
        }
        holder.tvHistory.setText(entity);
    }

    protected class ViewHolder {
        private LinearLayout llRoot;
        private TextView tvHistory;

        public ViewHolder(View view) {
            llRoot = (LinearLayout) view.findViewById(R.id.ll_root);
            tvHistory = (TextView) view.findViewById(R.id.tv_history);
        }
    }
}
