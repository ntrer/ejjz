package com.shushang.aishangjia.Bean;

import java.util.List;

/**
 * Created by YD on 2018/8/6.
 */

public class SignList {


    /**
     * ret : 200
     * msg : success
     * data : null
     * dataList : [{"token_id":null,"zhaopian":"http://192.168.0.113:8999/fileController.do?method=showimage&id=&token_id=2671c5ea103d4609b60aca1d7f69719a","qdsj":"2019-11-11 10:52:23","qddz":"河南省安阳市文峰区中华路29号靠近万达嘉华酒店","qdzb":null,"qdlh":"720","targetNum":"100","doNum":null,"doRate":null,"efficiencyRatio":null}]
     * intcurrentPage : 1
     * intpageSize : 10
     * intmaxCount : 1
     * intmaxPage : 1
     */

    private String ret;
    private String msg;
    private Object data;
    private int intcurrentPage;
    private int intpageSize;
    private int intmaxCount;
    private int intmaxPage;
    private List<DataListBean> dataList;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getIntcurrentPage() {
        return intcurrentPage;
    }

    public void setIntcurrentPage(int intcurrentPage) {
        this.intcurrentPage = intcurrentPage;
    }

    public int getIntpageSize() {
        return intpageSize;
    }

    public void setIntpageSize(int intpageSize) {
        this.intpageSize = intpageSize;
    }

    public int getIntmaxCount() {
        return intmaxCount;
    }

    public void setIntmaxCount(int intmaxCount) {
        this.intmaxCount = intmaxCount;
    }

    public int getIntmaxPage() {
        return intmaxPage;
    }

    public void setIntmaxPage(int intmaxPage) {
        this.intmaxPage = intmaxPage;
    }

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * token_id : null
         * zhaopian : http://192.168.0.113:8999/fileController.do?method=showimage&id=&token_id=2671c5ea103d4609b60aca1d7f69719a
         * qdsj : 2019-11-11 10:52:23
         * qddz : 河南省安阳市文峰区中华路29号靠近万达嘉华酒店
         * qdzb : null
         * qdlh : 720
         * targetNum : 100
         * doNum : null
         * doRate : null
         * efficiencyRatio : null
         */

        private Object token_id;
        private String zhaopian;
        private String qdsj;
        private String qddz;
        private Object qdzb;
        private String qdlh;
        private String targetNum;
        private Object doNum;
        private Object doRate;
        private Object efficiencyRatio;

        public Object getToken_id() {
            return token_id;
        }

        public void setToken_id(Object token_id) {
            this.token_id = token_id;
        }

        public String getZhaopian() {
            return zhaopian;
        }

        public void setZhaopian(String zhaopian) {
            this.zhaopian = zhaopian;
        }

        public String getQdsj() {
            return qdsj;
        }

        public void setQdsj(String qdsj) {
            this.qdsj = qdsj;
        }

        public String getQddz() {
            return qddz;
        }

        public void setQddz(String qddz) {
            this.qddz = qddz;
        }

        public Object getQdzb() {
            return qdzb;
        }

        public void setQdzb(Object qdzb) {
            this.qdzb = qdzb;
        }

        public String getQdlh() {
            return qdlh;
        }

        public void setQdlh(String qdlh) {
            this.qdlh = qdlh;
        }

        public String getTargetNum() {
            return targetNum;
        }

        public void setTargetNum(String targetNum) {
            this.targetNum = targetNum;
        }

        public Object getDoNum() {
            return doNum;
        }

        public void setDoNum(Object doNum) {
            this.doNum = doNum;
        }

        public Object getDoRate() {
            return doRate;
        }

        public void setDoRate(Object doRate) {
            this.doRate = doRate;
        }

        public Object getEfficiencyRatio() {
            return efficiencyRatio;
        }

        public void setEfficiencyRatio(Object efficiencyRatio) {
            this.efficiencyRatio = efficiencyRatio;
        }
    }
}
