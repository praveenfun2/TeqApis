package com.neo.Model;

public class RDeliveryType {

    private long type;
    private String title, description;
    private float price;

    public RDeliveryType(String title, String description, float price) {
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public RDeliveryType() {
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }
}
