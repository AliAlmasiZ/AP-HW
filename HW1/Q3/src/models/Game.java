package models;

import models.enums.CountryType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Game {
    private User[] users;
    private Country[] playerCountry;
    private User playingUser;
    private HashMap<Integer, Tile> allTiles;
    private final HashMap<String, Faction> factions = new HashMap<>();
    private final LinkedHashMap<CountryType, Country> countries = new LinkedHashMap<>();


    public Game(User[] users) {
        this.users = users;
        this.allTiles = new HashMap<>(App.tileMap());
        playingUser = this.users[0];
        this.playerCountry = new Country[5];
    }


    public User getPlayingUser() {
        return playingUser;
    }

    public void setPlayingUser(User playingUser) {
        this.playingUser = playingUser;
    }

    public Tile getTile(int index) {
        return allTiles.get(index);
    }

    public Faction getFaction(String name) {
        return factions.get(name);
    }

    public Country getCountryByType(CountryType type) {
        return countries.get(type);
    }

    public Country getCountryByIndex(int idx) {
        return playerCountry[idx];
    }

    public void addCountry(CountryType type, Country country) {
        playerCountry[countries.size()] = country;
        countries.put(type, country);
    }

    public void addFaction(Faction faction) {
        factions.put(faction.getName(), faction);
    }

    public User[] getAllPlayers() {
        return users;
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if(username.equals(user.getUsername()))
                return user;
        }
        return null;
    }
}