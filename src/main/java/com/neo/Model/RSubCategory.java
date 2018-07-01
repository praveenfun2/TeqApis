package com.neo.Model;

/**
 * Created by localadmin on 18/6/17.
 */
public class RSubCategory {
    private Long id;
    private String name;
    private RCategory category;

    public RSubCategory(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public RSubCategory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RCategory getCategory() {
        return category;
    }

    public void setCategory(RCategory category) {
        this.category = category;
    }
}
