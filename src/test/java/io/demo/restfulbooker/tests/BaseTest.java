package io.demo.restfulbooker.tests;

import io.demo.restfulbooker.config.TestConfig;
import io.demo.restfulbooker.models.Booking;
import io.demo.restfulbooker.models.BookingResponse;
import io.demo.restfulbooker.support.BookingFactory;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.params.HttpConnectionParams;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public abstract class BaseTest {

    protected static RequestSpecification commonJsonSpec;

    @BeforeAll
    static void configure() {
        RestAssuredConfig config = RestAssuredConfig.config().httpClient(
                HttpClientConfig.httpClientConfig()
                        .setParam(HttpConnectionParams.CONNECTION_TIMEOUT, 10_000)
                        .setParam(HttpConnectionParams.SO_TIMEOUT, 20_000));

        commonJsonSpec = new RequestSpecBuilder()
                .setBaseUri(TestConfig.BASE_URL)
                .setContentType(ContentType.JSON)
                .setAccept("application/json")
                .setConfig(config)
                .log(LogDetail.ALL)
                .build();
    }

    protected static RequestSpecification authSpec() {
        return given()
                .spec(commonJsonSpec)
                .auth().preemptive().basic(TestConfig.USERNAME, TestConfig.PASSWORD);
    }

    protected static BookingResponse createBooking() {
        return createBooking(BookingFactory.randomBooking());
    }

    protected static BookingResponse createBooking(Booking booking) {
        return given()
                .spec(commonJsonSpec)
                .body(booking)
        .when()
                .post("/booking")
        .then()
                .statusCode(200)
                .extract()
                .as(BookingResponse.class);
    }
}