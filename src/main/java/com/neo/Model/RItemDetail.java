package com.neo.Model;

import java.util.ArrayList;

/**
 * Created by Praveen Gupta on 4/10/2017.
 */
public class RItemDetail {

    private Long cartItemId, itemid;
    private String img;
    private Float price;
    private boolean userUploaded;



    public RItemDetail() {

    }

    public Long getItemid() {
        return itemid;
    }

    public void setItemid(Long itemid) {
        this.itemid = itemid;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }



    public RItemDetail(Long cartItemId, Long itemid, String img, Float price, boolean userUploaded){
        this.cartItemId = cartItemId;
        this.itemid = itemid;
        this.img = img;
        this.price=price;
        this.userUploaded = userUploaded;
    }

    public boolean isUserUploaded() {
        return userUploaded;
    }

    public void setUserUploaded(boolean userUploaded) {
        this.userUploaded = userUploaded;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

}
