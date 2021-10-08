package com.karithrastarson.registry.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Architect {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String fullName;

    private String dob;

    private String university;

    private List<Long> buildings;

    private List<Long> assets;

    public Architect() {
        //Empty constructor for Spring
    }

    public Architect(String name, String dob, String university) {
        this.fullName = name;
        this.dob = dob;
        this.university = university;
    }

    public List<Long> getAssets() {
        return assets;
    }

    public long getId() {
        return id;
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

    public List<Long> getBuildings() {
        return buildings;
    }

    public void addAsset(long assetId) {
        if (assets == null) {
            assets = new ArrayList<>();
        }

        assets.add(assetId);
    }
}
