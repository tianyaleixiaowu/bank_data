package com.tianyalei.bank.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 票据
 *
 * @author wuweifeng wrote on 2018/5/2.
 */
@Entity
@Table(name = "bill")
public class Bill extends BaseEntity {
    /**
     * 银行的大分类
     */
    private Byte bankType;
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
     * 票面开始时间
     */
    private Date beginTime;
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
     * 联系人的id
     */
    private Long contactId;
    /**
     * 原文
     */
    private String content;
    /**
     * 是否是最新的（0是最新，-1不是）
     */
    private byte young;

    @Override
    public String toString() {
        return "Bill{" +
                "bankType=" + bankType +
                ", bank='" + bank + '\'' +
                ", price=" + price +
                ", billPrice=" + billPrice +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", type=" + type +
                ", count=" + count +
                ", contactId=" + contactId +
                ", content='" + content + '\'' +
                ", young=" + young +
                '}';
    }

    public byte getYoung() {
        return young;
    }

    public void setYoung(byte young) {
        this.young = young;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Byte getBankType() {
        return bankType;
    }

    public void setBankType(Byte bankType) {
        this.bankType = bankType;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }
}
