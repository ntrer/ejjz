package com.xys.libzxing.zxing.Bean;

/**
 * Created by YD on 2018/7/23.
 */

public class UserInfo {

    /**
     * ret : 200
     * msg : success
     * data : {"user_id":null,"user_name":null,"user_phone":null,"address":null,"merchant_id":"1","merchant_name":"商家1","merchant_code":"1","merchant_address":null,"type":"2"}
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
         * user_id : null
         * user_name : null
         * user_phone : null
         * address : null
         * merchant_id : 1
         * merchant_name : 商家1
         * merchant_code : 1
         * merchant_address : null
         * type : 2
         */

        private Object user_id;
        private Object user_name;
        private Object user_phone;
        private Object address;
        private String merchant_id;
        private String merchant_name;
        private String merchant_code;
        private Object merchant_address;
        private String type;

        public Object getUser_id() {
            return user_id;
        }

        public void setUser_id(Object user_id) {
            this.user_id = user_id;
        }

        public Object getUser_name() {
            return user_name;
        }

        public void setUser_name(Object user_name) {
            this.user_name = user_name;
        }

        public Object getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(Object user_phone) {
            this.user_phone = user_phone;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public String getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(String merchant_id) {
            this.merchant_id = merchant_id;
        }

        public String getMerchant_name() {
            return merchant_name;
        }

        public void setMerchant_name(String merchant_name) {
            this.merchant_name = merchant_name;
        }

        public String getMerchant_code() {
            return merchant_code;
        }

        public void setMerchant_code(String merchant_code) {
            this.merchant_code = merchant_code;
        }

        public Object getMerchant_address() {
            return merchant_address;
        }

        public void setMerchant_address(Object merchant_address) {
            this.merchant_address = merchant_address;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
