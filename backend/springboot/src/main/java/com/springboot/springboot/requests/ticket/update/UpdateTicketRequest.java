package com.springboot.springboot.requests.ticket.update;

public class UpdateTicketRequest {

    private String date;

    private Integer persons;

    private boolean checkin;

    public UpdateTicketRequest() {
    }

    public UpdateTicketRequest(String date, Integer persons, boolean checkin) {
        this.date = date;
        this.persons = persons;
        this.checkin = checkin;
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