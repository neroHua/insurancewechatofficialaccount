package com.yuexing.insurancewechatofficalaccount.wechat.service;

import com.yuexing.insurancewechatofficalaccount.wechat.model.UserPO;

public interface UserService {
    void insertUser(UserPO userPO);

    UserPO findUserByOpenId(String openId);
}
