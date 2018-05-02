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
     * 银行名称
     */
    private String bank;
    /**
     * 支付价格(元)
     */
    private Long price;
    /**
     * 票面价格(元)
     */
    private Long billPrice;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getBillPrice() {
        return billPrice;
    }

    public void setBillPrice(Long billPrice) {
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
