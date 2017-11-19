package com.wutianyi.shiro.dataobject;

import java.io.Serializable;

/**
 * Created by hanjiewu on 2016/5/23.
 */
public class UserRole implements Serializable {
    private static final long serialVersionUID = -3908784685196861676L;

    private long userId;
    private long roleId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
