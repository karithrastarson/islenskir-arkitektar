package com.karithrastarson.registry.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String address;

    @ManyToOne
    @JoinColumn(name = "architectId")
    private Architect architect;

    @OneToMany(mappedBy = "building")
    private List<Asset> assets;

    private String createdDate;

    public Building() {
        //Empty constructor for Spring
    }

    public Building(String address, Architect architect, String createdDate) {
        this.address = address;
        this.architect = architect;
        this.createdDate = createdDate;
    }
}
