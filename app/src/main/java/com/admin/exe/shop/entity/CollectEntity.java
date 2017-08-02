package com.admin.exe.shop.entity;

import java.util.List;

public class CollectEntity {
    private String success;
    private List<Collect> collects;

    @Override
    public String toString() {
        return "CollectResEntity [success=" + success + ", collects=" + collects + "]";
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Collect> getCollects() {
        return collects;
    }

    public void setCollects(List<Collect> collects) {
        this.collects = collects;
    }

    public static class Collect {
        private String cid;
        private String uid;
        private String sid;
        private String sname;
        private String sprice;
        private String spic;
        private String ctime;


        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }

        public String getSprice() {
            return sprice;
        }

        public void setSprice(String sprice) {
            this.sprice = sprice;
        }

        public String getSpic() {
            return spic;
        }

        public void setSpic(String spic) {
            this.spic = spic;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        @Override
        public String toString() {
            return "Collect [uid=" + uid + ", sid=" + sid + ", sname=" + sname + ", sprice=" + sprice + ", spic=" + spic
                    + ", ctime=" + ctime + "]";
        }

    }
}
