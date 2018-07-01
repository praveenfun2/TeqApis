package com.neo.Model;

import java.util.ArrayList;
import java.util.List;

public class RColorAndImages {
    private List<String> colors=new ArrayList<>(), images=new ArrayList<>();

    public RColorAndImages() {
    }

    public List<String> getColors() {
        return colors;
    }

    public void addColor(String color){
        colors.add(color);
    }

    public void addImage(String image){
        images.add(image);
    }
    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
