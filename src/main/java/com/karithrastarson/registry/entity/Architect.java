package com.karithrastarson.registry.entity;

import javax.persistence.*;
import java.text.AttributedCharacterIterator;
import java.util.List;

@Entity
public class Architect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long architectId;

    private String fullName;

    private String dob;

    private String university;

    @OneToMany(mappedBy = "architect")
    private List<Building> buildings;

    public Architect() {
        //Empty constructor for Spring
    }

    public Architect(String name, String dob, String university) {
        this.fullName = name;
        this.dob = dob;
        this.university = university;
    }

    public Architect(String name, String dob) {
        this.fullName = name;
        this.dob = dob;
        this.university = null;
    }

    public Long getId() {
        return architectId;
    }

    public long getArchitectId() {
        return architectId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDob() {
        return dob;
    }

    public String getUniversity() {
        return university;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object obj) {
        Architect comparison = (Architect) obj;
        return comparison.fullName.equals(this.fullName)
                && comparison.dob.equals(this.dob);
    }

    public void setUniversity(String university) {
        this.university = university;
    }
}
