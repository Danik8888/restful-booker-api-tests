package io.demo.restfulbooker.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Booking {

    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    private BookingDates bookingdates;
    private String additionalneeds;

    public String getFirstname() {
        return firstname;
    }

    public Booking firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public Booking lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public Booking totalprice(int totalprice) {
        this.totalprice = totalprice;
        return this;
    }

    public boolean isDepositpaid() {
        return depositpaid;
    }

    public Booking depositpaid(boolean depositpaid) {
        this.depositpaid = depositpaid;
        return this;
    }

    public BookingDates getBookingdates() {
        return bookingdates;
    }

    public Booking bookingdates(BookingDates bookingdates) {
        this.bookingdates = bookingdates;
        return this;
    }

    public String getAdditionalneeds() {
        return additionalneeds;
    }

    public Booking additionalneeds(String additionalneeds) {
        this.additionalneeds = additionalneeds;
        return this;
    }
}