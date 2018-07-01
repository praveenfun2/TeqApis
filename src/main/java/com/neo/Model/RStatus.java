package com.neo.Model;

/**
 * Created by localadmin on 14/7/17.
 */
public class RStatus {
    private int key;
    private String status;

    public RStatus(int key, String status) {
        this.key = key;
        this.status = status;
    }

    public RStatus() {
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
