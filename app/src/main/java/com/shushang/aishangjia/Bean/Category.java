package com.shushang.aishangjia.Bean;

/**
 * Created by YD on 2018/7/19.
 */

public class Category {


    /**
     * ret : 200
     * msg : success
     * data : {"totalCustomer":5,"unregisterNum":4,"registerNum":1,"registerRate":0.2,"totalMerchant":2}
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
         * totalCustomer : 5
         * unregisterNum : 4
         * registerNum : 1
         * registerRate : 0.2
         * totalMerchant : 2
         */

        private int totalCustomer;
        private int unregisterNum;
        private int registerNum;
        private double registerRate;
        private int totalMerchant;

        public int getTotalCustomer() {
            return totalCustomer;
        }

        public void setTotalCustomer(int totalCustomer) {
            this.totalCustomer = totalCustomer;
        }

        public int getUnregisterNum() {
            return unregisterNum;
        }

        public void setUnregisterNum(int unregisterNum) {
            this.unregisterNum = unregisterNum;
        }

        public int getRegisterNum() {
            return registerNum;
        }

        public void setRegisterNum(int registerNum) {
            this.registerNum = registerNum;
        }

        public double getRegisterRate() {
            return registerRate;
        }

        public void setRegisterRate(double registerRate) {
            this.registerRate = registerRate;
        }

        public int getTotalMerchant() {
            return totalMerchant;
        }

        public void setTotalMerchant(int totalMerchant) {
            this.totalMerchant = totalMerchant;
        }
    }
}
