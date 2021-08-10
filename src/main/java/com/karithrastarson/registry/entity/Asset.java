package com.karithrastarson.registry.entity;

import javax.persistence.*;

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
}
