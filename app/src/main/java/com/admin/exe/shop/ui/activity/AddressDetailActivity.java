package com.admin.exe.shop.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.exe.shop.Constant;
import com.admin.exe.shop.R;
import com.admin.exe.shop.base.SCBaseActivity;
import com.admin.exe.shop.entity.JsonBean;
import com.admin.exe.shop.entity.ReceiverEntity;
import com.admin.exe.shop.utils.GetJsonDataUtil;
import com.admin.exe.shop.utils.LogerUtils;
import com.admin.exe.shop.utils.NetCallback;
import com.admin.exe.shop.utils.NetForJson;
import com.admin.exe.shop.utils.ToastUtils;
import com.bigkoo.pickerview.OptionsPickerView;
import com.example.library.utils.EdtUtil;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

public class AddressDetailActivity extends SCBaseActivity implements View.OnClickListener {
    private RelativeLayout mRlTitle;
    private ImageView mTitleLeft;
    private TextView mTitleCenter;
    private TextView mTitleRight;
    private EditText mEditRname, mEditRphone, mEditStreet;
    private LinearLayout mLlRaddress;
    private TextView mTvRaddress;
    private Button mBtSubmit;
    private String uid, rname, rphone, raddress;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private boolean isLoaded;
    private OptionsPickerView pvOptions;
    private String type;
    private ReceiverEntity.Receiver entity;

    @Override
    public int initRootLayout() {
        return R.layout.activity_address_detail;
    }

    @Override
    public void initViews() {
        initJsonData();

        mRlTitle = (RelativeLayout) findViewById(R.id.rl_title);
        mTitleLeft = (ImageView) findViewById(R.id.title_left);
        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mTitleRight = (TextView) findViewById(R.id.title_right);
        mEditRname = (EditText) findViewById(R.id.edit_rname);
        mEditRphone = (EditText) findViewById(R.id.edit_rphone);
        mLlRaddress = (LinearLayout) findViewById(R.id.ll_raddress);
        mTvRaddress = (TextView) findViewById(R.id.tv_raddress);
        mBtSubmit = (Button) findViewById(R.id.bt_submit);
        mEditStreet = (EditText) findViewById(R.id.edit_street);
        uid = LogerUtils.getUid(mActivitySelf);
        type = getIntent().getStringExtra("type");
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            entity = (ReceiverEntity.Receiver) bundle.getSerializable("entity");
            if (entity != null) {
                String[] split = entity.getRaddress().split(",");
                mTvRaddress.setText(split[0].replace(",", ""));
                mEditRname.setText(entity.getRname());
                mEditRphone.setText(entity.getRphone());
                mEditStreet.setText(split[1]);
            }
        }
    }

    @Override
    public void initDatas() {
        mTitleCenter.setText("修改地址");
        mTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelf.finish();
            }
        });
        mBtSubmit.setOnClickListener(this);
        mLlRaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPickerView();
            }
        });
        mTitleRight.setText("更新");
        mTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUpdate();
            }
        });
    }

    private void doUpdate() {
        NetForJson netForJson = new NetForJson(Constant.URL_RECEIVER, new ReceiverCallback(), true);
        netForJson.addParam("stype", "update");
        netForJson.addParam("uid", uid);
        netForJson.addParam("rid", entity.getRid());
        netForJson.addParam("rname", rname);
        netForJson.addParam("rphone", rphone);
        netForJson.addParam("raddress", raddress);
        netForJson.execute();
    }

    @Override
    public void initOthers() {

    }

    @Override
    public void onClick(View v) {
        rname = EdtUtil.getEdtText(mEditRname);
        rphone = EdtUtil.getEdtText(mEditRphone);
        raddress = mTvRaddress.getText().toString() + EdtUtil.getEdtText(mEditStreet);
        NetForJson netForJson = new NetForJson(Constant.URL_RECEIVER, new ReceiverCallback(), true);
        netForJson.addParam("stype", "delete");
        netForJson.addParam("uid", uid);
        netForJson.addParam("rname", rname);
        netForJson.addParam("rphone", rphone);
        netForJson.addParam("raddress", raddress);
        Log.e("xx", "uid:" + uid + ",,rname:" + rname + ",,rphone:" + rphone + ",,raddress:" + raddress);
        netForJson.execute();
    }

    private void ShowPickerView() {// 弹出选择器

        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                raddress = options1Items.get(options1).getPickerViewText() + "    " +
                        options2Items.get(options1).get(options2) + "    " +
                        options3Items.get(options1).get(options2).get(options3);
                mTvRaddress.setText(raddress);

            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void initJsonData() {//解析数据
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据
        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体
        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);
                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            options2Items.add(CityList);
            options3Items.add(Province_AreaList);
        }
        isLoaded = true;
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("xx", e.toString());
        }
        return detail;
    }

    class ReceiverCallback extends NetCallback<ReceiverEntity> {

        @Override
        public void onSuccess(ReceiverEntity receiverEntity) {
            if (receiverEntity.getSuccess().equals("1")) {
                Toast.makeText(mActivitySelf, "删除成功", Toast.LENGTH_SHORT).show();
                mActivitySelf.finish();
            } else if (receiverEntity.getSuccess().equals("2")) {
                Toast.makeText(mActivitySelf, "更新成功", Toast.LENGTH_SHORT).show();
                mActivitySelf.finish();
            } else {
                Toast.makeText(mActivitySelf, "您的网络有问题", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            ToastUtils.getToastOnFailure(mActivitySelf);
            Log.e("xx", throwable.toString());
        }

        @Override
        public void onFinished() {

        }
    }
}
