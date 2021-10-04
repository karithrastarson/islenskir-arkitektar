package com.karithrastarson.registry.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Architect {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long architectId;

    private String fullName;

    private String dob;

    private String university;

    @OneToMany(mappedBy = "architect")
    private List<Building> buildings;

    @OneToMany(mappedBy = "architect")
    private List<Asset> assets;

    public Architect() {
        //Empty constructor for Spring
    }

    public Architect(String name, String dob, String university) {
        this.fullName = name;
        this.dob = dob;
        this.university = university;
    }

    public Long getId() {
        return architectId;
    }

    public List<Asset> getAssets() {
        return assets;
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
}
