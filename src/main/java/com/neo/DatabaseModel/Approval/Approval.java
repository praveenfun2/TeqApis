package com.neo.DatabaseModel.Approval;

import com.neo.DatabaseModel.Users.Seller;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * Created by Praveen Gupta on 3/1/2017.
 */

@Entity
@Inheritance
@DiscriminatorColumn(
        name = "type",
        discriminatorType = DiscriminatorType.INTEGER
)
public class Approval {

    public static final int CATEGORY = 1, SUB_CATEGORY = 2, PAPER = 3, FINISH = 4;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, updatable = false)
    private int type;

    @ManyToOne
    @JoinColumn(name = "sid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Seller seller;

    @Column
    private String name;

    public Approval(Seller seller, String name) {
        this.seller = seller;
        this.name = name;
    }

    public Approval() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
