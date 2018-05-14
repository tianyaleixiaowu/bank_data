package com.tianyalei.bank.vo;

import java.util.Date;

/**
 * @author wuweifeng wrote on 2018/5/7.
 */
public class BillVO {
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
     * 公司名
     */
    private String company;
    /**
     * 票面价格(万元)
     */
    private Double billPrice;
    /**
     * 票面开始时间
     */
    private Date beginTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 发布时间
     */
    private String createTime;
    /**
     * 不足、短期1，半年2，一年3，超期4
     */
    private String type;
    /**
     * 数量
     */
    private Integer count;
    /**
     * 联系人
     */
    private String nickName;
    private String mobile;
    /**
     * 无法识别的放这里
     */
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNickName() {
        return nickName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
