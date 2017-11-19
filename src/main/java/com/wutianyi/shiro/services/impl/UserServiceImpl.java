package com.wutianyi.shiro.services.impl;

import com.wutianyi.shiro.PasswordHelper;
import com.wutianyi.shiro.dao.UserDao;
import com.wutianyi.shiro.dataobject.User;
import com.wutianyi.shiro.services.UserService;

import java.util.Set;

/**
 * Created by hanjiewu on 2016/5/25.
 */
public class UserServiceImpl implements UserService {
    UserDao userDao;
    PasswordHelper passwordHelper = new PasswordHelper();

    @Override
    public User createUser(User user) {
        passwordHelper.encryptPassword(user);
        return userDao.createUser(user);
    }

    @Override
    public void changePassword(long userId, String newPassword) {
        User user = userDao.findOne(userId);
        user.setPassword(newPassword);
        passwordHelper.encryptPassword(user);
        userDao.updateUser(user);
    }

    @Override
    public void correlationRoles(long userId, long... roleIds) {

    }

    @Override
    public void uncorrelationRoles(long userId, long... roleIds) {

    }

    @Override
    public User findByUserName(String username) {
        return null;
    }

    @Override
    public Set<String> findRoles(String username) {
        return null;
    }

    @Override
    public Set<String> findPermissions(String username) {
        return null;
    }
}
