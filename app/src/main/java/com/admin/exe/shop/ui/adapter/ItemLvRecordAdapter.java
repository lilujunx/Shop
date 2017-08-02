package com.admin.exe.shop.ui.adapter;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import com.admin.exe.shop.entity.RecordEntity;
import com.admin.exe.shop.ui.activity.EvaluteActivity;
import com.admin.exe.shop.ui.activity.RecordActivity;
import com.admin.exe.shop.utils.NetCallback;
import com.admin.exe.shop.utils.NetForJson;
import com.admin.exe.shop.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.example.library.utils.DateUtils;

import java.util.List;

public class ItemLvRecordAdapter extends BaseAdapter {

    private List<RecordEntity.RecordsBean> mEntities;
    private View.OnClickListener mOnClickListener;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int delPos;
    private String tagType;
    private Dialog mDialog;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mDialog != null) {
                mDialog.dismiss();
            }
            switch (msg.what) {
                case 1:
                    Toast.makeText(mContext, "已告知卖家尽早发货", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    doShouhuo(delPos);
                    break;
                case 3:
                    Intent intent = new Intent(mContext, EvaluteActivity.class);
                    mContext.startActivity(intent);

                    break;
            }

        }
    };


    public ItemLvRecordAdapter(Context context, List<RecordEntity.RecordsBean> entities) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mEntities = entities;
        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.dig_ing);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
    }


    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    public int getCount() {
        return mEntities.size();
    }

    @Override
    public RecordEntity.RecordsBean getItem(int position) {
        return mEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_lv_record, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((RecordEntity.RecordsBean) getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;
    }

    private void initializeViews(RecordEntity.RecordsBean entity, final ViewHolder holder, int position) {
        //TODO implement
        if (mOnClickListener != null) {
            holder.llRoot.setTag(entity);
            holder.llRoot.setOnClickListener(mOnClickListener);
        }
        Glide.with(mContext).load(entity.getPic()).into(holder.imgvHead);
        holder.tvTitle.setText(entity.getSname());
        holder.tvPrice.setText("￥" + entity.getBprice());
        String mytype = "";
        switch (entity.getMyType()) {

            case RecordEntity.WEIFAHUO:
                mytype = "待发货";
                holder.tvMystype.setTextColor(mContext.getResources().getColor(R.color.yellow));
                holder.tvCaozuo1.setText("提醒发货");
                holder.tvCaozuo2.setText("取消订单");
                holder.tvCaozuo1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.show();
                        holder.tvCaozuo1.setVisibility(View.INVISIBLE);
                        holder.tvCaozuo1.setOnClickListener(null);
                        mHandler.sendEmptyMessageDelayed(1, 1500);
                    }
                });
                holder.tvCaozuo2.setTag(position);
                holder.tvCaozuo2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delPos = (int) v.getTag();
                        tagType = "待发货";

                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("确定要取消订单吗？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        doCheckRecord(delPos);
                                    }
                                })
                                .setNegativeButton("取消", null);
                        builder.create().show();
                    }
                });
                break;
            case RecordEntity.DAISHOUHUO:
                mytype = "待收货";
                holder.tvMystype.setTextColor(mContext.getResources().getColor(R.color.red));
                holder.tvCaozuo1.setText("查看物流");

                holder.tvCaozuo2.setText("确认收货");
                holder.tvCaozuo2.setTag(position);
                holder.tvCaozuo2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delPos = (int) v.getTag();
                        Constant.mRecordsBean = mEntities.get(delPos);
                        mDialog.show();
                        mHandler.sendEmptyMessageDelayed(2, 300);
                    }
                });
                break;
            case RecordEntity.WEIPINGJIA:
                mytype = "未评价";
                holder.tvMystype.setTextColor(mContext.getResources().getColor(R.color.blue));
                holder.tvCaozuo1.setText("前去评价");
                holder.tvCaozuo1.setTag(position);
                holder.tvCaozuo1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delPos = (int) v.getTag();
                        Constant.mRecordsBean = mEntities.get(delPos);
                        Intent intent = new Intent(mContext, EvaluteActivity.class);
                        mContext.startActivity(intent);
                    }
                });
                holder.tvCaozuo2.setVisibility(View.INVISIBLE);
                break;
            case RecordEntity.YISHOUHUO:
                mytype = "已收货";
                holder.tvCaozuo1.setText("删除订单");
                holder.tvCaozuo1.setTag(position);
                holder.tvCaozuo1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delPos = (int) v.getTag();
                        tagType = "已收货";
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("确定要删除订单吗？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        doCheckRecord(delPos);
                                    }
                                })
                                .setNegativeButton("取消", null);
                        builder.create().show();
                    }

                });
                holder.tvCaozuo2.setVisibility(View.INVISIBLE);
                holder.tvMystype.setTextColor(mContext.getResources().getColor(R.color.green));
                break;

        }
        holder.tvMystype.setText(mytype);
        holder.tvTime.setText(DateUtils.getDateString("yyyy-MM-dd HH:mm", entity.getLtime()));
    }

    private void doCheckRecord(int delPos) {
        NetForJson netForJson = new NetForJson(Constant.URL_RECORD, new CheckCall());
        netForJson.addParam("stype", "delete");
        netForJson.addParam("sid", mEntities.get(delPos).getSid());
        netForJson.addParam("bnum", mEntities.get(delPos).getBnum());
        netForJson.addParam("lid", mEntities.get(delPos).getLid());

        netForJson.execute();
    }

    private void doShouhuo(int delPos) {
        NetForJson netForJson = new NetForJson(Constant.URL_RECORD, new CheckCall());
        netForJson.addParam("stype", "shouhuo");
        netForJson.addParam("lid", mEntities.get(delPos).getLid());

        netForJson.execute();
    }

    class CheckCall extends NetCallback<RecordEntity> {

        @Override
        public void onSuccess(RecordEntity recordEntity) {
            if (recordEntity.getSuccess().equals("1")) {
                Toast.makeText(mContext, "订单删除成功", Toast.LENGTH_SHORT).show();
                Log.e("xx", delPos + ",");
                if (Constant.TAG.equals("全部")) {
                    Constant.mAllRecord.remove(delPos);
                    Log.e("xx", "移除全部");
                } else {
                    if (tagType.equals("待发货")) {
                        Constant.mWeiFa.remove(delPos);
                    } else {
                        Constant.mShouHuo.remove(delPos);
                    }
                }
                notifyDataSetChanged();
            } else if (recordEntity.getSuccess().equals("2")) {
                Toast.makeText(mContext, "成功收货", Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessageDelayed(3, 1000);
            } else if (recordEntity.getSuccess().equals("null")) {
                Toast.makeText(mContext, "订单不存在", Toast.LENGTH_SHORT).show();
            } else if (recordEntity.getSuccess().equals("0")) {
                Toast.makeText(mContext, "您的订单刚刚已发货，请收货后再申请退款", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, RecordActivity.class);
                mContext.startActivity(intent);
            }
        }

        @Override
        public void onError(Throwable t) {
            Log.e("xx", t.toString());
            ToastUtils.getToastOnFailure(mContext);
        }

        @Override
        public void onFinished() {

        }
    }

    protected class ViewHolder {
        private LinearLayout llRoot;
        private ImageView imgvHead;
        private TextView tvTitle;
        private TextView tvPrice;
        private TextView tvTime;
        private TextView tvCaozuo1;
        private TextView tvCaozuo2;
        private TextView tvMystype;

        public ViewHolder(View view) {
            llRoot = (LinearLayout) view.findViewById(R.id.ll_root);
            imgvHead = (ImageView) view.findViewById(R.id.imgv_head);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
            tvTime = (TextView) view.findViewById(R.id.tv_time);
            tvCaozuo1 = (TextView) view.findViewById(R.id.tv_caozuo1);
            tvCaozuo2 = (TextView) view.findViewById(R.id.tv_caozuo2);
            tvMystype = (TextView) view.findViewById(R.id.tv_mystype);
        }
    }
}
