package com.sunyard.nettydemo.serialize;

import java.io.Serializable;

/**
 * Created by lww on 2018/10/18.
 */
public class User implements Serializable {
    private String userId;
    private String name;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
