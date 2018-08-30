package com.shushang.aishangjia.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by YD on 2018/8/6.
 */

public class NewPeople implements Serializable {


    /**
     * ret : 200
     * msg : success
     * data : null
     * dataList : [{"type":"1","activityId":null,"activityCode":null,"activityName":null,"username":"牛牛","phone":"18637280068","sex":"女","address":"黑龙江省伊春市翠峦区","sheng_code":"230000","shi_code":"230700","qu_code":"230706","sheng_name":"黑龙江省","shi_name":"伊春市","qu_name":"翠峦区","decorationProgress":"完成","decorationStyle":null,"likecolor":null,"thinkBuyGood":null,"merchantName":"数尚家具","merchantId":"402880b7652300d00165232941a2004b","cjsj":"2018-08-11 08:24:00"}]
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

    public static class DataListBean implements Serializable{
        /**
         * type : 1
         * activityId : null
         * activityCode : null
         * activityName : null
         * username : 牛牛
         * phone : 18637280068
         * sex : 女
         * address : 黑龙江省伊春市翠峦区
         * sheng_code : 230000
         * shi_code : 230700
         * qu_code : 230706
         * sheng_name : 黑龙江省
         * shi_name : 伊春市
         * qu_name : 翠峦区
         * decorationProgress : 完成
         * decorationStyle : null
         * likecolor : null
         * thinkBuyGood : null
         * merchantName : 数尚家具
         * merchantId : 402880b7652300d00165232941a2004b
         * cjsj : 2018-08-11 08:24:00
         */

        private String type;
        private Object activityId;
        private Object activityCode;
        private Object activityName;
        private String username;
        private String phone;
        private String sex;
        private String address;
        private String sheng_code;
        private String shi_code;
        private String qu_code;
        private String sheng_name;
        private String shi_name;
        private String qu_name;
        private String decorationProgress;
        private Object decorationStyle;
        private Object likecolor;
        private Object thinkBuyGood;
        private String merchantName;
        private String merchantId;
        private String cjsj;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getActivityId() {
            return activityId;
        }

        public void setActivityId(Object activityId) {
            this.activityId = activityId;
        }

        public Object getActivityCode() {
            return activityCode;
        }

        public void setActivityCode(Object activityCode) {
            this.activityCode = activityCode;
        }

        public Object getActivityName() {
            return activityName;
        }

        public void setActivityName(Object activityName) {
            this.activityName = activityName;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSheng_code() {
            return sheng_code;
        }

        public void setSheng_code(String sheng_code) {
            this.sheng_code = sheng_code;
        }

        public String getShi_code() {
            return shi_code;
        }

        public void setShi_code(String shi_code) {
            this.shi_code = shi_code;
        }

        public String getQu_code() {
            return qu_code;
        }

        public void setQu_code(String qu_code) {
            this.qu_code = qu_code;
        }

        public String getSheng_name() {
            return sheng_name;
        }

        public void setSheng_name(String sheng_name) {
            this.sheng_name = sheng_name;
        }

        public String getShi_name() {
            return shi_name;
        }

        public void setShi_name(String shi_name) {
            this.shi_name = shi_name;
        }

        public String getQu_name() {
            return qu_name;
        }

        public void setQu_name(String qu_name) {
            this.qu_name = qu_name;
        }

        public String getDecorationProgress() {
            return decorationProgress;
        }

        public void setDecorationProgress(String decorationProgress) {
            this.decorationProgress = decorationProgress;
        }

        public Object getDecorationStyle() {
            return decorationStyle;
        }

        public void setDecorationStyle(Object decorationStyle) {
            this.decorationStyle = decorationStyle;
        }

        public Object getLikecolor() {
            return likecolor;
        }

        public void setLikecolor(Object likecolor) {
            this.likecolor = likecolor;
        }

        public Object getThinkBuyGood() {
            return thinkBuyGood;
        }

        public void setThinkBuyGood(Object thinkBuyGood) {
            this.thinkBuyGood = thinkBuyGood;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getCjsj() {
            return cjsj;
        }

        public void setCjsj(String cjsj) {
            this.cjsj = cjsj;
        }
    }
}
