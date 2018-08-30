package com.shushang.aishangjia.Bean;

import java.util.List;

/**
 * Created by YD on 2018/8/7.
 */

public class Activity {


    /**
     * ret : 200
     * msg : success
     * data : null
     * dataList : [{"activityId":"402880b76511fd0c0165120a81c80016","activityCode":1030,"activityName":"全民大抢购","eventStart":1533398415000,"eventEnd":1535644815000,"sellCardStart":1533484815000,"sellCardEnd":1533916815000,"sceneStart":1533605992000,"sceneEnd":1535644815000,"chuangjianren":"asdfghjkl123qwertg","xiugairen":"asdfghjkl123qwertg","cjsj":1533606003000,"xgsj":1533606003000,"del":"0","active":"1","merchantId":"402880b764ef440c0164ef563be60041","merchants":null},{"activityId":"402880b76511fd0c0165120b4571001d","activityCode":1031,"activityName":"购物马拉松","eventStart":1533398419000,"eventEnd":1535644819000,"sellCardStart":1533484819000,"sellCardEnd":1533916819000,"sceneStart":1533606047000,"sceneEnd":1535644819000,"chuangjianren":"asdfghjkl123qwertg","xiugairen":"asdfghjkl123qwertg","cjsj":1533606053000,"xgsj":1533606053000,"del":"0","active":"1","merchantId":"402880b764ef440c0164ef563be60041","merchants":null},{"activityId":"402880b76511fd0c0165120f64110029","activityCode":1033,"activityName":"秋季买就送","eventStart":1533606298000,"eventEnd":1535644800000,"sellCardStart":1533657632000,"sellCardEnd":1533916832000,"sceneStart":1533606316000,"sceneEnd":1535644832000,"chuangjianren":"asdfghjkl123qwertg","xiugairen":"asdfghjkl123qwertg","cjsj":1533606323000,"xgsj":1533606323000,"del":"0","active":"1","merchantId":"402880b764ef440c0164ef563be60041","merchants":null},{"activityId":"402880b76511fd0c0165127961fe00dc","activityCode":1034,"activityName":"郑州家装大促","eventStart":1533398404000,"eventEnd":1535644804000,"sellCardStart":1533613254000,"sellCardEnd":1533916804000,"sceneStart":1533657604000,"sceneEnd":1535644804000,"chuangjianren":"asdfghjkl123qwertg","xiugairen":"asdfghjkl123qwertg","cjsj":1533613270000,"xgsj":1533613473000,"del":"0","active":"1","merchantId":"402880b764ef440c0164ef563be60041","merchants":null}]
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
         * activityId : 402880b76511fd0c0165120a81c80016
         * activityCode : 1030
         * activityName : 全民大抢购
         * eventStart : 1533398415000
         * eventEnd : 1535644815000
         * sellCardStart : 1533484815000
         * sellCardEnd : 1533916815000
         * sceneStart : 1533605992000
         * sceneEnd : 1535644815000
         * chuangjianren : asdfghjkl123qwertg
         * xiugairen : asdfghjkl123qwertg
         * cjsj : 1533606003000
         * xgsj : 1533606003000
         * del : 0
         * active : 1
         * merchantId : 402880b764ef440c0164ef563be60041
         * merchants : null
         */

        private String activityId;
        private int activityCode;
        private String activityName;
        private long eventStart;
        private long eventEnd;
        private long sellCardStart;
        private long sellCardEnd;
        private long sceneStart;
        private long sceneEnd;
        private String chuangjianren;
        private String xiugairen;
        private long cjsj;
        private long xgsj;
        private String del;
        private String active;
        private String merchantId;
        private Object merchants;

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public int getActivityCode() {
            return activityCode;
        }

        public void setActivityCode(int activityCode) {
            this.activityCode = activityCode;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public long getEventStart() {
            return eventStart;
        }

        public void setEventStart(long eventStart) {
            this.eventStart = eventStart;
        }

        public long getEventEnd() {
            return eventEnd;
        }

        public void setEventEnd(long eventEnd) {
            this.eventEnd = eventEnd;
        }

        public long getSellCardStart() {
            return sellCardStart;
        }

        public void setSellCardStart(long sellCardStart) {
            this.sellCardStart = sellCardStart;
        }

        public long getSellCardEnd() {
            return sellCardEnd;
        }

        public void setSellCardEnd(long sellCardEnd) {
            this.sellCardEnd = sellCardEnd;
        }

        public long getSceneStart() {
            return sceneStart;
        }

        public void setSceneStart(long sceneStart) {
            this.sceneStart = sceneStart;
        }

        public long getSceneEnd() {
            return sceneEnd;
        }

        public void setSceneEnd(long sceneEnd) {
            this.sceneEnd = sceneEnd;
        }

        public String getChuangjianren() {
            return chuangjianren;
        }

        public void setChuangjianren(String chuangjianren) {
            this.chuangjianren = chuangjianren;
        }

        public String getXiugairen() {
            return xiugairen;
        }

        public void setXiugairen(String xiugairen) {
            this.xiugairen = xiugairen;
        }

        public long getCjsj() {
            return cjsj;
        }

        public void setCjsj(long cjsj) {
            this.cjsj = cjsj;
        }

        public long getXgsj() {
            return xgsj;
        }

        public void setXgsj(long xgsj) {
            this.xgsj = xgsj;
        }

        public String getDel() {
            return del;
        }

        public void setDel(String del) {
            this.del = del;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public Object getMerchants() {
            return merchants;
        }

        public void setMerchants(Object merchants) {
            this.merchants = merchants;
        }
    }
}
