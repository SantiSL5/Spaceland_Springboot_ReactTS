package com.springboot.springboot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "blacklist_token")
public class BlacklistToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "token")
    private String token;

    public BlacklistToken(long id, String token) {
        this.id = id;
        this.token = token;
    }

    public BlacklistToken() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
