package com.karithrastarson.registry.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String address;

    private List<Long> architects;

    private List<Long> assets;

    private String createdDate;

    public Building() {
        //Empty constructor for Spring
    }

    public Building(String address, long architectId, String createdDate) {
        this.address = address;

        this.architects = new ArrayList<>();
        architects.add(architectId);

        this.createdDate = createdDate;
    }

    public long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public List<Long> getArchitects() {
        if (architects == null) {
            return new ArrayList<>();
        }
        return architects;
    }

    public List<Long> getAssets() {
        return assets;
    }

    public String getCreatedDate() {
        return createdDate;
    }
}
