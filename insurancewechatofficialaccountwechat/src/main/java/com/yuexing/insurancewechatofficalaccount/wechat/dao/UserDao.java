package com.yuexing.insurancewechatofficalaccount.wechat.dao;

import com.yuexing.insurancewechatofficalaccount.wechat.model.UserPO;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<UserPO, Long> {
    UserPO findOneByOpenId(String openId);
}
