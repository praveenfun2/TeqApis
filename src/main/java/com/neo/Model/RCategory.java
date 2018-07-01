package com.neo.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by localadmin on 18/6/17.
 */
public class RCategory {

    private Long id;
    private String name;

    private List<RSubCategory> subCategories=new ArrayList<>();

    public RCategory() {
    }

    public RCategory(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addSub(long id, String name){
        subCategories.add(new RSubCategory(id, name));
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

    public List<RSubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<RSubCategory> subCategories) {
        this.subCategories = subCategories;
    }
}
