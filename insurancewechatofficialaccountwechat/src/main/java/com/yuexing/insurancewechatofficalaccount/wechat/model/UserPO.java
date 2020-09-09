package com.yuexing.insurancewechatofficalaccount.wechat.model;

import com.yuexing.insurancewechatofficalaccount.wechat.model.base.BaseEntityPO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_user")
public class UserPO extends BaseEntityPO {
    @Column(name = "open_id")
    private String openId;

    @Column(name = "other_party_id")
    private String otherPartyId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "interest_title")
    private String interestTitle;

    @Column(name = "interest_province_name")
    private String interestProvinceName;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOtherPartyId() {
        return otherPartyId;
    }

    public void setOtherPartyId(String otherPartyId) {
        this.otherPartyId = otherPartyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getInterestTitle() {
        return interestTitle;
    }

    public void setInterestTitle(String interestTitle) {
        this.interestTitle = interestTitle;
    }

    public String getInterestProvinceName() {
        return interestProvinceName;
    }

    public void setInterestProvinceName(String interestProvinceName) {
        this.interestProvinceName = interestProvinceName;
    }
}
