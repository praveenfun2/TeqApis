package com.neo.DatabaseModel.Users;

import com.neo.DatabaseModel.Rating;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "type",
        discriminatorType = DiscriminatorType.STRING
)
public class User {

    public static final String SELLER_TYPE = "seller", COURIER_TYPE = "courier",
            DESIGNER_TYPE = "designer", CUSTOMER_TYPE = "customer", ADMIN_TYPE = "admin";

    public static List<String> uTypes;

    static {
        uTypes = new ArrayList<>();
        uTypes.add(SELLER_TYPE);
        uTypes.add(COURIER_TYPE);
        uTypes.add(DESIGNER_TYPE);
        uTypes.add(CUSTOMER_TYPE);
    }

    @Id
    @Column(nullable = false)
    protected String id;

    @Column(nullable = false)
    protected String pass;

    @Column
    private String name, phone;

    @Column
    private Integer otp = 1;

    @Column(insertable = false, updatable = false)
    private String type;

    @Column
    private long rating = 0;

    @Column
    private int ratingCount = 0;

    @Column
    @ColumnDefault("false")
    private boolean activated;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @BatchSize(size = 5)
    private List<Rating> ratings;

    public User(String id, String pass, String name, int otp) {
        this.pass = pass;
        this.name = name;
        this.otp = otp;
        this.id = id;
    }

    public User(String id, String pass, String name) {
        this.id = id;
        this.pass = pass;
        this.name = name;
    }

    public User(String id, String pass) {
        this.id = id;
        this.pass = pass;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
