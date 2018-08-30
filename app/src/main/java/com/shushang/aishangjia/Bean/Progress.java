package com.shushang.aishangjia.Bean;

import java.util.List;

/**
 * Created by YD on 2018/8/21.
 */

public class Progress {

    /**
     * ret : 200
     * msg : success
     * data : null
     * dataList : [{"decorationProgressId":"adsf87g2378hads78fg278g87dsafg87","decorationProgressName":"毛坯","active":"1","sortId":0,"caoZuoRen":"9hfs9dh273fh98sdfh","xiugairen":null,"cjsj":1533634919000,"xgsj":1533634922000,"del":"0"}]
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
         * decorationProgressId : adsf87g2378hads78fg278g87dsafg87
         * decorationProgressName : 毛坯
         * active : 1
         * sortId : 0
         * caoZuoRen : 9hfs9dh273fh98sdfh
         * xiugairen : null
         * cjsj : 1533634919000
         * xgsj : 1533634922000
         * del : 0
         */

        private String decorationProgressId;
        private String decorationProgressName;
        private String active;
        private int sortId;
        private String caoZuoRen;
        private Object xiugairen;
        private long cjsj;
        private long xgsj;
        private String del;

        public String getDecorationProgressId() {
            return decorationProgressId;
        }

        public void setDecorationProgressId(String decorationProgressId) {
            this.decorationProgressId = decorationProgressId;
        }

        public String getDecorationProgressName() {
            return decorationProgressName;
        }

        public void setDecorationProgressName(String decorationProgressName) {
            this.decorationProgressName = decorationProgressName;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }

        public int getSortId() {
            return sortId;
        }

        public void setSortId(int sortId) {
            this.sortId = sortId;
        }

        public String getCaoZuoRen() {
            return caoZuoRen;
        }

        public void setCaoZuoRen(String caoZuoRen) {
            this.caoZuoRen = caoZuoRen;
        }

        public Object getXiugairen() {
            return xiugairen;
        }

        public void setXiugairen(Object xiugairen) {
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
    }
}
