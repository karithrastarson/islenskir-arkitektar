package com.karithrastarson.registry.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long assetId;

    private String name;

    private String url;

    private String thumbnailUrl;

    @ManyToOne
    @JoinColumn(name = "buildingId")
    private Building building;

    @ManyToOne
    @JoinColumn(name = "architectId")
    private Architect architect;

    public Asset() {
        //Empty constructor for Spring
    }

    public Asset(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void setArchitect(Architect architect) {
        this.architect = architect;
    }

    public String getUrl() {
        return url;
    }

    public long getAssetId() {
        return assetId;
    }

    public String getName() {
        return name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getBuilding() {
       if (building == null) {
           return "";
       }
       return building.toString();
    }

    public String getArchitect() {
        if (architect == null) {
            return "";
        }
        return architect.toString();
    }
}
