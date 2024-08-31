package org.acme.domain.models;

import com.google.gson.Gson;

import java.io.Serializable;

public class UserAccount implements Serializable {
    private static Gson gson = new Gson();

    private String username;
    private String email; // TODO: lookup whether quarkus has the email validator
    private String password;

    public UserAccount() {}

    public UserAccount(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserAccount(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static String toJson(UserAccount userAccount) {
        return gson.toJson(userAccount);
    }

    public static UserAccount fromJson(String json) {
        return gson.fromJson(json, UserAccount.class);
    }
}
