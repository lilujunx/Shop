package com.admin.exe.shop.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.admin.exe.shop.R;
import com.admin.exe.shop.entity.EvaluteEntity;
import com.admin.exe.shop.ui.views.StarBarView;
import com.admin.exe.shop.utils.GlideUtils;
import com.example.library.utils.DateUtils;

import java.util.List;

public class ItemLvEvaluteAdapter extends BaseAdapter {

    private List<EvaluteEntity.EvaluteEntitiesBean> mEntities;

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ItemLvEvaluteAdapter(Context context, List<EvaluteEntity.EvaluteEntitiesBean> entities) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mEntities = entities;
    }

    public void addEntity(List<EvaluteEntity.EvaluteEntitiesBean> add){
        mEntities.addAll(add);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mEntities.size();
    }

    @Override
    public EvaluteEntity.EvaluteEntitiesBean getItem(int position) {
        return mEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_lv_evalute, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((EvaluteEntity.EvaluteEntitiesBean) getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;
    }

    private void initializeViews(EvaluteEntity.EvaluteEntitiesBean entity, ViewHolder holder, int position) {
        //TODO implement
        if(mEntities.size()==0){
            holder.tvName.setText("暂无买家评价");
        }else {
            if (!entity.getUhead().equals("")) {
                GlideUtils.getCircular(mContext, entity.getUhead(), holder.imgvHead);
            } else {
                GlideUtils.getCircular(mContext, R.drawable.user_head, holder.imgvHead);
            }
            holder.sbvStarbar.setStarRating(Float.parseFloat(entity.getStar()));
            holder.tvDetail.setText(entity.getDetatil());
            holder.tvName.setText(entity.getUname());
            holder.tvTime.setText(DateUtils.getDateString("yyyy-MM-dd HH:mm", entity.getTime()));
        }

    }

    protected class ViewHolder {
        private LinearLayout ll;
        private ImageView imgvHead;
        private TextView tvName;
        private StarBarView sbvStarbar;
        private TextView tvTime;
        private TextView tvDetail;
        private View view;

        public ViewHolder(View view) {
            ll = (LinearLayout) view.findViewById(R.id.ll);
            imgvHead = (ImageView) view.findViewById(R.id.imgv_head);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            sbvStarbar = (StarBarView) view.findViewById(R.id.sbv_starbar);
            tvTime = (TextView) view.findViewById(R.id.tv_time);
            tvDetail = (TextView) view.findViewById(R.id.tv_detail);
            view = (View) view.findViewById(R.id.view);
        }
    }
}
