package com.neo.DatabaseModel;

import com.neo.DatabaseModel.Users.Customer;

import javax.persistence.*;
import java.util.List;

@Entity(name = "designer_offer")
public class DesignerOffer {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid")
    private Customer customer;

    @Column(length = 1024)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private SubCategory subCategory;

    @OneToMany(mappedBy = "offer")
    private List<DesignerBid> bids;

    public DesignerOffer() {
    }

    public DesignerOffer(Customer customer, String description, Category category, SubCategory subCategory) {
        this.customer=customer;
        this.description = description;
        this.category = category;
        this.subCategory = subCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public List<DesignerBid> getBids() {
        return bids;
    }

    public void setBids(List<DesignerBid> bids) {
        this.bids = bids;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
