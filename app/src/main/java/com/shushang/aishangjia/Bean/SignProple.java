package com.shushang.aishangjia.Bean;

import java.util.List;

/**
 * Created by YD on 2018/7/19.
 */

public class SignProple {


    /**
     * ret : 200
     * msg : success
     * data : null
     * dataList : [{"user_id":"402880b7651cd14801651d6aa7450061","user_name":"徐二","user_phone":"13939991111","user_sex":null,"merchant_id":"402880b7651c9c9801651ca153a00008","merchant_name":"数尚家具","merchant_code":"ssjj","qdsj":"2018-08-09 14:55:12","lqsuccess":null,"lqsj":null},{"user_id":"402880b7651cd14801651d69c1c5005a","user_name":"徐一","user_phone":"13939990000","user_sex":null,"merchant_id":"402880b7651c9c9801651ca153a00008","merchant_name":"数尚家具","merchant_code":"ssjj","qdsj":"2018-08-09 14:52:21","lqsuccess":null,"lqsj":null}]
     * intcurrentPage : 1
     * intpageSize : 10
     * intmaxCount : 2
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
         * user_id : 402880b7651cd14801651d6aa7450061
         * user_name : 徐二
         * user_phone : 13939991111
         * user_sex : null
         * merchant_id : 402880b7651c9c9801651ca153a00008
         * merchant_name : 数尚家具
         * merchant_code : ssjj
         * qdsj : 2018-08-09 14:55:12
         * lqsuccess : null
         * lqsj : null
         */

        private String user_id;
        private String user_name;
        private String user_phone;
        private Object user_sex;
        private String merchant_id;
        private String merchant_name;
        private String merchant_code;
        private String qdsj;
        private Object lqsuccess;
        private Object lqsj;

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

        public Object getUser_sex() {
            return user_sex;
        }

        public void setUser_sex(Object user_sex) {
            this.user_sex = user_sex;
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

        public String getQdsj() {
            return qdsj;
        }

        public void setQdsj(String qdsj) {
            this.qdsj = qdsj;
        }

        public Object getLqsuccess() {
            return lqsuccess;
        }

        public void setLqsuccess(Object lqsuccess) {
            this.lqsuccess = lqsuccess;
        }

        public Object getLqsj() {
            return lqsj;
        }

        public void setLqsj(Object lqsj) {
            this.lqsj = lqsj;
        }
    }
}
