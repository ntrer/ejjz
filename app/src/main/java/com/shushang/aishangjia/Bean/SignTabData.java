package com.shushang.aishangjia.Bean;

/**
 * Created by YD on 2018/8/6.
 */

public class SignTabData {

    /**
     * ret : 200
     * msg : success
     * data : {"token_id":null,"zhaopian":null,"qdsj":null,"qddz":null,"qdzb":null,"qdlh":null,"targetNum":"100","doNum":"3","doRate":"0.03","efficiencyRatio":"1.6666666666666667"}
     * dataList : null
     * intcurrentPage : 0
     * intpageSize : 0
     * intmaxCount : 0
     * intmaxPage : 0
     */

    private String ret;
    private String msg;
    private DataBean data;
    private Object dataList;
    private int intcurrentPage;
    private int intpageSize;
    private int intmaxCount;
    private int intmaxPage;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getDataList() {
        return dataList;
    }

    public void setDataList(Object dataList) {
        this.dataList = dataList;
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

    public static class DataBean {
        /**
         * token_id : null
         * zhaopian : null
         * qdsj : null
         * qddz : null
         * qdzb : null
         * qdlh : null
         * targetNum : 100
         * doNum : 3
         * doRate : 0.03
         * efficiencyRatio : 1.6666666666666667
         */

        private Object token_id;
        private Object zhaopian;
        private Object qdsj;
        private Object qddz;
        private Object qdzb;
        private Object qdlh;
        private String targetNum;
        private String doNum;
        private String doRate;
        private String efficiencyRatio;

        public Object getToken_id() {
            return token_id;
        }

        public void setToken_id(Object token_id) {
            this.token_id = token_id;
        }

        public Object getZhaopian() {
            return zhaopian;
        }

        public void setZhaopian(Object zhaopian) {
            this.zhaopian = zhaopian;
        }

        public Object getQdsj() {
            return qdsj;
        }

        public void setQdsj(Object qdsj) {
            this.qdsj = qdsj;
        }

        public Object getQddz() {
            return qddz;
        }

        public void setQddz(Object qddz) {
            this.qddz = qddz;
        }

        public Object getQdzb() {
            return qdzb;
        }

        public void setQdzb(Object qdzb) {
            this.qdzb = qdzb;
        }

        public Object getQdlh() {
            return qdlh;
        }

        public void setQdlh(Object qdlh) {
            this.qdlh = qdlh;
        }

        public String getTargetNum() {
            return targetNum;
        }

        public void setTargetNum(String targetNum) {
            this.targetNum = targetNum;
        }

        public String getDoNum() {
            return doNum;
        }

        public void setDoNum(String doNum) {
            this.doNum = doNum;
        }

        public String getDoRate() {
            return doRate;
        }

        public void setDoRate(String doRate) {
            this.doRate = doRate;
        }

        public String getEfficiencyRatio() {
            return efficiencyRatio;
        }

        public void setEfficiencyRatio(String efficiencyRatio) {
            this.efficiencyRatio = efficiencyRatio;
        }
    }
}
