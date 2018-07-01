package com.neo.Model;

import java.util.ArrayList;

/**
 * Created by localadmin on 17/6/17.
 */
public class RCartItem {

    private String sellerId;
    private boolean customCard;
    private ArrayList<Paper> papers=new ArrayList<>();
    private ArrayList<Finish> finishes=new ArrayList<>();

    public RCartItem() {

    }

    public RCartItem(Long id) {
        this.id = id;
    }

    private Long id, fsId, psId;
    private int quantity;
    private RItem fItem, bItem;

    public ArrayList<Paper> getPapers() {
        return papers;
    }

    public void setPapers(ArrayList<Paper> papers) {
        this.papers = papers;
    }

    public void addPaper(String name, Long psid, Float price){
        papers.add(new Paper(name, psid, price));
    }

    public ArrayList<Finish> getFinishes() {
        return finishes;
    }

    public void addFinish(String name, Long fsid, float price){
        finishes.add(new Finish(name, fsid, price));
    }

    public void setFinishes(ArrayList<Finish> finishes) {
        this.finishes = finishes;
    }

    public Long getFsId() {
        return fsId;
    }

    public void setFsId(Long fsId) {
        this.fsId = fsId;
    }

    public Long getPsId() {
        return psId;
    }

    public void setPsId(Long psId) {
        this.psId = psId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RItem getfItem() {
        return fItem;
    }

    public void setfItem(RItem fItem) {
        this.fItem = fItem;
    }

    public RItem getbItem() {
        return bItem;
    }

    public void setbItem(RItem bItem) {
        this.bItem = bItem;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public boolean isCustomCard() {
        return customCard;
    }

    public void setCustomCard(boolean customCard) {
        this.customCard = customCard;
    }

    public class Paper{
        private String name;
        private Long psid;
        private Float price;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public Paper() {

        }

        public Paper(String name, Long psid, Float price) {
            this.name = name;
            this.psid = psid;
            this.price = price;
        }

        public Long getPsid() {
            return psid;
        }

        public void setPsid(Long psid) {
            this.psid = psid;
        }

        public Float getPrice() {
            return price;
        }

        public void setPrice(Float price) {
            this.price = price;
        }
    }
    public  class Finish{
        String name;
        private Long fsid;
        float price;

        public Finish(String name, Long fsid, float price) {
            this.name = name;
            this.fsid = fsid;
            this.price = price;
        }

        public Finish() {
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getFsid() {
            return fsid;
        }

        public void setFsid(Long fsid) {
            this.fsid = fsid;
        }
    }
}
