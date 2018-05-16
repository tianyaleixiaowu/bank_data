package com.tianyalei.bank.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 每日价格参考
 * @author wuweifeng wrote on 2018/5/15.
 */
@Entity
@Table(name = "current_price")
public class CurrentPrice extends BaseEntity {
    /**
     * 售价
     */
    private int price;
    /**
     * 票面价格
     */
    private int billPrice;
    /**
     * 大分类（国股1、城商2、三农3）
     */
    private byte bankType;
    /**
     * 统计的是哪天
     */
    private Date buildTime;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBillPrice() {
        return billPrice;
    }

    public void setBillPrice(int billPrice) {
        this.billPrice = billPrice;
    }

    public byte getBankType() {
        return bankType;
    }

    public void setBankType(byte bankType) {
        this.bankType = bankType;
    }

    public Date getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(Date buildTime) {
        this.buildTime = buildTime;
    }
}
