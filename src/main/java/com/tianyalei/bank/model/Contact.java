package com.tianyalei.bank.model;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 联系人
 * @author wuweifeng wrote on 2018/5/2.
 */
@Entity
@Table(name = "contact", indexes = {@Index(name = "mobile", columnList =
        "mobile")})
public class Contact extends BaseEntity {
    private String company;
    private String nickName;

    private String mobile;

    private String wxId;

    private Long companyId;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
}
