package com.springboot.springboot.requests.general;

import jakarta.persistence.Column;

import java.util.ArrayList;

public class DeleteManyRequest {
    @Column(name = "ids")
    private ArrayList<Long> ids;

    public DeleteManyRequest() {
    }

    public DeleteManyRequest(ArrayList<Long> ids) {
        this.ids = ids;
    }

    public ArrayList<Long> getIds() {
        return ids;
    }

    public void setIds(ArrayList<Long> ids) {
        this.ids = ids;
    }
}