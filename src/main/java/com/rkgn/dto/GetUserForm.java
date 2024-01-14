package com.rkgn.dto;

public class GetUserForm extends Form {
    private String username;

    public GetUserForm(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
