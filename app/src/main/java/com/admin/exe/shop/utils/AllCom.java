package com.admin.exe.shop.utils;

import com.admin.exe.shop.entity.ShopListEntity;
import com.example.library.utils.DoubleUtil;


import java.util.Comparator;

/**
 * Created by Administrator on 2017/1/25.
 */

public abstract class AllCom implements Comparator<ShopListEntity.ShopsBean> {

}




class TypeComAes extends AllCom {
    //类型，升序
    @Override
    public int compare(ShopListEntity.ShopsBean t1, ShopListEntity.ShopsBean t2) {
        return t1.getStype().compareTo(t2.getStype());
    }
}

class TypeComDes extends AllCom {
    //类型，降序
    @Override
    public int compare(ShopListEntity.ShopsBean t1, ShopListEntity.ShopsBean t2) {
        return -t1.getStype().compareTo(t2.getStype());
    }
}

class PriceComAes extends AllCom {
    //单价，升序
    @Override
    public int compare(ShopListEntity.ShopsBean t1, ShopListEntity.ShopsBean t2) {
        if (Double.parseDouble(t2.getLprice()) >Double.parseDouble( t1.getLprice())) {
            return -1;
        }
        if (Double.parseDouble(t2.getLprice()) < Double.parseDouble(t1.getLprice())) {
            return 1;
        }
        return 0;
    }
}

class PriceComDes extends AllCom {
    //单价，降序
    @Override
    public int compare(ShopListEntity.ShopsBean t1, ShopListEntity.ShopsBean t2) {
        if (Double.parseDouble(t2.getLprice()) >Double.parseDouble( t1.getLprice())) {
            return 1;
        }
        if (Double.parseDouble(t2.getLprice()) < Double.parseDouble(t1.getLprice())) {
            return -1;
        }
        return 0;
    }
}

