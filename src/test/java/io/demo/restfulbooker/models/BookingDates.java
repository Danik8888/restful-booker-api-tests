package io.demo.restfulbooker.models;

public class BookingDates {

    private String checkin;
    private String checkout;

    public String getCheckin() {
        return checkin;
    }

    public BookingDates checkin(String checkin) {
        this.checkin = checkin;
        return this;
    }

    public String getCheckout() {
        return checkout;
    }

    public BookingDates checkout(String checkout) {
        this.checkout = checkout;
        return this;
    }
}