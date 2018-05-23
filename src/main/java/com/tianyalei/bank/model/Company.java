package com.tianyalei.bank.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 公司
 * @author wuweifeng wrote on 2018/5/16.
 */
@Entity
@Table(name = "company")
public class Company extends BaseEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
