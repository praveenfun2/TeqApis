package com.neo.DatabaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Paper {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 1024)
    private String des;

    @Id
    @GeneratedValue
    private long pqid;

    public Paper(String name, String des) {
        this.name = name;
        this.des = des;
    }

    public Paper(String name, String desc, long pqid) {
        this.name = name;
        this.des = desc;
        this.pqid = pqid;
    }

    public Paper() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPqid() {
        return pqid;
    }

    public void setPqid(long pqid) {
        this.pqid = pqid;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
