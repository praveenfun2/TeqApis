package com.neo.DatabaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PassToken {

    @Id
    @Column
    private String id;

    @Column(nullable = false)
    private String token;

    public PassToken(String id, String token) {
        this.id = id;
        this.token = token;
    }

    public PassToken() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
