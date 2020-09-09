package com.yuexing.insurancewechatofficalaccount.wechat.service.impl;

import com.yuexing.insurancewechatofficalaccount.wechat.dao.UserDao;
import com.yuexing.insurancewechatofficalaccount.wechat.model.UserPO;
import com.yuexing.insurancewechatofficalaccount.wechat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void insertUser(UserPO userPO) {
        userDao.save(userPO);
    }

    @Override
    public UserPO findUserByOpenId(String openId) {
        return userDao.findOneByOpenId(openId);
    }

}
