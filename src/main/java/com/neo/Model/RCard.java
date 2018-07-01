package com.neo.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praveen Gupta on 5/13/2017.
 */
public class RCard {

    private String name, sid, sname, category, image, encodedImage;
    private Long id, cat;
    private List<String> papers, finishes, subCategories;
    private ArrayList<Long> paper, finish, subcat;
    private ArrayList<String> color;
    private List<RCardImage> images;

    private Integer side;
    private boolean landscape;
    private Float price, discountedPrice;
    private Distance distance;

    public RCard(String name, String sid, String sname, Long id, float price) {
        this.name = name;
        this.sid = sid;
        this.sname = sname;
        this.id = id;
        this.price = price;
    }

    public RCard(String name, String sid, float price, int side) {
        this.name = name;
        this.sid = sid;
        this.price = price;
        this.side = side;
    }

    public RCard(String name, Float price, boolean landscape) {
        this.name = name;
        this.price = price;
        this.landscape = landscape;
    }

    public RCard() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getCat() {
        return cat;
    }

    public void setCat(Long cat) {
        this.cat = cat;
    }

    public ArrayList<Long> getPaper() {
        return paper;
    }

    public void setPaper(ArrayList<Long> paper) {
        this.paper = paper;
    }

    public ArrayList<Long> getFinish() {
        return finish;
    }

    public void setFinish(ArrayList<Long> finish) {
        this.finish = finish;
    }

    public ArrayList<Long> getSubcat() {
        return subcat;
    }

    public void setSubcat(ArrayList<Long> subcat) {
        this.subcat = subcat;
    }

    public ArrayList<String> getColor() {
        return color;
    }

    public void setColor(ArrayList<String> color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addSubCategory(String subCategory) {
        if (subCategories == null) subCategories = new ArrayList<>();
        subCategories.add(subCategory);
    }

    public void addPaper(String paper) {
        if (papers == null) papers = new ArrayList<>();
        papers.add(paper);
    }

    public void addFinish(String finish) {
        if (finishes == null) finishes = new ArrayList<>();
        finishes.add(finish);
    }

    public void addCardImage(Long id, String filename, String color){
        if(images==null) images=new ArrayList<>();
        images.add(new RCardImage(id, filename, color));
    }
    public List<String> getPapers() {
        return papers;
    }

    public void setPapers(ArrayList<String> papers) {
        this.papers = papers;
    }

    public List<String> getFinishes() {
        return finishes;
    }

    public void setFinishes(ArrayList<String> finishes) {
        this.finishes = finishes;
    }

    public List<String> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(ArrayList<String> subCategories) {
        this.subCategories = subCategories;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getSide() {
        return side;
    }

    public void setSide(Integer side) {
        this.side = side;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Float getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(Float discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public List<RCardImage> getImages() {
        return images;
    }

    public void setImages(List<RCardImage> images) {
        this.images = images;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    public boolean isLandscape() {
        return landscape;
    }

    public void setLandscape(boolean landscape) {
        this.landscape = landscape;
    }
}
