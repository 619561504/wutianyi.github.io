package com.wutianyi.shiro.dataobject;

import java.io.Serializable;

/**
 * Created by hanjiewu on 2016/5/23.
 */
public class User implements Serializable {

    private static final long serialVersionUID = 8560994186014414883L;

    private long id;
    private String username;
    private String password;
    private String salt;

    private boolean locked = false;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
