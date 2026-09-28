package io.demo.restfulbooker.tests;

import io.demo.restfulbooker.models.Booking;
import io.demo.restfulbooker.models.BookingResponse;
import io.demo.restfulbooker.support.BookingFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@DisplayName("CRUD бронирований")
class BookingCrudTest extends BaseTest {

    @Test
    @DisplayName("POST /booking создаёт бронирование и возвращает его в теле ответа")
    void createBookingReturnsEcho() {
        Booking expected = BookingFactory.randomBooking();

        BookingResponse created = given()
                .spec(commonJsonSpec)
                .body(expected)
        .when()
                .post("/booking")
        .then()
                .statusCode(200)
                .body("bookingid", greaterThan(0))
                .extract()
                .as(BookingResponse.class);

        assertThat(created.getBooking())
                .as("созданное бронирование должно совпасть с отправленным")
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("GET /booking/{id} возвращает созданное бронирование")
    void createdBookingCanBeFetched() {
        BookingResponse created = createBooking();

        Booking fetched = given()
                .spec(commonJsonSpec)
        .when()
                .get("/booking/{id}", created.getBookingid())
        .then()
                .statusCode(200)
                .extract()
                .as(Booking.class);

        assertThat(fetched)
                .as("полученное бронирование должно совпадать с созданным")
                .usingRecursiveComparison()
                .isEqualTo(created.getBooking());
    }

    @Test
    @DisplayName("PUT /booking/{id} полностью заменяет бронирование")
    void fullUpdateReplacesBooking() {
        BookingResponse created = createBooking();
        Booking replacement = BookingFactory.randomBooking();

        Booking updated = given()
                .spec(authSpec())
                .body(replacement)
        .when()
                .put("/booking/{id}", created.getBookingid())
        .then()
                .statusCode(200)
                .extract()
                .as(Booking.class);

        assertThat(updated)
                .as("после PUT бронирование должно стать равно переданному целиком")
                .usingRecursiveComparison()
                .isEqualTo(replacement);
    }

    @Test
    @DisplayName("PATCH /booking/{id} обновляет только переданные поля")
    void partialUpdateChangesOnlyPassedFields() {
        BookingResponse created = createBooking();
        String newFirstname = "UpdatedFirstName";

        Booking updated = given()
                .spec(authSpec())
                .body(Map.of("firstname", newFirstname))
        .when()
                .patch("/booking/{id}", created.getBookingid())
        .then()
                .statusCode(200)
                .extract()
                .as(Booking.class);

        assertThat(updated.getFirstname()).isEqualTo(newFirstname);
        assertThat(updated.getLastname()).isEqualTo(created.getBooking().getLastname());
        assertThat(updated.getTotalprice()).isEqualTo(created.getBooking().getTotalprice());
        assertThat(updated.isDepositpaid()).isEqualTo(created.getBooking().isDepositpaid());
    }

    @Test
    @DisplayName("DELETE /booking/{id} удаляет бронирование: 201, далее GET → 404")
    void deleteRemovesBookingAndGetReturnsNotFound() {
        BookingResponse created = createBooking();

        String deleteBody = given()
                .spec(authSpec())
        .when()
                .delete("/booking/{id}", created.getBookingid())
        .then()
                .statusCode(201)
                .extract()
                .asString();

        assertThat(deleteBody).as("тело ответа об удалении").isEqualTo("Created");

        given()
                .spec(commonJsonSpec)
        .when()
                .get("/booking/{id}", created.getBookingid())
        .then()
                .statusCode(404);
    }
}