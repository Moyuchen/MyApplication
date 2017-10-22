package com.moyuchen.mvpuser.Bean;

import java.util.List;

/**
 * User: 张亚博
 * Date: 2017-10-22 15:39
 * Description：
 */
public class getordersInfo {

    /**
     * msg : 请求成功
     * code : 0
     * data : [{"createtime":"2017-10-21T14:27:25","orderid":437,"price":1192.92,"status":0,"uid":91},{"createtime":"2017-10-21T14:59:57","orderid":458,"price":1080.93,"status":0,"uid":91},{"createtime":"2017-10-21T15:02:30","orderid":459,"price":1192,"status":0,"uid":91},{"createtime":"2017-10-21T15:02:39","orderid":460,"price":11,"status":0,"uid":91},{"createtime":"2017-10-21T15:27:37","orderid":467,"price":1330.89,"status":0,"uid":91},{"createtime":"2017-10-21T15:27:37","orderid":468,"price":1330.89,"status":0,"uid":91},{"createtime":"2017-10-21T15:27:38","orderid":469,"price":1330.89,"status":0,"uid":91},{"createtime":"2017-10-21T15:27:38","orderid":470,"price":1330.89,"status":0,"uid":91}]
     * page : 1
     */

    private String msg;
    private String code;
    private String page;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createtime : 2017-10-21T14:27:25
         * orderid : 437
         * price : 1192.92
         * status : 0
         * uid : 91
         */

        private String createtime;
        private int orderid;
        private double price;
        private int status;
        private int uid;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getOrderid() {
            return orderid;
        }

        public void setOrderid(int orderid) {
            this.orderid = orderid;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }
}
