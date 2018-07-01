package com.neo.DatabaseModel.Shipment;


import javax.persistence.*;

@Entity
public class DeliveryType {

    @Id
    @GeneratedValue
    private long type;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(length = 1024)
    private String description;

    public DeliveryType() {
    }

    public DeliveryType(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
