package com.springboot.springboot.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "attractions")
public class Attraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition="CLOB NOT NULL")
    @Lob
    private String description;

    @Column(name = "photo")
    private String photo;

    @Column(name = "ride_capacity")
    private Integer ride_capacity;

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name = "updated_at")
    private Timestamp updated_at;


    public Attraction() {
    }

    public Attraction(long id, String name, String description, String photo, Integer ride_capacity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.ride_capacity = ride_capacity;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getRide_capacity() {
        return ride_capacity;
    }

    public void setRide_capacity(Integer ride_capacity) {
        this.ride_capacity = ride_capacity;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

}