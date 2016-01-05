package com.lamyatweng.mmugraduation1.model;

public class Student {
    String name;
    int id;
    String course;
    int balanceCreditHour;
    double cgpa;
    int muet;
    double financialDue;

    public Student() {
    }

    public Student(String name, int id, String course, int balanceCreditHour, double cgpa, int muet, double financialDue) {
        this.name = name;
        this.id = id;
        this.course = course;
        this.balanceCreditHour = balanceCreditHour;
        this.cgpa = cgpa;
        this.muet = muet;
        this.financialDue = financialDue;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public int getBalanceCreditHour() {
        return balanceCreditHour;
    }

    public double getCgpa() {
        return cgpa;
    }

    public int getMuet() {
        return muet;
    }

    public double getFinancialDue() {
        return financialDue;
    }
}
