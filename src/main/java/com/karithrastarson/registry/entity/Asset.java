package com.karithrastarson.registry.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String url;

    private String thumbnailUrl;

    private Long buildingId;

    private List<Long> architects;

    public Asset() {
        //Empty constructor for Spring
    }

    public Asset(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setBuilding(Long buildingId) {
        this.buildingId = buildingId;
    }

    public void addArchitect(Long architectId) {
        if (architects == null) {
            architects = new ArrayList<>();
        }
        architects.add(architectId);
    }

    public void setArchitects(List<Long> architects) {
        this.architects = architects;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Long getBuilding() {
       return buildingId;
    }

    public List<Long> getArchitects() {
        return architects;
    }
}
