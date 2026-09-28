package io.demo.restfulbooker.support;

import io.demo.restfulbooker.models.Booking;
import io.demo.restfulbooker.models.BookingDates;

import java.util.UUID;

public final class BookingFactory {

    private BookingFactory() {
    }

    public static Booking randomBooking() {
        String uniqueSuffix = UUID.randomUUID().toString().substring(0, 6);
        return new Booking()
                .firstname("Ivan" + uniqueSuffix)
                .lastname("Petrov")
                .totalprice(150)
                .depositpaid(true)
                .bookingdates(new BookingDates()
                        .checkin("2026-09-01")
                        .checkout("2026-09-10"))
                .additionalneeds("Late checkout");
    }
}