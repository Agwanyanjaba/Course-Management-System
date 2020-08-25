package com.student.microservices.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Authentication {
    @Id
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Authentication{" + "username='" + username + " '\''," + "password='" + password + " ''}";
    }
}