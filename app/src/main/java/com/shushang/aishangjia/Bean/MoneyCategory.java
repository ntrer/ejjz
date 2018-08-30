package com.shushang.aishangjia.Bean;

/**
 * Created by YD on 2018/7/24.
 */

public class MoneyCategory {

    /**
     * ret : 200
     * msg : success
     * data : {"totalMerchant":5,"totalOrders":4,"totalMoney":52,"averageMoney":1.2,"refundOrders":2,"refundMoney":12.5}
     */

    private String ret;
    private String msg;
    private DataBean data;

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

    public static class DataBean {
        /**
         * totalMerchant : 5
         * totalOrders : 4
         * totalMoney : 52.0
         * averageMoney : 1.2
         * refundOrders : 2
         * refundMoney : 12.5
         */

        private int totalMerchant;
        private int totalOrders;
        private double totalMoney;
        private double averageMoney;
        private int refundOrders;
        private double refundMoney;

        public int getTotalMerchant() {
            return totalMerchant;
        }

        public void setTotalMerchant(int totalMerchant) {
            this.totalMerchant = totalMerchant;
        }

        public int getTotalOrders() {
            return totalOrders;
        }

        public void setTotalOrders(int totalOrders) {
            this.totalOrders = totalOrders;
        }

        public double getTotalMoney() {
            return totalMoney;
        }

        public void setTotalMoney(double totalMoney) {
            this.totalMoney = totalMoney;
        }

        public double getAverageMoney() {
            return averageMoney;
        }

        public void setAverageMoney(double averageMoney) {
            this.averageMoney = averageMoney;
        }

        public int getRefundOrders() {
            return refundOrders;
        }

        public void setRefundOrders(int refundOrders) {
            this.refundOrders = refundOrders;
        }

        public double getRefundMoney() {
            return refundMoney;
        }

        public void setRefundMoney(double refundMoney) {
            this.refundMoney = refundMoney;
        }
    }
}
