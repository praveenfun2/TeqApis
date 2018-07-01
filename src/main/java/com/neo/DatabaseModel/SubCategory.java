package com.neo.DatabaseModel;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity(name = "sub_category")
public class SubCategory {

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catid", nullable = false)
    private Category category;

    @Id
    @GeneratedValue
    private long subid;

    public SubCategory(String name) {
        this.name = name;
        this.category = category;
    }

    public SubCategory(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public SubCategory() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSubid() {
        return subid;
    }

    public void setSubid(long subid) {
        this.subid = subid;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
