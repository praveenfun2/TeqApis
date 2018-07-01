package com.neo.DatabaseModel.Shipment;

import com.neo.DatabaseModel.Shipment.DeliveryType;
import com.neo.DatabaseModel.Users.Courier;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.INTEGER)
public class UserDeliveryType {
    public static final int COURIER=1, SELLER=2;

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private DeliveryType deliveryType;

    @Column(nullable = false)
    private float price;

    @Column(insertable = false, updatable = false)
    private Integer type;

    public UserDeliveryType() {
    }

    public UserDeliveryType(DeliveryType deliveryType, float price) {
        this.deliveryType = deliveryType;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
