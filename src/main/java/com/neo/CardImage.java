package com.neo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CardImage{
    private File file;
    private List<Component> components=new ArrayList<>();
    private List<ImageComponent> imageComponents=new ArrayList<>();
    private QRComponent qrComponent;

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public CardImage(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setImageComponents(List<ImageComponent> imageComponents) {
        this.imageComponents = imageComponents;
    }

    public List<ImageComponent> getImageComponents() {
        return imageComponents;
    }

    public QRComponent getQrComponent() {
        return qrComponent;
    }

    public void setQrComponent(QRComponent qrComponent) {
        this.qrComponent = qrComponent;
    }

    public static class QRComponent{
        private String qr;
        private float x, y, w ,h;

        public QRComponent(String qr, float x, float y, float w, float h) {
            this.qr = qr;
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        public String getQr() {
            return qr;
        }

        public void setQr(String qr) {
            this.qr = qr;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getW() {
            return w;
        }

        public void setW(float w) {
            this.w = w;
        }

        public float getH() {
            return h;
        }

        public void setH(float h) {
            this.h = h;
        }
    }

    public static class ImageComponent {
        private File image;
        private float x, y, w ,h;

        public ImageComponent(File image, float x, float y, float w, float h) {
            this.image=image;
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getW() {
            return w;
        }

        public void setW(float w) {
            this.w = w;
        }

        public float getH() {
            return h;
        }

        public void setH(float h) {
            this.h = h;
        }

        public File getImage() {
            return image;
        }

        public void setImage(File image) {
            this.image = image;
        }
    }
    public static class Component {
        private String text;
        private float x, y, w ,h;

        public Component(String text, float x, float y, float w, float h) {
            this.text = text;
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getW() {
            return w;
        }

        public void setW(float w) {
            this.w = w;
        }

        public float getH() {
            return h;
        }

        public void setH(float h) {
            this.h = h;
        }
    }

    public static class Builder{
        public Builder setFile(File file) {
            this.file = file;
            return this;
        }

        private File file;
        private List<Component> components=new ArrayList<>();
        private List<ImageComponent> imageComponents=new ArrayList<>();
        private QRComponent qrComponent;

        public Builder addComponent(String text, float x, float y, float w, float h){
            components.add(new Component(text, x, y, w, h));
            return this;
        }

        public Builder addImageComponent(File file, float x, float y, float w, float h){
            imageComponents.add(new ImageComponent(file, x, y, w, h));
            return this;
        }

        public CardImage build(){
            CardImage cardImage=new CardImage(file);
            cardImage.setComponents(components);
            cardImage.setImageComponents(imageComponents);
            cardImage.setQrComponent(qrComponent);
            return cardImage;
        }

        public QRComponent getQrComponent() {
            return qrComponent;
        }

        public void setQrComponent(String qr, float x, float y, float w, float h) {
            this.qrComponent = new QRComponent(qr, x, y, w, h);
        }
    }
}
