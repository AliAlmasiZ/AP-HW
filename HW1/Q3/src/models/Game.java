package models;

import models.enums.CountryType;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Game {
    private User[] users;
    private User playingUser;
    private HashMap<Integer, Tile> allTiles;
    private final HashMap<String, Faction> factions = new HashMap<>();
    private final HashMap<CountryType, Country> countries = new HashMap<>();


    public Game(User user1, User user2, User user3, User user4) {
        users = new User[]{user1, user2, user3, user4};
        this.allTiles = new HashMap<>(App.tileMap());
        playingUser = users[0];
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

    public void addFaction(Faction faction) {
        factions.put(faction.getName(), faction);
    }
}