package com.admin.exe.shop;


import com.admin.exe.shop.entity.RecordEntity;

import java.util.ArrayList;
import java.util.Random;

public class Constant {
    public static boolean isDebug=true;
    public static boolean isLogin;
     
    public static final String URL = "http://172.21.252.1:8080/Shop";
  
    public static final int EVERY=3000;
    public static final String URL_HOME = URL + "/home";
    public static final String URL_SHOPDETAIL = URL + "/shopdetail";
    public static final String URL_EVALUTE = URL + "/evalute";
    public static final String URL_SHOPLIST = URL + "/shoplist";
    public static final String URL_USERABOUT = URL + "/user";
    public static final String URL_RECEIVER = URL + "/receiver";
    public static final String URL_COLLECT = URL + "/collect";
    public static final String URL_RECORD = URL + "/record";
    public static final String URL_EVALUTEABOUT = URL + "/evaluteabout";

    public static final String URL_QIANGSHENG = "https://www.johnsonsbaby.com.cn/baby-products"; //强生
    public static final String URL_MAMIBAOBEI = "http://www.mamypoko.cn/m/"; //妈咪宝贝
    public static final String URL_BEIYINMEI = "http://www.beingmate.com/m"; //贝因美
    public static final String URL_FEIHE = "https://shop.m.jd.com/?shopId=23898"; //飞鹤
    public static final String URL_LIJIA = "https://m.lijiababy.com.cn/"; //丽家宝贝
    public static final String URL_MEIZAN = "https://www.meadjohnson.com.cn/"; //美赞臣
    public static final String URL_BANG = "http://www.pampers.com.cn/?utm_source=baidu&utm_medium=mobilebz&utm_campaign=pmp1676sem002o1&utm_channel=pams"; //帮宝适
    public static final String URL_QUECHAO = "https://nestlemy.m.tmall.com/?spm=a222m.7628550/A.1998338747.2.4teB4t"; //雀巢

    public static final String[] URLS = {URL_LIJIA, URL_BEIYINMEI, URL_FEIHE, URL_MAMIBAOBEI,
            URL_MEIZAN, URL_QIANGSHENG, URL_BANG, URL_QUECHAO};
    public static String STREET = "";
    public static Random random = new Random();
    public static String[] titles = {"地址管理", "待收货", "待发货", "待评价", "我的收藏", "全部订单", "我的购物车"};
    public static ArrayList<String> history = new ArrayList<>();
    public static String[] recommend = {"奶粉", "壶", "乳酸菌", "湿巾", "滋润霜", "橙子味", "胡萝卜", "燕麦粉"};
    public static String[] recommend1 = {"合生元", "玛珞", "电动", "摇椅", "营养面", "饼干", "泡腾片", "美赞臣"};
    public static String[] recommend2 = {"方广", "菲丽洁", "婴儿柔湿巾", "消毒锅", "新安怡", "沐浴露", "保温水壶", "奶嘴"};


    public static int[] icons = {R.drawable.icon_dizhi, R.drawable.icon_daishouhuo, R.drawable.icon_daifahuo, R.drawable.icon_daifukuan,
            R.drawable.icon_collect, R.drawable.icon_quanbudingdan, R.drawable.icon_shopcar};
    public static int[] brs = {R.drawable.br_lijia, R.drawable.br_beiyinmei, R.drawable.br_feihe, R.drawable.br_mami
            , R.drawable.br_meizan, R.drawable.br_qiangsheng, R.drawable.br_bang, R.drawable.br_quechao};

    public static ArrayList<RecordEntity.RecordsBean> mAllRecord = new ArrayList<>();
    public static ArrayList<RecordEntity.RecordsBean> mWeiFa = new ArrayList<>();
    public static ArrayList<RecordEntity.RecordsBean> mDaiShow = new ArrayList<>();
    public static ArrayList<RecordEntity.RecordsBean> mWeiPingJia = new ArrayList<>();
    public static ArrayList<RecordEntity.RecordsBean> mYiPingJia = new ArrayList<>();
    public static ArrayList<RecordEntity.RecordsBean> mShouHuo = new ArrayList<>();
    public static RecordEntity.RecordsBean mRecordsBean;
    public static int from = 0;
    public static String sid;
    public static String TAG="全部";
    public static String sname;
    public static String sprice;
    public static String snum;
}
