package com.neo.DatabaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Finish {

    @Column(nullable = false)
    private String fname;

    @Column(nullable = false, length = 1024)
    private String fDesc;

    @Id
    @GeneratedValue
    private long fid;

    public Finish(String name, String fDesc) {
        this.fname = name;
        this.fDesc = fDesc;
    }

    public Finish(String name, String fDesc, long fid) {
        this.fDesc = fDesc;
        this.fid = fid;
        this.fname = name;
    }

    public Finish() {
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }

    public String getfDesc() {
        return fDesc;
    }

    public void setfDesc(String fDesc) {
        this.fDesc = fDesc;
    }
}
