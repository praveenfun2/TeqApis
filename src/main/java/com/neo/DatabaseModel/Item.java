package com.neo.DatabaseModel;

import com.neo.DatabaseModel.Card.Card;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Praveen Gupta on 3/1/2017.
 */

@Entity
public class Item {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private CardImage image;

    @Column
    private String qr, logo;

    @Column
    private float qrs, qrx, qry, lx, ly, lw, lh;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true, mappedBy = "item")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<TextBox> textBoxes;

    public Item(CardImage cardImage, String qr, String logo, float qrs, float lx, float ly, float lw, float lh){
        this.image=cardImage;
        this.qr = qr;
        this.logo = logo;
        this.qrs = qrs;
        this.lx = lx;
        this.ly = ly;
        this.lw = lw;
        this.lh = lh;
    }

    public Item() {
    }

    public void addTextBox(String content, float x, float y, float w, float h){
        getTextBoxes().add(new TextBox(x, y, w, h, content, this));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<TextBox> getTextBoxes() {
        return textBoxes;
    }

    public void setTextBoxes(List<TextBox> textBoxes) {
        this.textBoxes = textBoxes;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public float getQrs() {
        return qrs;
    }

    public void setQrs(float qrs) {
        this.qrs = qrs;
    }

    public float getLx() {
        return lx;
    }

    public void setLx(float lx) {
        this.lx = lx;
    }

    public float getLy() {
        return ly;
    }

    public void setLy(float ly) {
        this.ly = ly;
    }

    public float getLw() {
        return lw;
    }

    public void setLw(float lw) {
        this.lw = lw;
    }

    public float getLh() {
        return lh;
    }

    public void setLh(float lh) {
        this.lh = lh;
    }

    public CardImage getImage() {
        return image;
    }

    public void setImage(CardImage image) {
        this.image = image;
    }

    public float getQrx() {
        return qrx;
    }

    public void setQrx(float qrx) {
        this.qrx = qrx;
    }

    public float getQry() {
        return qry;
    }

    public void setQry(float qry) {
        this.qry = qry;
    }
}
