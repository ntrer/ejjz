package com.shushang.aishangjia.Bean;

/**
 * Created by YD on 2018/7/23.
 */

public class Login {

    /**
     * ret : 200
     * msg : success
     * data : {"token_id":"76696f50202a4fbbb522af4be06f045d","xingming":"admin","shoujihao":null,"touxiang":"","type":"1","shangjia_code":"admin","shangjia_id":"1000","shangjia_name":null,"activity_id":"402880e564c0ddfb0164c0ec821c0003"}
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
         * token_id : 76696f50202a4fbbb522af4be06f045d
         * xingming : admin
         * shoujihao : null
         * touxiang :
         * type : 1
         * shangjia_code : admin
         * shangjia_id : 1000
         * shangjia_name : null
         * activity_id : 402880e564c0ddfb0164c0ec821c0003
         */

        private String token_id;
        private String xingming;
        private Object shoujihao;
        private String touxiang;
        private String type;
        private String shangjia_code;
        private String shangjia_id;
        private Object shangjia_name;
        private String activity_id;

        public String getToken_id() {
            return token_id;
        }

        public void setToken_id(String token_id) {
            this.token_id = token_id;
        }

        public String getXingming() {
            return xingming;
        }

        public void setXingming(String xingming) {
            this.xingming = xingming;
        }

        public Object getShoujihao() {
            return shoujihao;
        }

        public void setShoujihao(Object shoujihao) {
            this.shoujihao = shoujihao;
        }

        public String getTouxiang() {
            return touxiang;
        }

        public void setTouxiang(String touxiang) {
            this.touxiang = touxiang;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getShangjia_code() {
            return shangjia_code;
        }

        public void setShangjia_code(String shangjia_code) {
            this.shangjia_code = shangjia_code;
        }

        public String getShangjia_id() {
            return shangjia_id;
        }

        public void setShangjia_id(String shangjia_id) {
            this.shangjia_id = shangjia_id;
        }

        public Object getShangjia_name() {
            return shangjia_name;
        }

        public void setShangjia_name(Object shangjia_name) {
            this.shangjia_name = shangjia_name;
        }

        public String getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
        }
    }
}
