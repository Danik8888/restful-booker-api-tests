package io.demo.restfulbooker.tests;

import io.demo.restfulbooker.config.TestConfig;
import io.demo.restfulbooker.models.BookingResponse;
import io.demo.restfulbooker.support.BookingFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Негативные сценарии")
class BookingNegativeTest extends BaseTest {

    @Test
    @DisplayName("PUT без авторизации → 403 Forbidden")
    void putWithoutAuthReturnsForbidden() {
        BookingResponse created = createBooking();

        given()
                .spec(commonJsonSpec)
                .body(BookingFactory.randomBooking())
        .when()
                .put("/booking/{id}", created.getBookingid())
        .then()
                .statusCode(403);
    }

    @Test
    @DisplayName("PUT с неверным паролем → 403 Forbidden")
    void putWithWrongCredentialsReturnsForbidden() {
        BookingResponse created = createBooking();

        given()
                .spec(commonJsonSpec)
                .auth().preemptive().basic(TestConfig.USERNAME, "wrong-password")
                .body(BookingFactory.randomBooking())
        .when()
                .put("/booking/{id}", created.getBookingid())
        .then()
                .statusCode(403);
    }

    @Test
    @DisplayName("GET несуществующего бронирования → 404")
    void getNonExistentBookingReturns404() {
        given()
                .spec(commonJsonSpec)
        .when()
                .get("/booking/{id}", 999_999_999)
        .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("POST c Content-Type application/xml (при валидном JSON-теле) → 400")
    void postWithXmlContentTypeReturnsBadRequest() {
        String validJsonBody = "{\"firstname\":\"XmlHeader\",\"lastname\":\"Petrov\",\"totalprice\":88,"
                + "\"depositpaid\":true,\"bookingdates\":{\"checkin\":\"2026-09-01\",\"checkout\":\"2026-09-02\"}}";

        given()
                .spec(commonJsonSpec)
                .header("Content-Type", "application/xml")
                .body(validJsonBody)
        .when()
                .post("/booking")
        .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("GET с Accept: application/xml — контент-негодиация, ответ в формате XML")
    void getWithXmlAcceptReturnsXml() {
        BookingResponse created = createBooking();

        String xml = given()
                .spec(commonJsonSpec)
                .header("Accept", "application/xml")
        .when()
                .get("/booking/{id}", created.getBookingid())
        .then()
                .statusCode(200)
                .extract()
                .asString();

        assertThat(xml).contains("<booking>", "<firstname>", "<lastname>", "<bookingdates>");
    }
}