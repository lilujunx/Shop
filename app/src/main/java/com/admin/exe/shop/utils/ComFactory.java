package com.admin.exe.shop.utils;

/**
 * Created by Administrator on 2017/1/25.
 */

public class ComFactory {
    public static ComFactory mComFactory;

    private ComFactory() {

    }


    public static ComFactory getComFactory() {
        if (mComFactory == null) {
            synchronized (ComFactory.class) {
                if (mComFactory == null) {
                    mComFactory = new ComFactory();
                }
            }
        }
        return mComFactory;
    }

    public AllCom getCom(String key) {
        AllCom allCom = null;
        switch (key) {
            case "timeComAes":

                break;
            case "timeComDes":

                break;
            case "typeComAes":
                allCom = new TypeComAes();
                break;
            case "typeComDes":
                allCom = new TypeComDes();
                break;
            case "priceComAes":
                allCom = new PriceComAes();
                break;
            case "priceComDes":
                allCom = new PriceComDes();
                break;
            case "surplusComAes":

                break;
            case "surplusComDes":

                break;
        }
        return allCom;
    }


}
