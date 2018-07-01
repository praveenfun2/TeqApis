package com.neo.Model;

/**
 * Created by Praveen Gupta on 5/18/2017.
 */
public class RDesignerOffer {
    private Long id, cat, subcat;
    private String description, category, subCategory, uid;

    public RDesignerOffer(Long id, String description, String category, String subCategory) {
        this.id = id;
        this.description = description;
        this.category = category;
        this.subCategory = subCategory;
    }

    public RDesignerOffer(Long id, String description, String category, String subCategory, String uid) {
        this.id = id;
        this.description = description;
        this.category = category;
        this.subCategory = subCategory;
        this.uid = uid;
    }

    public RDesignerOffer() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public Long getCat() {
        return cat;
    }

    public void setCat(Long cat) {
        this.cat = cat;
    }

    public Long getSubcat() {
        return subcat;
    }

    public void setSubcat(Long subcat) {
        this.subcat = subcat;
    }
}
