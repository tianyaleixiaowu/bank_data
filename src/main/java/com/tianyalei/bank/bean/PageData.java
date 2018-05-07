package com.tianyalei.bank.bean;

/**
 * @author wuweifeng wrote on 2018/5/7.
 */
public class PageData extends BaseData {
    private long count;

    public long getCount() {
        return count;
    }

    public PageData setCount(long count) {
        this.count = count;
        return this;
    }
}
