package com.admin.exe.shop.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/5/16.
 */

public class EvaluteEntity {

    /**
     * evaluteEntities : [{"detatil":"","star":"5","uhead":"","uname":"毛毛桑"},{"detatil":"","star":"5","uhead":"","uname":"毛毛桑"},{"detatil":"","star":"5","uhead":"","uname":"毛毛桑"},{"detatil":"","star":"5","uhead":"","uname":"毛毛桑"},{"detatil":"","star":"5","uhead":"","uname":"毛毛桑"},{"detatil":"","star":"5","uhead":"","uname":"毛毛桑"}]
     * success : 0
     */

    private String success;
    /**
     * detatil :
     * star : 5
     * uhead :
     * uname : 毛毛桑
     */

    private List<EvaluteEntitiesBean> evaluteEntities;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<EvaluteEntitiesBean> getEvaluteEntities() {
        return evaluteEntities;
    }

    public void setEvaluteEntities(List<EvaluteEntitiesBean> evaluteEntities) {
        this.evaluteEntities = evaluteEntities;
    }

    public static class EvaluteEntitiesBean {
        private String detatil;
        private String star;
        private String uhead;
        private String uname;
        private String time;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDetatil() {
            return detatil;
        }

        public void setDetatil(String detatil) {
            this.detatil = detatil;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
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

        @Override
        public String toString() {
            return "EvaluteEntitiesBean{" +
                    "detatil='" + detatil + '\'' +
                    ", star='" + star + '\'' +
                    ", uhead='" + uhead + '\'' +
                    ", uname='" + uname + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }

        public void setUname(String uname) {


            this.uname = uname;
        }
    }

    @Override
    public String toString() {
        return "EvaluteEntity{" +
                "success='" + success + '\'' +
                ", evaluteEntities=" + evaluteEntities +
                '}';
    }
}
