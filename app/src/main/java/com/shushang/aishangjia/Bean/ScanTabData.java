package com.shushang.aishangjia.Bean;

/**
 * Created by YD on 2018/8/6.
 */

public class ScanTabData {

    /**
     * ret : 200
     * msg : success
     * data : {"customerActionId":null,"customerName":null,"customerMobile":null,"customerSex":null,"merchantName":null,"cjsj":null,"customerNum":"5","acCustomerNum":"0","acRate":"0.0","refuseCustomer":"0"}
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
         * customerActionId : null
         * customerName : null
         * customerMobile : null
         * customerSex : null
         * merchantName : null
         * cjsj : null
         * customerNum : 5
         * acCustomerNum : 0
         * acRate : 0.0
         * refuseCustomer : 0
         */

        private Object customerActionId;
        private Object customerName;
        private Object customerMobile;
        private Object customerSex;
        private Object merchantName;
        private Object cjsj;
        private String customerNum;
        private String acCustomerNum;
        private String acRate;
        private String refuseCustomer;

        public Object getCustomerActionId() {
            return customerActionId;
        }

        public void setCustomerActionId(Object customerActionId) {
            this.customerActionId = customerActionId;
        }

        public Object getCustomerName() {
            return customerName;
        }

        public void setCustomerName(Object customerName) {
            this.customerName = customerName;
        }

        public Object getCustomerMobile() {
            return customerMobile;
        }

        public void setCustomerMobile(Object customerMobile) {
            this.customerMobile = customerMobile;
        }

        public Object getCustomerSex() {
            return customerSex;
        }

        public void setCustomerSex(Object customerSex) {
            this.customerSex = customerSex;
        }

        public Object getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(Object merchantName) {
            this.merchantName = merchantName;
        }

        public Object getCjsj() {
            return cjsj;
        }

        public void setCjsj(Object cjsj) {
            this.cjsj = cjsj;
        }

        public String getCustomerNum() {
            return customerNum;
        }

        public void setCustomerNum(String customerNum) {
            this.customerNum = customerNum;
        }

        public String getAcCustomerNum() {
            return acCustomerNum;
        }

        public void setAcCustomerNum(String acCustomerNum) {
            this.acCustomerNum = acCustomerNum;
        }

        public String getAcRate() {
            return acRate;
        }

        public void setAcRate(String acRate) {
            this.acRate = acRate;
        }

        public String getRefuseCustomer() {
            return refuseCustomer;
        }

        public void setRefuseCustomer(String refuseCustomer) {
            this.refuseCustomer = refuseCustomer;
        }
    }
}
