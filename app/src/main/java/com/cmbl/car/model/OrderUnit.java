package com.cmbl.car.model;

/**
 * Created by Administrator on 2015/7/15 0015.
 */
public class OrderUnit {
    /***
     * 订单状态
     */
    private int state;
    /***
     * 订单类型
     */
    private int type;
    /***
     * 4S店名称
     */
    private String company;
    /***
     * 订单时间
     */
    private String orderDate;
    /***
     * 其他操作时间
     */
    private String otherTime;
    /***
     * 4S店图片源
     */
    private String companyUrl;
    /***
     * 联系号码
     */
    private String phone;
    /***
     * 金额
     */
    private int price;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOtherTime() {
        return otherTime;
    }

    public void setOtherTime(String otherTime) {
        this.otherTime = otherTime;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
