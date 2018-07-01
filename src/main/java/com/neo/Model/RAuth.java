package com.neo.Model;

/**
 * Created by Praveen Gupta on 5/29/2017.
 */
public class RAuth {

    private String token;
    private String type;

    public RAuth(String token, String type) {
        this.token = token;
        this.type = type;
    }

    public RAuth() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
