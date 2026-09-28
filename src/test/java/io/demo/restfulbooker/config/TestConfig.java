package io.demo.restfulbooker.config;

public final class TestConfig {

    public static final String BASE_URL = System.getProperty("base.url", "https://restful-booker.herokuapp.com");
    public static final String USERNAME = System.getProperty("api.username", "admin");
    public static final String PASSWORD = System.getProperty("api.password", "password123");

    private TestConfig() {
    }
}