package com.admin.exe.shop.db;

import org.xutils.DbManager;

/**
 * Created by Administrator on 0026/10/26.
 */

public class DBConfig {
    private static DbManager.DaoConfig mDaoConfig;

    public static DbManager.DaoConfig getDaoConfig(){

        if (mDaoConfig==null){
            mDaoConfig=new DbManager.DaoConfig();
            mDaoConfig.setDbVersion(1);
            mDaoConfig.setDbName("shop.db");
        }

        return mDaoConfig;
    }


    public static int max=100;
}
