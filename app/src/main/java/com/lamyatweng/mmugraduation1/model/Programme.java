package com.lamyatweng.mmugraduation1.model;

public class Programme {
    String name;
    String faculty;
    String level;

    public Programme() {
        // empty default constructor, necessary for Firebase to be able to deserialize
    }

    public Programme(String name, String faculty, String level) {
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
