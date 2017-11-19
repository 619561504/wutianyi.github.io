package com.wutianyi.shiro.services;

import com.wutianyi.shiro.dataobject.User;

import java.util.Set;

/**
 * Created by hanjiewu on 2016/5/25.
 */
public interface UserService {

    User createUser(User user);

    void changePassword(long userId, String newPassword);

    void correlationRoles(long userId, long... roleIds);

    void uncorrelationRoles(long userId, long... roleIds);

    User findByUserName(String username);

    Set<String> findRoles(String username);

    Set<String> findPermissions(String username);
}
