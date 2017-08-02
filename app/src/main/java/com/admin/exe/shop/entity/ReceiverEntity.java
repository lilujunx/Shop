package com.admin.exe.shop.entity;

import java.io.Serializable;
import java.util.List;

public class ReceiverEntity {

    private String success;
    private List<Receiver> receivers;

    @Override
    public String toString() {
        return "ReceiverResEntity [success=" + success + ", receivers=" + receivers + "]";
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Receiver> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<Receiver> receivers) {
        this.receivers = receivers;
    }

    public static class Receiver implements Serializable{
        private String rname;
        private String rphone;
        private String raddress;
        private String uid;
        private String rid;

        public String getRid() {
            return rid;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        public String getRname() {
            return rname;
        }

        public void setRname(String rname) {
            this.rname = rname;
        }

        public String getRphone() {
            return rphone;
        }

        public void setRphone(String rphone) {
            this.rphone = rphone;
        }

        public String getRaddress() {
            return raddress;
        }

        public void setRaddress(String raddress) {
            this.raddress = raddress;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        @Override
        public String toString() {
            return "Receiver [rname=" + rname + ", rphone=" + rphone + ", raddress=" + raddress + ", uid=" + uid + "]";
        }

    }
}
