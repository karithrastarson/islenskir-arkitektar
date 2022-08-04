package com.karithrastarson.registry.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long buildingId;

    private String address;

    private int postalCode;

    private String name;

    @ManyToOne
    @JoinColumn(name = "architectId")
    private Architect architect;

    @OneToMany(mappedBy = "building")
    private List<Asset> assets;

    private String createdDate;

    public Building() {
        //Empty constructor for Spring
    }

    public Building(String address, int postalCode, String name, String createdDate) {
        this.address = address;
        this.postalCode = postalCode;
        this.name = name;
        this.createdDate = createdDate;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArchitect(Architect architect) {
        this.architect = architect;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }

    public void addAsset(Asset asset) {
        if (this.assets == null) {
            this.assets = new ArrayList<>();
        }
        this.assets.add(asset);
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getArchitect() {
        if (architect == null) {
            return "";
        }
        return architect.toString();
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getName() {
        return name;
    }

    public int getPostalCode() {
        return postalCode;
    }

    @Override
    public String toString() {
        return address;
    }

    @Override
    public boolean equals(Object obj) {
        Building comparison = (Building) obj;
        return comparison.getAddress().equals(this.address);
    }
}
