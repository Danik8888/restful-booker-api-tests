package io.demo.restfulbooker.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponse {

    private String token;
    private String reason;

    public String getToken() {
        return token;
    }

    public AuthResponse token(String token) {
        this.token = token;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public AuthResponse reason(String reason) {
        this.reason = reason;
        return this;
    }
}