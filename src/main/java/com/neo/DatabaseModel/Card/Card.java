package com.neo.DatabaseModel.Card;

import com.neo.DatabaseModel.CardImage;
import com.neo.DatabaseModel.Users.Seller;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Praveen Gupta on 7/30/2017.
 */

@Entity
@Inheritance
@DiscriminatorColumn(
        name = "type",
        discriminatorType = DiscriminatorType.INTEGER
)
public class Card {

    //public static final double SCALE_LANDSCAPE = 4d / 3, SCALE_PORTRAIT = 3d / 4;
    public static final String SORT_BY_DISTANCE = "distance", SORT_BY_PRICE = "price";
    public static final int SELLER_CARD_FRONT = 1, SELLER_CARD_BACK = 2, CUSTOMER_CARD = 3;

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private boolean landscape;

    @Column
    protected Float price;

    @Column(insertable = false, updatable = false)
    private int type;

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sid")
    private Seller seller;

    @OneToMany(orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "card")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CardImage> images;

    public Card(boolean landscape, float price, String name, Seller seller) {
        this.landscape = landscape;
        this.price = price;
        this.name = name;
        this.seller=seller;
    }

    public Card(boolean landscape,  String name) {
        this.name = name;
        this.landscape=landscape;
    }

    public Card() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<CardImage> getImages() {
        return images;
    }

    public void setImages(List<CardImage> images) {
        this.images = images;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public boolean isLandscape() {
        return landscape;
    }

    public void setLandscape(boolean landscape) {
        this.landscape = landscape;
    }
}
