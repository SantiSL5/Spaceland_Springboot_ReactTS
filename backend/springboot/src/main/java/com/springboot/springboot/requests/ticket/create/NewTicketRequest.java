package com.springboot.springboot.requests.ticket.create;

public class NewTicketRequest {

    private String date;

    private Integer persons;

    public NewTicketRequest(String date, Integer persons) {
        this.date = date;
        this.persons = persons;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getPersons() {
        return persons;
    }

    public void setPersons(Integer persons) {
        this.persons = persons;
    }
}