package com.tianyalei.bank.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wuweifeng wrote on 2018/5/1.
 */
@Entity
@Table(name = "message")
public class MessageEntity extends BaseEntity {
    private String nickName;

    private String content;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
