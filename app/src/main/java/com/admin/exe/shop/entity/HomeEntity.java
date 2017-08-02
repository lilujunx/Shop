package com.admin.exe.shop.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */

public class HomeEntity {

    /**
     * limitBuy : [{"leftime":3600000,"lprice":"89","price":"136","snum":"60","id":"0","pic":"http://127.0.0.1:8080/Shop/image/home/homelimit/limit0.jpg","title":"新安怡400ml婴儿二合一洗发沐浴露SCF51341"},{"leftime":3600000,"lprice":"79","price":"118","snum":"70","id":"1","pic":"http://127.0.0.1:8080/Shop/image/home/homelimit/limit1.jpg","title":"BOOTO 260ML sweet系列爱心条纹双柄吸管保温水壶"},{"leftime":3600000,"lprice":"399","price":"699","snum":"3","id":"2","pic":"http://127.0.0.1:8080/Shop/image/home/homelimit/limit2.jpg","title":"费雪多功能摇椅DMR87"},{"leftime":3600000,"lprice":"249","price":"499","snum":"4","id":"3","pic":"http://127.0.0.1:8080/Shop/image/home/homelimit/limit3.jpg","title":"托马斯电动系列之雪地大冒险轨道套装DHC78"},{"leftime":3600000,"lprice":"32.9","price":"49","snum":"50","id":"4","pic":"http://127.0.0.1:8080/Shop/image/home/homelimit/limit4.jpg","title":"Booto婴儿柔湿巾80片*3包"},{"leftime":3600000,"lprice":"79.9","price":"95.5","snum":"10","id":"5","pic":"http://127.0.0.1:8080/Shop/image/home/homelimit/limit5.jpg","title":"菲丽洁30g小绵羊油滋润霜"}]
     * success : 0
     * homeBanner : [{"id":"0","pic":"http://127.0.0.1:8080/Shop/image/home/homebanner/homebanner0.jpg"},{"id":"1","pic":"http://127.0.0.1:8080/Shop/image/home/homebanner/homebanner1.jpg"},{"id":"2","pic":"http://127.0.0.1:8080/Shop/image/home/homebanner/homebanner2.jpg"},{"id":"3","pic":"http://127.0.0.1:8080/Shop/image/home/homebanner/homebanner3.jpg"},{"id":"4","pic":"http://127.0.0.1:8080/Shop/image/home/homebanner/homebanner4.jpg"}]
     * starmost : [{"sname":"菲丽洁30g小绵羊油滋润霜","count":"1","id":"0","pic":"http://127.0.0.1:8080/Shop/image/home/homelimit/limit5.jpg","sid":"211142"},{"sname":"界界乐乳酸菌(草莓味)","count":"6","id":"1","pic":"http://127.0.0.1:8080/Shop/image/shop/shop2-0.jpg","sid":"238758"}]
     */

    private String success;
    /**
     * leftime : 3600000
     * lprice : 89
     * price : 136
     * snum : 60
     * id : 0
     * pic : http://127.0.0.1:8080/Shop/image/home/homelimit/limit0.jpg
     * title : 新安怡400ml婴儿二合一洗发沐浴露SCF51341
     */

    private List<LimitBuyBean> limitBuy;
    /**
     * id : 0
     * pic : http://127.0.0.1:8080/Shop/image/home/homebanner/homebanner0.jpg
     */

    private List<HomeBannerBean> homeBanner;
    /**
     * sname : 菲丽洁30g小绵羊油滋润霜
     * count : 1
     * id : 0
     * pic : http://127.0.0.1:8080/Shop/image/home/homelimit/limit5.jpg
     * sid : 211142
     */

    private List<StarmostBean> starmost;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<LimitBuyBean> getLimitBuy() {
        return limitBuy;
    }

    public void setLimitBuy(List<LimitBuyBean> limitBuy) {
        this.limitBuy = limitBuy;
    }

    public List<HomeBannerBean> getHomeBanner() {
        return homeBanner;
    }

    public void setHomeBanner(List<HomeBannerBean> homeBanner) {
        this.homeBanner = homeBanner;
    }

    public List<StarmostBean> getStarmost() {
        return starmost;
    }

    public void setStarmost(List<StarmostBean> starmost) {
        this.starmost = starmost;
    }

    public static class LimitBuyBean {
        private int leftime;
        private String lprice;
        private String price;
        private String snum;
        private String id;
        private String pic;
        private String title;
        private String sid;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public int getLeftime() {
            return leftime;
        }

        public void setLeftime(int leftime) {
            this.leftime = leftime;
        }

        public String getLprice() {
            return lprice;
        }

        public void setLprice(String lprice) {
            this.lprice = lprice;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSnum() {
            return snum;
        }

        public void setSnum(String snum) {
            this.snum = snum;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class HomeBannerBean {
        private String id;
        private String pic;
        private String sid;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }


    }

    public static class StarmostBean {
        private String sname;
        private String count;
        private String id;
        private String pic;
        private String sid;

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }
    }
}
