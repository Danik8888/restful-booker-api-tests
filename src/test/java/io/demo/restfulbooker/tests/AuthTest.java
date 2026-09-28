package io.demo.restfulbooker.tests;

import io.demo.restfulbooker.config.TestConfig;
import io.demo.restfulbooker.models.AuthResponse;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Авторизация")
class AuthTest extends BaseTest {

    @Test
    @DisplayName("Валидные учётные данные возвращают токен")
    void validCredentialsReturnToken() {
        AuthResponse auth = given()
                .spec(commonJsonSpec)
                .body(Map.of(
                        "username", TestConfig.USERNAME,
                        "password", TestConfig.PASSWORD))
        .when()
                .post("/auth")
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .as(AuthResponse.class);

        assertThat(auth.getToken()).as("токен не должен быть пустым").isNotBlank();
    }

    @Test
    @DisplayName("Неверный пароль: 200 + reason 'Bad credentials'")
    void invalidPasswordReturnsBadCredentials() {
        given()
                .spec(commonJsonSpec)
                .body(Map.of(
                        "username", TestConfig.USERNAME,
                        "password", "wrong-password"))
        .when()
                .post("/auth")
        .then()
                .statusCode(200)
                .body("reason", equalTo("Bad credentials"));
    }
}