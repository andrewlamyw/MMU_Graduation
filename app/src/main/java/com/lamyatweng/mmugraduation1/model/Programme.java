package com.lamyatweng.mmugraduation1.model;

/**
 * Created by choco on 24/12/2015.
 */
public class Programme {
    String name;
    String campus;
    String type;

    public Programme() {
    }

    public Programme(String type, String campus, String name) {
        this.type = type;
        this.campus = campus;
        this.name = name;
    }

    public Programme(String campus, String name) {
        this.campus = campus;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;

    }

    public String getCampus() {
        return campus;
    }
}
