package com.neo.Model;

public class RCoverage {

    private int coverage;
    private float price;

    public RCoverage(int coverage, float price) {
        this.coverage = coverage;
        this.price = price;
    }

    public RCoverage() {
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCoverage() {
        return coverage;
    }

    public void setCoverage(int coverage) {
        this.coverage = coverage;
    }
}
