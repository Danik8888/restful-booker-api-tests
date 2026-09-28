package io.demo.restfulbooker.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingResponse {

    private int bookingid;
    private Booking booking;

    public int getBookingid() {
        return bookingid;
    }

    public BookingResponse bookingid(int bookingid) {
        this.bookingid = bookingid;
        return this;
    }

    public Booking getBooking() {
        return booking;
    }

    public BookingResponse booking(Booking booking) {
        this.booking = booking;
        return this;
    }
}