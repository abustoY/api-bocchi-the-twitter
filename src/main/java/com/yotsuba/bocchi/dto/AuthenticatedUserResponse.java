package com.yotsuba.bocchi.dto;

public class AuthenticatedUserResponse {
    public String id;
    public String name;

    public AuthenticatedUserResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }
}