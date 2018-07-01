package com.neo.Model;

public class RCardImage {
    private Long id;
    private String fileName, color, base64;
    private RCard card;

    public RCardImage(Long id, String fileName, String color) {
        this.id = id;
        this.fileName = fileName;
        this.color = color;
    }

    public RCardImage() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public RCard getCard() {
        return card;
    }

    public void setCard(RCard card) {
        this.card = card;
    }
}
