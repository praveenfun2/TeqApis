package com.neo.Model;

/**
 * Created by localadmin on 22/6/17.
 */
public class RNotification {

    private String type, message;
    private Long id;

    public RNotification(String type, String message, Long id) {
        this.type = type;
        this.message = message;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
