package com.neo.Model;

/**
 * Created by Praveen Gupta on 4/3/2017.
 */
public class RPaperSeller {
    private Float price;
    private String name, des;
    private long id;
    private boolean canDelete;

    public RPaperSeller(Float price, String name, String des, long id, boolean canDelete) {
        this.price = price;
        this.name = name;
        this.des = des;
        this.id = id;
        this.canDelete = canDelete;
    }

    public RPaperSeller() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }
}
