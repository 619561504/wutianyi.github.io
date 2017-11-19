package com.wutianyi.shiro.services;

import com.wutianyi.shiro.dataobject.Role;

/**
 * Created by hanjiewu on 2016/5/25.
 */
public interface RoleService {

    Role createRole(Role role);

    void deleteRole(long roleId);

    void correlationPermissions(long roleId, long... permissionIds);

    void uncorrelationPermissions(long roleId, long... permissionIds);
}
