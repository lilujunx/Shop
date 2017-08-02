package com.admin.exe.shop.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */

public class ShopListEntity {


    /**
     * mShops : [{"spic":"http://192.168.31.118:8080/Shop/image/home/homelimit/limit0.jpg","lprice":"89","sname":"新安怡400ml婴儿二合一洗发沐浴露SCF51341","snum":"60","stype":"1","sprice":"136","lefttime":3600000,"sid":"247004"},{"spic":"http://192.168.31.118:8080/Shop/image/home/homelimit/limit1.jpg","lprice":"79","sname":"BOOTO 260ML sweet系列爱心条纹双柄吸管保温水壶","snum":"70","stype":"1","sprice":"118","lefttime":3600000,"sid":"416081"},{"spic":"http://192.168.31.118:8080/Shop/image/home/homelimit/limit2.jpg","lprice":"399","sname":"费雪多功能摇椅DMR87","snum":"3","stype":"1","sprice":"699","lefttime":3600000,"sid":"420502"},{"spic":"http://192.168.31.118:8080/Shop/image/home/homelimit/limit3.jpg","lprice":"249","sname":"托马斯电动系列之雪地大冒险轨道套装DHC78","snum":"4","stype":"1","sprice":"499","lefttime":3600000,"sid":"420496"},{"spic":"http://192.168.31.118:8080/Shop/image/home/homelimit/limit4.jpg","lprice":"32.9","sname":"Booto婴儿柔湿巾80片*3包","snum":"50","stype":"1","sprice":"49","lefttime":3600000,"sid":"321038"},{"spic":"http://192.168.31.118:8080/Shop/image/home/homelimit/limit5.jpg","lprice":"79.9","sname":"菲丽洁30g小绵羊油滋润霜","snum":"10","stype":"1","sprice":"95.5","lefttime":3600000,"sid":"211142"}]
     * success : 0
     */

    private String success;
    /**
     * spic : http://192.168.31.118:8080/Shop/image/home/homelimit/limit0.jpg
     * lprice : 89
     * sname : 新安怡400ml婴儿二合一洗发沐浴露SCF51341
     * snum : 60
     * stype : 1
     * sprice : 136
     * lefttime : 3600000
     * sid : 247004
     */

    private List<ShopsBean> mShops;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ShopsBean> getMShops() {
        return mShops;
    }

    public void setMShops(List<ShopsBean> mShops) {
        this.mShops = mShops;
    }


    public static class ShopsBean {
        private String spic;
        private String lprice;
        private String sname;
        private String snum;
        private String stype;
        private String sprice;
        private int lefttime;
        private String sid;
        private String salenum;
        private String sclass;


        public String getSclass() {
            return sclass;
        }

        public void setSclass(String sclass) {
            this.sclass = sclass;
        }

        public String getSalenum() {
            return salenum;
        }

        public void setSalenum(String salenum) {
            this.salenum = salenum;
        }

        public String getSpic() {
            return spic;
        }

        public void setSpic(String spic) {
            this.spic = spic;
        }

        public String getLprice() {
            return lprice;
        }

        public void setLprice(String lprice) {
            this.lprice = lprice;
        }

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }

        public String getSnum() {
            return snum;
        }

        public void setSnum(String snum) {
            this.snum = snum;
        }

        public String getStype() {
            return stype;
        }

        public void setStype(String stype) {
            this.stype = stype;
        }

        public String getSprice() {
            return sprice;
        }

        public void setSprice(String sprice) {
            this.sprice = sprice;
        }

        public int getLefttime() {
            return lefttime;
        }

        public void setLefttime(int lefttime) {
            this.lefttime = lefttime;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        @Override
        public String toString() {
            return "ShopsBean{" +
                    "spic='" + spic + '\'' +
                    ", lprice='" + lprice + '\'' +
                    ", sname='" + sname + '\'' +
                    ", snum='" + snum + '\'' +
                    ", stype='" + stype + '\'' +
                    ", sprice='" + sprice + '\'' +
                    ", lefttime=" + lefttime +
                    ", sid='" + sid + '\'' +
                    ", salenum='" + salenum + '\'' +
                    ", sclass='" + sclass + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "ShopListEntity{" +
                "success='" + success + '\'' +
                ", mShops=" + mShops +
                '}';
    }
}
