package com.admin.exe.shop.ui.adapter;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.exe.shop.R;
import com.admin.exe.shop.db.DBConfig;
import com.admin.exe.shop.entity.ShopCarEntity;
import com.bumptech.glide.Glide;
import com.example.library.utils.DateUtils;

import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class ItemLvShopcarAdapter extends BaseAdapter {

    private Dialog mDialog;
    private List<ShopCarEntity> mEntities;
    private View.OnClickListener mOnClickListener;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    public List<CheckBox> cbs = new ArrayList<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<ShopCarEntity> all = null;
            try {
                all = x.getDb(DBConfig.getDaoConfig()).findAll(ShopCarEntity.class);

                    ItemLvShopcarAdapter.this.setEntities(all);

            } catch (DbException e) {
                e.printStackTrace();
            }
            mDialog.dismiss();
        }
    };

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public ItemLvShopcarAdapter(Context context, List<ShopCarEntity> entities) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mEntities = entities;
        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.dig_ing);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
    }

    public void setEntities(List<ShopCarEntity> entities) {
        mEntities = entities;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mEntities.size();
    }

    @Override
    public ShopCarEntity getItem(int position) {
        return mEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_lv_shopcar, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((ShopCarEntity) getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;
    }

    private void initializeViews(ShopCarEntity entity, ViewHolder holder, int position) {
        //TODO implement

        holder.cb.setTag(position);
        if (holder.cb.isChecked()) {
            holder.mTvDel.setVisibility(View.VISIBLE);
        } else {
            holder.mTvDel.setVisibility(View.INVISIBLE);
        }
        if (mOnClickListener != null) {
            holder.cb.setOnClickListener(mOnClickListener);
        }
        Glide.with(mContext).load(entity.getPic()).into(holder.imgvHead);
        holder.tvTitle.setText(entity.getTitle());
        holder.tvPrice.setText(" ￥ " + entity.getPrice());
        holder.tvTotalPrice.setText("总价: ￥ " + entity.getTotalprice());
        holder.tvNum.setText("X" + entity.getNum());
        holder.tvTime.setText(DateUtils.getDateString("yyyy-MM-dd HH:mm", entity.getTime()));
        holder.mTvDel.setTag(entity);
        holder.mTvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ShopCarEntity entity = (ShopCarEntity) v.getTag();
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("确定要移除该商品吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    x.getDb(DBConfig.getDaoConfig()).delete(entity);
                                    Toast.makeText(mContext, "已移除", Toast.LENGTH_SHORT).show();
                                    mDialog.show();
                                    mHandler.sendEmptyMessageDelayed(0, 1000);
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("取消", null);
                builder.create().show();


            }
        });
    }

    protected class ViewHolder {
        private LinearLayout llRoot;
        private CheckBox cb;
        private ImageView imgvHead;
        private TextView tvTitle;
        private TextView tvPrice;
        private TextView tvTotalPrice;
        private TextView tvNum;
        private TextView tvTime;
        private TextView mTvDel;


        public ViewHolder(View view) {
            llRoot = (LinearLayout) view.findViewById(R.id.ll_root);
            cb = (CheckBox) view.findViewById(R.id.cb);
            imgvHead = (ImageView) view.findViewById(R.id.imgv_head);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
            tvTotalPrice = (TextView) view.findViewById(R.id.tv_total_price);
            tvNum = (TextView) view.findViewById(R.id.tv_num);
            tvTime = (TextView) view.findViewById(R.id.tv_time);
            mTvDel = (TextView) view.findViewById(R.id.tv_del);
            cbs.add(cb);
        }
    }
}
