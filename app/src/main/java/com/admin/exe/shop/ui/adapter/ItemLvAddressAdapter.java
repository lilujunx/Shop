package com.admin.exe.shop.ui.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.exe.shop.Constant;
import com.admin.exe.shop.R;
import com.admin.exe.shop.entity.ReceiverEntity;
import com.admin.exe.shop.ui.activity.AddressDetailActivity;
import com.admin.exe.shop.utils.NetCallback;
import com.admin.exe.shop.utils.NetForJson;
import com.admin.exe.shop.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemLvAddressAdapter extends BaseAdapter implements View.OnClickListener {

    private List<ReceiverEntity.Receiver> mEntities;
    private View.OnClickListener mOnClickListener;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int index = 0;
    private boolean isShow = false;
    private int checkIndex;
    public List<CheckBox> cbs=new ArrayList<>();
    public ReceiverEntity.Receiver checkEntity;

    public void setCheckIndex(int checkIndex) {
        this.checkIndex = checkIndex;
    }

    public void setShow(boolean show) {
        isShow = show;
        notifyDataSetChanged();
    }


    public void setEntities(List<ReceiverEntity.Receiver> entities) {
        mEntities = entities;
        notifyDataSetChanged();
    }

    public ItemLvAddressAdapter(Context context, List<ReceiverEntity.Receiver> entities) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mEntities = entities;
        checkEntity = entities.get(0);
    }


    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    public int getCount() {
        return mEntities.size();
    }

    @Override
    public ReceiverEntity.Receiver getItem(int position) {
        return mEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_lv_address, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((ReceiverEntity.Receiver) getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;
    }

    private void initializeViews(final ReceiverEntity.Receiver entity, ViewHolder holder, int position) {
        //TODO implement
        if (isShow) {
            holder.mCb.setVisibility(View.VISIBLE);
            holder.mCb.setTag(position);
            if (checkIndex == position) {
                holder.mCb.setChecked(true);
            } else {
                holder.mCb.setChecked(false);
            }
            if (mOnClickListener != null) {
                holder.mCb.setOnClickListener(mOnClickListener);
            }
        } else {
            holder.mCb.setVisibility(View.GONE);
        }

        index = position;
        holder.tvRname.setText(entity.getRname());
        holder.tvAddress.setText(entity.getRaddress().replace(",", " "));
        holder.tvRphone.setText(entity.getRphone());
        holder.llAddress.setTag(entity);
        holder.llEdit.setTag(entity);
        holder.llAddress.setOnClickListener(this);
        holder.llEdit.setOnClickListener(this);

        holder.llDelete.setTag(entity);
        holder.llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ReceiverEntity.Receiver receiver = (ReceiverEntity.Receiver) v.getTag();
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("确认删除吗?")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doDelete(receiver);
                            }
                        })
                        .setNegativeButton("取消", null);
                builder.create().show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        ReceiverEntity.Receiver entity = (ReceiverEntity.Receiver) v.getTag();
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        Intent intent = new Intent(mContext, AddressDetailActivity.class);
        intent.putExtra("bundle", bundle);
        mContext.startActivity(intent);
    }

    private void doDelete(ReceiverEntity.Receiver receiver) {
        NetForJson netForJson = new NetForJson(Constant.URL_RECEIVER, new ReceiverCallback(), true);
        netForJson.addParam("uid", receiver.getUid());
        netForJson.addParam("rname", receiver.getRname());
        netForJson.addParam("rphone", receiver.getRphone());
        netForJson.addParam("raddress", receiver.getRaddress());
        netForJson.execute();
    }

    class ReceiverCallback extends NetCallback<ReceiverEntity> {

        @Override
        public void onSuccess(ReceiverEntity receiverEntity) {
            if (receiverEntity.getSuccess().equals("1")) {
                Toast.makeText(mContext, "已删除", Toast.LENGTH_SHORT).show();
                mEntities.remove(index);
                ItemLvAddressAdapter.this.setEntities(mEntities);
            }
        }

        @Override
        public void onError(Throwable throwable) {
            ToastUtils.getToastOnFailure(mContext);
            Log.e("xx", throwable.toString());
        }

        @Override
        public void onFinished() {

        }
    }


    protected class ViewHolder {
        private LinearLayout llAddress;
        private TextView tvRname;
        private TextView tvRphone;
        private TextView tvAddress;
        private LinearLayout llEdit;
        private LinearLayout llDelete;
        private CheckBox mCb;


        public ViewHolder(View view) {
            llAddress = (LinearLayout) view.findViewById(R.id.ll_address);
            mCb = (CheckBox) view.findViewById(R.id.cb);
            cbs.add(mCb);
            tvRname = (TextView) view.findViewById(R.id.tv_rname);
            tvRphone = (TextView) view.findViewById(R.id.tv_rphone);
            tvAddress = (TextView) view.findViewById(R.id.tv_address);
            llEdit = (LinearLayout) view.findViewById(R.id.ll_edit);
            llDelete = (LinearLayout) view.findViewById(R.id.ll_delete);
        }
    }
}
