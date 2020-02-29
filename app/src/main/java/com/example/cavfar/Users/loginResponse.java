package com.example.cavfar.Users;

public class loginResponse {
    private String message;
    private Usuario user;
    private String access_token;

    public loginResponse(String message, Usuario user, String access_token) {
        this.message = message;
        this.user = user;
        this.access_token = access_token;
    }

    public String getMessage() {
        return message;
    }

    public Usuario getUser() {
        return user;
    }

    public String getAccess_token() {
        return access_token;
    }
}
