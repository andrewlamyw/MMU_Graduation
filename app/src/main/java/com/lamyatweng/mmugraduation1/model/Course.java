package com.lamyatweng.mmugraduation1.model;

public class Course {
    String name;
    String faculty;
    String level;

    public Course() {
        // Compulsory
    }

    public Course(String name, String faculty, String level) {
        this.name = name;
        this.faculty = faculty;
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public String getFaculty() {
        return faculty;
    }
}
