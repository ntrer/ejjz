package com.shushang.aishangjia.Bean;

import java.util.List;

/**
 * Created by YD on 2018/8/7.
 */

public class Sheng {

    /**
     * ret : 200
     * msg : success
     * data : null
     * dataList : [{"id":"110000","mingcheng":"北京市","code":"110000","paixu":0,"del":"0"},{"id":"120000","mingcheng":"天津市","code":"120000","paixu":0,"del":"0"},{"id":"130000","mingcheng":"河北省","code":"130000","paixu":0,"del":"0"},{"id":"140000","mingcheng":"山西省","code":"140000","paixu":0,"del":"0"},{"id":"150000","mingcheng":"内蒙古自治区","code":"150000","paixu":0,"del":"0"},{"id":"210000","mingcheng":"辽宁省","code":"210000","paixu":0,"del":"0"},{"id":"220000","mingcheng":"吉林省","code":"220000","paixu":0,"del":"0"},{"id":"230000","mingcheng":"黑龙江省","code":"230000","paixu":0,"del":"0"},{"id":"310000","mingcheng":"上海市","code":"310000","paixu":0,"del":"0"},{"id":"320000","mingcheng":"江苏省","code":"320000","paixu":0,"del":"0"},{"id":"330000","mingcheng":"浙江省","code":"330000","paixu":0,"del":"0"},{"id":"340000","mingcheng":"安徽省","code":"340000","paixu":0,"del":"0"},{"id":"350000","mingcheng":"福建省","code":"350000","paixu":0,"del":"0"},{"id":"360000","mingcheng":"江西省","code":"360000","paixu":0,"del":"0"},{"id":"370000","mingcheng":"山东省","code":"370000","paixu":0,"del":"0"},{"id":"410000","mingcheng":"河南省","code":"410000","paixu":0,"del":"0"},{"id":"420000","mingcheng":"湖北省","code":"420000","paixu":0,"del":"0"},{"id":"430000","mingcheng":"湖南省","code":"430000","paixu":0,"del":"0"},{"id":"440000","mingcheng":"广东省","code":"440000","paixu":0,"del":"0"},{"id":"450000","mingcheng":"广西壮族自治区","code":"450000","paixu":0,"del":"0"},{"id":"460000","mingcheng":"海南省","code":"460000","paixu":0,"del":"0"},{"id":"500000","mingcheng":"重庆市","code":"500000","paixu":0,"del":"0"},{"id":"510000","mingcheng":"四川省","code":"510000","paixu":0,"del":"0"},{"id":"520000","mingcheng":"贵州省","code":"520000","paixu":0,"del":"0"},{"id":"530000","mingcheng":"云南省","code":"530000","paixu":0,"del":"0"},{"id":"540000","mingcheng":"西藏自治区","code":"540000","paixu":0,"del":"0"},{"id":"610000","mingcheng":"陕西省","code":"610000","paixu":0,"del":"0"},{"id":"620000","mingcheng":"甘肃省","code":"620000","paixu":0,"del":"0"},{"id":"630000","mingcheng":"青海省","code":"630000","paixu":0,"del":"0"},{"id":"640000","mingcheng":"宁夏回族自治区","code":"640000","paixu":0,"del":"0"},{"id":"650000","mingcheng":"新疆维吾尔自治区","code":"650000","paixu":0,"del":"0"},{"id":"710000","mingcheng":"台湾省","code":"710000","paixu":0,"del":"0"},{"id":"810000","mingcheng":"香港特别行政区","code":"810000","paixu":0,"del":"0"},{"id":"820000","mingcheng":"澳门特别行政区","code":"820000","paixu":0,"del":"0"}]
     * currentPage : 0
     * totalCount : 0
     * maxPage : 0
     * pageSize : 0
     */

    private String ret;
    private String msg;
    private Object data;
    private int currentPage;
    private int totalCount;
    private int maxPage;
    private int pageSize;
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

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * id : 110000
         * mingcheng : 北京市
         * code : 110000
         * paixu : 0
         * del : 0
         */

        private String id;
        private String mingcheng;
        private String code;
        private int paixu;
        private String del;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMingcheng() {
            return mingcheng;
        }

        public void setMingcheng(String mingcheng) {
            this.mingcheng = mingcheng;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getPaixu() {
            return paixu;
        }

        public void setPaixu(int paixu) {
            this.paixu = paixu;
        }

        public String getDel() {
            return del;
        }

        public void setDel(String del) {
            this.del = del;
        }
    }
}
