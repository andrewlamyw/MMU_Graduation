package com.lamyatweng.mmugraduation1.model;

public class Course {
    String name;
    String campus;
    String type;

    public Course() {
    }

    public Course(String name, String campus, String type) {
        this.type = type;
        this.campus = campus;
        this.name = name;
    }

    public Course(String campus, String name) {
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
