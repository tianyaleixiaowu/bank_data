package com.tianyalei.bank.model;

import javax.persistence.*;

/**
 * 已经处理过的Message的id
 * @author wuweifeng wrote on 2018/5/2.
 */
@Entity
@Table(name = "done_msg_id")
public class DoneMsgId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long begin;
    private Long end;

    public Long getId() {
        return id;
    }

    public Long getBegin() {
        return begin;
    }

    public Long getEnd() {
        return end;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBegin(Long begin) {
        this.begin = begin;
    }

    public void setEnd(Long end) {
        this.end = end;
    }
}
