package models;

import java.util.HashMap;

public class Game {
    private User[] users;
    private User playingUser;
    private HashMap<Integer, Tile> allTiles;

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
}