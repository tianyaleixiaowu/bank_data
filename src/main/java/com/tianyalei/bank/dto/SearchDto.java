package com.tianyalei.bank.dto;

/**
 * @author wuweifeng wrote on 2018/5/7.
 */
public class SearchDto {
    private Integer page;
    private Integer size;
    /**
     * 银行类型
     */
    private Integer bankType;
    /**
     * 面额
     */
    private Integer billPrice;
    /**
     * 手填的价格筛选低价
     */
    private Integer lowPrice;
    /**
     * 手填的价格筛选高价
     */
    private Integer highPrice;
    /**
     * 时间，半年 1年
     */
    private Integer type;
    /**
     * 关键字
     */
    private String keywords;

    @Override
    public String toString() {
        return "SearchDto{" +
                "page=" + page +
                ", size=" + size +
                ", bankType=" + bankType +
                ", billPrice=" + billPrice +
                ", lowPrice=" + lowPrice +
                ", highPrice=" + highPrice +
                ", type=" + type +
                ", keywords='" + keywords + '\'' +
                '}';
    }

    public Integer getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(Integer lowPrice) {
        this.lowPrice = lowPrice;
    }

    public Integer getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(Integer highPrice) {
        this.highPrice = highPrice;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getBankType() {
        return bankType;
    }

    public void setBankType(Integer bankType) {
        this.bankType = bankType;
    }

    public Integer getBillPrice() {
        return billPrice;
    }

    public void setBillPrice(Integer billPrice) {
        this.billPrice = billPrice;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
