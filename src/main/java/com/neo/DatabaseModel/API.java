package com.neo.DatabaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by localadmin on 29/6/17.
 */

@Entity
public class API {

    public static final long GEOCODING=1, PAYPAL=2, EMAIL=3;

    @Column(nullable = false)
    private String id;

    @Column
    private String secret;

    @Id
    @Column(nullable = false)
    private Long type;

    public API(String id, String secret, long type) {
        this.id = id;
        this.secret = secret;
        this.type = type;
    }

    public API(String id, String secret) {
        this.id = id;
        this.secret = secret;
    }

    public API() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }
}
