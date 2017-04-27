package com.e7code.crud.model;

import com.e7code.common.api.bean.BaseEntity;
import com.e7code.common.api.bean.MatchType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

@Entity
public class User extends BaseEntity {
    @Column(nullable = false, unique = true, length = 20)
    private String username;

    private Boolean locked;
    private Long orderNum;
    private Double money;
    private BigDecimal fee;
    private Date birthday;
    private MatchType matchTypeId;
    private MatchType matchTypeName;
    private String comment;

    private String xxx;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(columnDefinition="tinyint(1) NOT NULL DEFAULT '0'")
    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    @Temporal(TemporalType.DATE)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Enumerated(EnumType.ORDINAL)
    public MatchType getMatchTypeId() {
        return matchTypeId;
    }

    public void setMatchTypeId(MatchType matchTypeId) {
        this.matchTypeId = matchTypeId;
    }

    @Enumerated(EnumType.STRING)
    public MatchType getMatchTypeName() {
        return matchTypeName;
    }

    public void setMatchTypeName(MatchType matchTypeName) {
        this.matchTypeName = matchTypeName;
    }

    @Lob
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Transient
    public String getXxx() {
        return xxx;
    }

    public void setXxx(String xxx) {
        this.xxx = xxx;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", locked=" + locked +
                ", orderNum=" + orderNum +
                ", money=" + money +
                ", fee=" + fee +
                ", birthday=" + birthday +
                ", matchTypeId=" + matchTypeId +
                ", matchTypeName=" + matchTypeName +
                ", comment='" + comment + '\'' +
                ", xxx='" + xxx + '\'' +
                '}';
    }
}
