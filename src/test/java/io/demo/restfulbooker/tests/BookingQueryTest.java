package io.demo.restfulbooker.tests;

import io.demo.restfulbooker.models.BookingResponse;
import io.demo.restfulbooker.support.BookingFactory;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Выборки и схема бронирований")
class BookingQueryTest extends BaseTest {

    @Test
    @DisplayName("GET /booking возвращает непустой список, элементы содержат bookingid")
    void listBookingsReturnsBookingIds() {
        List<Map<String, Integer>> bookings = given()
                .spec(commonJsonSpec)
        .when()
                .get("/booking")
        .then()
                .statusCode(200)
                .extract()
                .as(new TypeRef<List<Map<String, Integer>>>() {
                });

        assertThat(bookings).isNotEmpty();
        assertThat(bookings).allSatisfy(item -> assertThat(item).containsKey("bookingid"));
    }

    @Test
    @DisplayName("Фильтр GET /booking?firstname=... находит бронирование по имени")
    void filterBookingsByFirstname() {
        BookingResponse created = createBooking();
        String uniqueFirstname = created.getBooking().getFirstname();

        List<Map<String, Integer>> found = given()
                .spec(commonJsonSpec)
                .queryParam("firstname", uniqueFirstname)
        .when()
                .get("/booking")
        .then()
                .statusCode(200)
                .extract()
                .as(new TypeRef<List<Map<String, Integer>>>() {
                });

        assertThat(found).as("по уникальному имени должно найтись хотя бы одно бронирование").isNotEmpty();
    }

    @Test
    @DisplayName("GET /booking/{id} возвращает полную схему бронирования")
    void getBookingByIdReturnsFullSchema() {
        BookingResponse created = createBooking();

        given()
                .spec(commonJsonSpec)
        .when()
                .get("/booking/{id}", created.getBookingid())
        .then()
                .statusCode(200)
                .body("firstname", notNullValue())
                .body("lastname", notNullValue())
                .body("totalprice", greaterThan(0))
                .body("depositpaid", notNullValue())
                .body("bookingdates.checkin", notNullValue())
                .body("bookingdates.checkout", notNullValue());
    }
}