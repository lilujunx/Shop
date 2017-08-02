package com.admin.exe.shop.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/26.
 */

public class RecordEntity {
    public static final int WEIFAHUO=0;
    public static final int DAISHOUHUO=1;
    public static final int WEIPINGJIA=2;
    public static final int YISHOUHUO=3;
    public static final int YIPINGJIA=4;


    /**
     * records : [{"cphone":"18275417177","address":"xxxxxxxxxxxxxx","uname":"","lid":"l123456781","cname":"bbb","bnum":"1","pic":"http://172.27.35.1:8080/Shop/image/shop/shop0-0.jpg","sid":"176324","rstatus":"-1","ltime":"1495380622673","uid":"18275417177","estatus":"-1","sname":"合生元300g婴幼儿大米粉（6-36个月）","bprice":"40","sstatus":"-1"},{"cphone":"18275417177","address":"xxxxxxxxxxxxxx","uname":"","lid":"l123456780","cname":"aaa","bnum":"1","pic":"http://172.27.35.1:8080/Shop/image/home/homelimit/limit1.jpg","sid":"416081","rstatus":"-1","ltime":"1495380622673","uid":"18275417177","estatus":"-1","sname":"BOOTO 260ML sweet系列爱心条纹双柄吸管保温水壶","bprice":"30","sstatus":"0"},{"cphone":"18275417177","address":"xxxxxxxxxxxxxx","uname":"","lid":"l123456783","cname":"aaa","bnum":"1","pic":"http://172.27.35.1:8080/Shop/image/shop/shop0-0.jpg","sid":"176324","rstatus":"-1","ltime":"1495380622673","uid":"18275417177","estatus":"-1","sname":"合生元300g婴幼儿大米粉（6-36个月）","bprice":"40","sstatus":"0"}]
     * success : 1
     */

    private String success;
    /**
     * cphone : 18275417177
     * address : xxxxxxxxxxxxxx
     * uname :
     * lid : l123456781
     * cname : bbb
     * bnum : 1
     * pic : http://172.27.35.1:8080/Shop/image/shop/shop0-0.jpg
     * sid : 176324
     * rstatus : -1
     * ltime : 1495380622673
     * uid : 18275417177
     * estatus : -1
     * sname : 合生元300g婴幼儿大米粉（6-36个月）
     * bprice : 40
     * sstatus : -1
     */

    private List<RecordsBean> records;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean implements Serializable{
        private String cphone;
        private String address;
        private String uname;
        private String lid;
        private String cname;
        private String bnum;
        private String pic;
        private String sid;
        private String rstatus;
        private String ltime;
        private String uid;
        private String estatus;
        private String sname;
        private String bprice;
        private String sstatus;
        private int mMyType;


        public int getMyType() {
            return mMyType;
        }

        public void setMyType(int mytype) {
            mMyType = mytype;
        }

        public String getCphone() {
            return cphone;
        }

        public void setCphone(String cphone) {
            this.cphone = cphone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getLid() {
            return lid;
        }

        public void setLid(String lid) {
            this.lid = lid;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getBnum() {
            return bnum;
        }

        public void setBnum(String bnum) {
            this.bnum = bnum;
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

        public String getRstatus() {
            return rstatus;
        }

        public void setRstatus(String rstatus) {
            this.rstatus = rstatus;
        }

        public String getLtime() {
            return ltime;
        }

        public void setLtime(String ltime) {
            this.ltime = ltime;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getEstatus() {
            return estatus;
        }

        public void setEstatus(String estatus) {
            this.estatus = estatus;
        }

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }

        public String getBprice() {
            return bprice;
        }

        public void setBprice(String bprice) {
            this.bprice = bprice;
        }

        public String getSstatus() {
            return sstatus;
        }

        public void setSstatus(String sstatus) {
            this.sstatus = sstatus;
        }

        @Override
        public String toString() {
            return "RecordsBean{" +
                    "cphone='" + cphone + '\'' +
                    ", address='" + address + '\'' +
                    ", uname='" + uname + '\'' +
                    ", lid='" + lid + '\'' +
                    ", cname='" + cname + '\'' +
                    ", bnum='" + bnum + '\'' +
                    ", pic='" + pic + '\'' +
                    ", sid='" + sid + '\'' +
                    ", rstatus='" + rstatus + '\'' +
                    ", ltime='" + ltime + '\'' +
                    ", uid='" + uid + '\'' +
                    ", estatus='" + estatus + '\'' +
                    ", sname='" + sname + '\'' +
                    ", bprice='" + bprice + '\'' +
                    ", sstatus='" + sstatus + '\'' +
                    '}';
        }
    }
}
