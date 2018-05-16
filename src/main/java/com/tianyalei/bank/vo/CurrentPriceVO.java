package com.tianyalei.bank.vo;

/**
 * @author wuweifeng wrote on 2018/5/15.
 */
public class CurrentPriceVO {
    private Long id;
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
    private String buildTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }
}
