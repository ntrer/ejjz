package com.shushang.aishangjia.Bean;

import java.util.List;

/**
 * Created by YD on 2018/7/24.
 */

public class MoneyPeople {


    /**
     * ret : 200
     * msg : success
     * data : null
     * dataList : [{"order_id":"402880b764cecf980164cf297a6e0038","user_id":"402880b764bb557a0164bb5ae7a30000","user_name":"张三","user_phone":"13131313131","merchant_id":null,"merchant_code":"1","merchant_name":null,"money":100,"xdsj":"2018-07-25 09:59:19"},{"order_id":"402880b764cf3fd60164cf61fe07000f","user_id":"402880b764bb557a0164bb5ae7a30000","user_name":"张三","user_phone":"13131313131","merchant_id":null,"merchant_code":"1","merchant_name":null,"money":500,"xdsj":"2018-07-25 11:01:03"},{"order_id":"402880b764cf3fd60164cf669cfd0013","user_id":"402880b764bb557a0164bb5ae7a30000","user_name":"张三","user_phone":"13131313131","merchant_id":null,"merchant_code":"1","merchant_name":null,"money":11,"xdsj":"2018-07-25 11:06:06"},{"order_id":"402880b764cf3fd60164cf6704b20014","user_id":"402880b764bb557a0164bb5ae7a30000","user_name":"张三","user_phone":"13131313131","merchant_id":null,"merchant_code":"1","merchant_name":null,"money":661,"xdsj":"2018-07-25 11:06:32"}]
     * intcurrentPage : 0
     * intpageSize : 0
     * intmaxCount : 0
     * intmaxPage : 0
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
         * order_id : 402880b764cecf980164cf297a6e0038
         * user_id : 402880b764bb557a0164bb5ae7a30000
         * user_name : 张三
         * user_phone : 13131313131
         * merchant_id : null
         * merchant_code : 1
         * merchant_name : null
         * money : 100.0
         * xdsj : 2018-07-25 09:59:19
         */

        private String order_id;
        private String user_id;
        private String user_name;
        private String user_phone;
        private Object merchant_id;
        private String merchant_code;
        private Object merchant_name;
        private double money;
        private String xdsj;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public Object getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(Object merchant_id) {
            this.merchant_id = merchant_id;
        }

        public String getMerchant_code() {
            return merchant_code;
        }

        public void setMerchant_code(String merchant_code) {
            this.merchant_code = merchant_code;
        }

        public Object getMerchant_name() {
            return merchant_name;
        }

        public void setMerchant_name(Object merchant_name) {
            this.merchant_name = merchant_name;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getXdsj() {
            return xdsj;
        }

        public void setXdsj(String xdsj) {
            this.xdsj = xdsj;
        }
    }
}
