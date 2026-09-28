package io.demo.restfulbooker.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Здоровье сервиса")
class HealthCheckTest extends BaseTest {

    @Test
    @DisplayName("GET /ping возвращает 201 Created")
    void pingReturnsCreated() {
        String body = given()
                .spec(commonJsonSpec)
        .when()
                .get("/ping")
        .then()
                .statusCode(201)
                .extract()
                .asString();

        assertThat(body).as("тело healthcheck-ответа").isEqualTo("Created");
    }
}