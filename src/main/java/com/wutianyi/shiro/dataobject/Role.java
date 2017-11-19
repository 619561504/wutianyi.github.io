package com.wutianyi.shiro.dataobject;

import java.io.Serializable;

/**
 * Created by hanjiewu on 2016/5/23.
 */
public class Role implements Serializable {
    private static final long serialVersionUID = -2939506447037486751L;

    private long id;
    private String role;
    private String description;
    private boolean available = false;

    public Role() {
    }

    public Role(String role, String description, boolean available) {
        this.role = role;
        this.description = description;
        this.available = available;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
