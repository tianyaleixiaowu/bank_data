package com.tianyalei.bank.dto;

/**
 * @author wuweifeng wrote on 2018/5/8.
 */
public class BillMoDto {
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
                ", content='" + content + '\'' +
                ", company='" + company + '\'' +
                ", nickName='" + nickName + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
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
