package com.neo.DatabaseModel;

import com.neo.DatabaseModel.Card.Card;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TextBox {

    @Id
    private long id;

    @Column
    private float x, y, w, h;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    private Item item;

    public TextBox(float x, float y, float w, float h, String content, Item item) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.content = content;
        this.item = item;
    }

    public TextBox() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
