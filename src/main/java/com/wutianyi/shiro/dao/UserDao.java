package com.wutianyi.shiro.dao;

import com.wutianyi.shiro.dataobject.User;

/**
 * Created by hanjiewu on 2016/5/25.
 */
public interface UserDao {

    User createUser(User user);

    User findOne(long userId);

    void updateUser(User user);
}
