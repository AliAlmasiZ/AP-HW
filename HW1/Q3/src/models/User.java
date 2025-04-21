package models;

import models.enums.CountryType;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String email;
    private int point;

    private Country playingCountry;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.point = 0;
    }

    public static User getUserByUsername(String username) {
        return App.getUsernameToUser().get(username);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Country getPlayingCountry() {
        return playingCountry;
    }

    public void setPlayingCountry(Country playingCountry) {
        this.playingCountry = playingCountry;
    }

    public void addPoint(int point) {
        this.point += point;
    }

    public int getPoint() {
        return point;
    }
}
