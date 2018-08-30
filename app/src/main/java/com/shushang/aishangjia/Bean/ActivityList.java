package com.shushang.aishangjia.Bean;

import java.util.List;

/**
 * Created by YD on 2018/8/8.
 */

public class ActivityList {


    /**
     * ret : 200
     * msg : success
     * data : null
     * dataList : [{"activityId":"402880b765179cb6016517cc578e0016","activityName":"安阳万达母亲节送大礼","roleType":"0","roleName":"签到员","startTime":"2018-08-09 00:00:08","endTime":"2018-09-01 00:00:08"},{"activityId":"402880b76518f25a0165190291de0007","activityName":"安阳万达618送大礼","roleType":"2","roleName":"礼品发放员","startTime":"2018-08-09 00:00:42","endTime":"2018-08-15 23:55:42"}]
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
         * activityId : 402880b765179cb6016517cc578e0016
         * activityName : 安阳万达母亲节送大礼
         * roleType : 0
         * roleName : 签到员
         * startTime : 2018-08-09 00:00:08
         * endTime : 2018-09-01 00:00:08
         */

        private String activityId;
        private String activityName;
        private String roleType;
        private String roleName;
        private String startTime;
        private String endTime;

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public String getRoleType() {
            return roleType;
        }

        public void setRoleType(String roleType) {
            this.roleType = roleType;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
    }
}
