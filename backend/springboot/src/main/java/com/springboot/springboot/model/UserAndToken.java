package com.springboot.springboot.model;

public class UserAndToken {

    private String token;
    private User user;


    public UserAndToken() {
    }

    public UserAndToken(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserAndToken token(String token) {
        setToken(token);
        return this;
    }

    public UserAndToken user(User user) {
        setUser(user);
        return this;
    }


}
