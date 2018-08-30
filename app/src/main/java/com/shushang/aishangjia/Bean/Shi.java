package com.shushang.aishangjia.Bean;

import java.util.List;

/**
 * Created by YD on 2018/8/7.
 */

public class Shi {

    /**
     * ret : 200
     * msg : success
     * data : null
     * dataList : [{"id":"410100","mingcheng":"郑州市","code":"410100","paixu":0,"shenghao":"410000","del":"0"},{"id":"410200","mingcheng":"开封市","code":"410200","paixu":0,"shenghao":"410000","del":"0"},{"id":"410300","mingcheng":"洛阳市","code":"410300","paixu":0,"shenghao":"410000","del":"0"},{"id":"410400","mingcheng":"平顶山市","code":"410400","paixu":0,"shenghao":"410000","del":"0"},{"id":"410500","mingcheng":"安阳市","code":"410500","paixu":0,"shenghao":"410000","del":"0"},{"id":"410600","mingcheng":"鹤壁市","code":"410600","paixu":0,"shenghao":"410000","del":"0"},{"id":"410700","mingcheng":"新乡市","code":"410700","paixu":0,"shenghao":"410000","del":"0"},{"id":"410800","mingcheng":"焦作市","code":"410800","paixu":0,"shenghao":"410000","del":"0"},{"id":"410900","mingcheng":"濮阳市","code":"410900","paixu":0,"shenghao":"410000","del":"0"},{"id":"411000","mingcheng":"许昌市","code":"411000","paixu":0,"shenghao":"410000","del":"0"},{"id":"411100","mingcheng":"漯河市","code":"411100","paixu":0,"shenghao":"410000","del":"0"},{"id":"411200","mingcheng":"三门峡市","code":"411200","paixu":0,"shenghao":"410000","del":"0"},{"id":"411300","mingcheng":"南阳市","code":"411300","paixu":0,"shenghao":"410000","del":"0"},{"id":"411400","mingcheng":"商丘市","code":"411400","paixu":0,"shenghao":"410000","del":"0"},{"id":"411500","mingcheng":"信阳市","code":"411500","paixu":0,"shenghao":"410000","del":"0"},{"id":"411600","mingcheng":"周口市","code":"411600","paixu":0,"shenghao":"410000","del":"0"},{"id":"411700","mingcheng":"驻马店市","code":"411700","paixu":0,"shenghao":"410000","del":"0"},{"id":"419000","mingcheng":"省直辖县级行政区划","code":"419000","paixu":0,"shenghao":"410000","del":"0"}]
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
         * id : 410100
         * mingcheng : 郑州市
         * code : 410100
         * paixu : 0
         * shenghao : 410000
         * del : 0
         */

        private String id;
        private String mingcheng;
        private String code;
        private int paixu;
        private String shenghao;
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

        public String getShenghao() {
            return shenghao;
        }

        public void setShenghao(String shenghao) {
            this.shenghao = shenghao;
        }

        public String getDel() {
            return del;
        }

        public void setDel(String del) {
            this.del = del;
        }
    }
}
