package com.admin.exe.shop.ui.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.exe.shop.R;
import com.admin.exe.shop.db.DBConfig;
import com.admin.exe.shop.entity.ShopCarEntity;
import com.admin.exe.shop.entity.ShopDetailEntity;
import com.admin.exe.shop.ui.activity.MyShopCarActivity;
import com.admin.exe.shop.ui.activity.PayDetailActivity;
import com.bumptech.glide.Glide;

import org.xutils.common.util.DensityUtil;
import org.xutils.ex.DbException;
import org.xutils.x;


public class PopBuy extends PopupWindow implements View.OnClickListener {
    private Context mContext;
    private ImageView mImgvHead;
    private TextView mTvName, mTvPrice, mTvBuy, mTvSnum, mTvNum;
    private LinearLayout mLlAdd, mLlDelete;
    private String price;
    private ShopDetailEntity mShopDetailEntity;
    private int num = 1;
    private String mType;


    public PopBuy(Context context, ShopDetailEntity shopDetailEntity, String type) {
        super(context);
        mContext = context;
        mType = type;
        mShopDetailEntity = shopDetailEntity;
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setWidth(DensityUtil.getScreenWidth());
        this.setHeight(DensityUtil.dip2px(260));
        this.setBackgroundDrawable(new BitmapDrawable());
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View contentView = layoutInflater.inflate(R.layout.layout_pop_buy, null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(DensityUtil.getScreenWidth(), DensityUtil.dip2px(260));
        contentView.setLayoutParams(layoutParams);
        this.setContentView(contentView);
        this.setAnimationStyle(R.style.pop_more);
        mImgvHead = (ImageView) contentView.findViewById(R.id.imgv_head);
        mTvName = (TextView) contentView.findViewById(R.id.tv_name);
        mTvPrice = (TextView) contentView.findViewById(R.id.tv_price);
        mTvSnum = (TextView) contentView.findViewById(R.id.tv_snum);
        mLlAdd = (LinearLayout) contentView.findViewById(R.id.ll_add);
        mTvNum = (TextView) contentView.findViewById(R.id.tv_num);
        mTvBuy = (TextView) contentView.findViewById(R.id.tv_buy);
        mTvNum.setText("1");
        mLlDelete = (LinearLayout) contentView.findViewById(R.id.ll_delete);
        doSomething();
        mLlAdd.setOnClickListener(this);
        mLlDelete.setOnClickListener(this);
        mTvBuy.setOnClickListener(this);
    }

    private void doSomething() {
        Glide.with(mContext).load(mShopDetailEntity.getSpic()).into(mImgvHead);
        price = mShopDetailEntity.getLprice();
        if (price == null || "".equals(price)) {
            price = mShopDetailEntity.getSprice();
        }
        mTvPrice.setText("￥" + price);
        mTvName.setText(mShopDetailEntity.getSname());
        mTvSnum.setText("剩余库存:" + mShopDetailEntity.getSnum() + "件");
        if(mShopDetailEntity.getSnum().equals("0")){
            mLlAdd.setVisibility(View.INVISIBLE);
            mLlDelete.setVisibility(View.INVISIBLE);
            mTvNum.setText("暂时没有库存了哦");
            mTvBuy.setEnabled(false);
            mTvBuy.setBackgroundColor(mContext.getResources().getColor(R.color.gray_line));
            mTvBuy.setText("缺货");
            mTvBuy.setTextColor(mContext.getResources().getColor(R.color.app_style));
        }
    }


    @Override
    public void onClick(View v) {
        if (num >= 1 && num <= Integer.parseInt(mShopDetailEntity.getSnum())) {
            mLlDelete.setVisibility(View.VISIBLE);
            mLlAdd.setVisibility(View.VISIBLE);
        }
        switch (v.getId()) {
            case R.id.ll_add:
                if (num < Integer.parseInt(mShopDetailEntity.getSnum())) {
                    mLlAdd.setVisibility(View.VISIBLE);
                    num++;
                } else {
                    mLlAdd.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.ll_delete:
                if (num > 1) {
                    mLlDelete.setVisibility(View.VISIBLE);
                    num--;
                } else {
                    mLlDelete.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.tv_buy:
                if (mType.equals("buy")) {
                    Intent intent = new Intent(mContext, PayDetailActivity.class);
                    intent.putExtra("sname", mShopDetailEntity.getSname());
                    intent.putExtra("sid", mShopDetailEntity.getSid());
                    intent.putExtra("sprice", mTvPrice.getText().toString());
                    intent.putExtra("snum", mTvNum.getText().toString());
                    mContext.startActivity(intent);

                } else {
                    ShopCarEntity shopCarEntity = new ShopCarEntity();
                    shopCarEntity.setNum(mTvNum.getText().toString());
                    shopCarEntity.setTotalprice(String.format("%.2f", num * Double.parseDouble(price)));
                    shopCarEntity.setPrice(price);
                    shopCarEntity.setPic(mShopDetailEntity.getSpic());
                    shopCarEntity.setTitle(mShopDetailEntity.getSname());
                    shopCarEntity.setTime(String.valueOf(System.currentTimeMillis()));
                    try {
                        x.getDb(DBConfig.getDaoConfig()).save(shopCarEntity);
                        Log.e("xx", "存储购物车:" + shopCarEntity.toString());
                        Toast.makeText(mContext, "添加购物车成功", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("添加成功，是否去购物车查看")
                                .setNegativeButton("留在当前", null)
                                .setPositiveButton("去购物车", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(mContext, MyShopCarActivity.class);
                                        mContext.startActivity(intent);
                                        PopBuy.this.dismiss();
                                    }
                                });
                        builder.create().show();
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
                this.dismiss();
                return;
        }
        mTvNum.setText(String.valueOf(num));

        mTvPrice.setText("￥" + String.format("%.2f", num * Double.parseDouble(price)));
    }
}
