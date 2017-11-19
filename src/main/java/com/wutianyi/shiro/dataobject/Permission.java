package com.wutianyi.shiro.dataobject;

import java.io.Serializable;

/**
 * Created by hanjiewu on 2016/5/23.
 */
public class Permission implements Serializable {
    private static final long serialVersionUID = -2870496022915839451L;

    private long id;
    private String permission;
    private String description;
    private boolean available = false;

    public Permission() {
    }

    public Permission(String permission, String description, boolean available) {
        this.permission = permission;
        this.description = description;
        this.available = available;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
