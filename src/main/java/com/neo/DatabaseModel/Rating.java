package com.neo.DatabaseModel;

import com.neo.DatabaseModel.Users.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class Rating {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private int rating;

    @Column(length = 1024)
    private String review;

    @ManyToOne
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Rating() {
    }

    public Rating(int rating, String review, User user) {
        this.rating = rating;
        this.review = review;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
