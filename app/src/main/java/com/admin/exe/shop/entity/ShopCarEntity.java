package com.admin.exe.shop.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2017/5/28.
 */
@Table(name = "shopcar")
public class ShopCarEntity {

    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "price")
    private String price;
    @Column(name = "totalprice")
    private String totalprice;
    @Column(name = "num")
    private String num;
    @Column(name = "time")
    private String time;
    @Column(name = "pic")
    private String pic;


    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ShopCarEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", totalprice='" + totalprice + '\'' +
                ", num='" + num + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
