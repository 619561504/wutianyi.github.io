package com.wutianyi.shiro.services;

import com.wutianyi.shiro.dataobject.Permission;

/**
 * Created by hanjiewu on 2016/5/23.
 */
public interface PermissionService {
    Permission createPermission(Permission permission);

    void deletePermission(long permissionId);
}
