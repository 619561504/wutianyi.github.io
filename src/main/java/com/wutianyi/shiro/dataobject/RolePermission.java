package com.wutianyi.shiro.dataobject;

/**
 * Created by hanjiewu on 2016/5/23.
 */
public class RolePermission {
    private long roleId;
    private long permissionId;

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
    }
}
