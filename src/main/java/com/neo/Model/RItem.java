package com.neo.Model;

import com.neo.DatabaseModel.Item;

import java.util.ArrayList;
import java.util.List;

import static com.neo.Utils.GeneralHelper.isNotEmpty;

/**
 * Created by Praveen Gupta on 7/29/2017.
 */
public class RItem {
    private String logo, qr;
    private Float lx, ly, lw, lh, qrs, qrx, qry, price;
    private Long cid, fItemId, id;
    private int type;
    private List<RTextBox> textBoxes=new ArrayList<>();
    private RCardImage image;
    private boolean customCard;

    public RItem() {
    }

    public void addTextBox(String content, float x, float y, float w, float h, long id){
        textBoxes.add(new RTextBox(content, x, y, w, h, id));
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getfItemId() {
        return fItemId;
    }

    public void setfItemId(Long fItemId) {
        this.fItemId = fItemId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Float getLx() {
        return lx;
    }

    public void setLx(Float lx) {
        this.lx = lx;
    }

    public Float getLy() {
        return ly;
    }

    public void setLy(Float ly) {
        this.ly = ly;
    }

    public Float getLw() {
        return lw;
    }

    public void setLw(Float lw) {
        this.lw = lw;
    }

    public Float getLh() {
        return lh;
    }

    public void setLh(Float lh) {
        this.lh = lh;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<RTextBox> getTextBoxes() {
        return textBoxes;
    }

    public void setTextBoxes(List<RTextBox> textBoxes) {
        this.textBoxes = textBoxes;
    }

    public Float getQrs() {
        return qrs;
    }

    public void setQrs(Float qrs) {
        this.qrs = qrs;
    }

    public RCardImage getImage() {
        return image;
    }

    public void setImage(RCardImage image) {
        this.image = image;
    }

    public Float getQrx() {
        return qrx;
    }

    public void setQrx(Float qrx) {
        this.qrx = qrx;
    }

    public Float getQry() {
        return qry;
    }

    public void setQry(Float qry) {
        this.qry = qry;
    }

    public boolean isCustomCard() {
        return customCard;
    }

    public void setCustomCard(boolean customCard) {
        this.customCard = customCard;
    }

    public class RTextBox {
        private String content;
        private float x, y, w, h;
        private long id;

        public RTextBox() {
        }

        public RTextBox(String content, float x, float y, float w, float h, long id) {
            this.content = content;
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }
}
