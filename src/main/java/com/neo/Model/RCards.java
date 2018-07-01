package com.neo.Model;

import java.util.ArrayList;
import java.util.List;

public class RCards {
    private List<RCard> fCards=new ArrayList<>(), bCards=new ArrayList<>();

    public RCards() {

    }

    public List<RCard> getfCards() {
        return fCards;
    }

    public void setfCards(List<RCard> fCards) {
        this.fCards = fCards;
    }

    public List<RCard> getbCards() {
        return bCards;
    }

    public void setbCards(List<RCard> bCards) {
        this.bCards = bCards;
    }
}
