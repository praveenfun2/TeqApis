package com.neo.Model;

/**
 * Created by localadmin on 2/7/17.
 */
public class RApproval {

    private Long id;
    private String sid, name, cat, desc;
    private int type;

    public RApproval() {
    }


    public RApproval(Long id, String sid, String name, int type) {
        this.id = id;
        this.sid = sid;
        this.name = name;
        this.type = type;
    }

    public RApproval(Long id, String sid, String name, String cat, int type) {
        this.id = id;
        this.sid = sid;
        this.name = name;
        this.cat = cat;
        this.type = type;
    }

    public RApproval(Long id, String sid, String name, int type, String desc) {
        this.id = id;
        this.sid = sid;
        this.name = name;
        this.desc=desc;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}