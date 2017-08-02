package com.admin.exe.shop.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2017/5/20.
 */
@Table(name="user")
public class UserEntity {

    /**
     * uid : 18275417177
     * uhead :
     * uname : 毛毛桑
     * usex : 男
     * upwd :
     */
    @Column(name = "id",isId = true)
    private int id;
    @Column(name = "uid" )
    private String uid;
    @Column(name="uhead")
    private String uhead;
    @Column(name="uname")
    private String uname;
    @Column(name="usex")
    private String usex;
    @Column(name="upwd")
    private String upwd;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUhead() {
        return uhead;
    }

    public void setUhead(String uhead) {
        this.uhead = uhead;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUsex() {
        return usex;
    }

    public void setUsex(String usex) {
        this.usex = usex;
    }

    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "uid='" + uid + '\'' +
                ", uhead='" + uhead + '\'' +
                ", uname='" + uname + '\'' +
                ", usex='" + usex + '\'' +
                ", upwd='" + upwd + '\'' +
                '}';
    }
}
