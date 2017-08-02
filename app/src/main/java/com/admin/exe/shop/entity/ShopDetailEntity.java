package com.admin.exe.shop.entity;

/**
 * Created by Administrator on 2017/5/16.
 */

public class ShopDetailEntity {

    /**
     * spic : http://172.27.35.1:8080/Shop/image/shop/shop4-0.jpg
     * lprice : 28
     * sname : 方广300gAD钙蛋白营养面(6M+)
     * snum : 60
     * stype : 0
     * sprice : 28
     * sid : 016885
     */

    private String spic;
    private String lprice;
    private String sname;
    private String snum;
    private String stype;
    private String sprice;
    private String sid;

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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }


    @Override
    public String toString() {
        return "ShopDetailEntity{" +
                "spic='" + spic + '\'' +
                ", lprice='" + lprice + '\'' +
                ", sname='" + sname + '\'' +
                ", snum='" + snum + '\'' +
                ", stype='" + stype + '\'' +
                ", sprice='" + sprice + '\'' +
                ", sid='" + sid + '\'' +
                '}';
    }
}
