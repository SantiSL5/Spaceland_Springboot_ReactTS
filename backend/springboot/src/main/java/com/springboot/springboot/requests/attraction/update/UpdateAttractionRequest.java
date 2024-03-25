package com.springboot.springboot.requests.attraction.update;

public class UpdateAttractionRequest {
    private String name;
    private String description;
    private String photo;
    private Integer ride_capacity;

    public UpdateAttractionRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
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
}