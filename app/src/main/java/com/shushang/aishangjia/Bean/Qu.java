package com.shushang.aishangjia.Bean;

import java.util.List;

/**
 * Created by YD on 2018/8/7.
 */

public class Qu {


    /**
     * ret : 200
     * msg : success
     * data : null
     * dataList : [{"id":"410501","mingcheng":"市辖区","code":"410501","paixu":0,"shihao":"410500","del":"0"},{"id":"410502","mingcheng":"文峰区","code":"410502","paixu":0,"shihao":"410500","del":"0"},{"id":"410503","mingcheng":"北关区","code":"410503","paixu":0,"shihao":"410500","del":"0"},{"id":"410505","mingcheng":"殷都区","code":"410505","paixu":0,"shihao":"410500","del":"0"},{"id":"410506","mingcheng":"龙安区","code":"410506","paixu":0,"shihao":"410500","del":"0"},{"id":"410522","mingcheng":"安阳县","code":"410522","paixu":0,"shihao":"410500","del":"0"},{"id":"410523","mingcheng":"汤阴县","code":"410523","paixu":0,"shihao":"410500","del":"0"},{"id":"410526","mingcheng":"滑县","code":"410526","paixu":0,"shihao":"410500","del":"0"},{"id":"410527","mingcheng":"内黄县","code":"410527","paixu":0,"shihao":"410500","del":"0"},{"id":"410581","mingcheng":"林州市","code":"410581","paixu":0,"shihao":"410500","del":"0"}]
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
         * id : 410501
         * mingcheng : 市辖区
         * code : 410501
         * paixu : 0
         * shihao : 410500
         * del : 0
         */

        private String id;
        private String mingcheng;
        private String code;
        private int paixu;
        private String shihao;
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

        public String getShihao() {
            return shihao;
        }

        public void setShihao(String shihao) {
            this.shihao = shihao;
        }

        public String getDel() {
            return del;
        }

        public void setDel(String del) {
            this.del = del;
        }
    }
}
