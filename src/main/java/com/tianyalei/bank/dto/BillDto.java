package com.tianyalei.bank.dto;

import java.util.Date;

/**
 * @author wuweifeng wrote on 2018/5/8.
 */
public class BillDto {
    private Long id;
    /**
     * 银行名称
     */
    private String bank;
    /**
     * 支付价格(元)
     */
    private Integer price;
    /**
     * 票面价格(万元)
     */
    private Double billPrice;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 不足、短期1，半年2，一年3，超期4
     */
    private Integer type;
    /**
     * 数量
     */
    private Integer count;
    /**
     * 原文
     */
    private String content;

    private String company;
    private String nickName;
    private String mobile;

    @Override
    public String toString() {
        return "BillDto{" +
                "id=" + id +
                ", bank='" + bank + '\'' +
                ", price=" + price +
                ", billPrice=" + billPrice +
                ", endTime=" + endTime +
                ", type=" + type +
                ", count=" + count +
                ", content='" + content + '\'' +
                ", company='" + company + '\'' +
                ", nickName='" + nickName + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Double getBillPrice() {
        return billPrice;
    }

    public void setBillPrice(Double billPrice) {
        this.billPrice = billPrice;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
