package models;

import models.enums.CountryType;
import models.enums.Menu;

import java.util.*;

public class App {
    private static User activeUser = null;
    private static Menu activeMenu =  Menu.SIGNUP_MENU;
    private static Game activeGame = null;
    private static final HashMap<String, User> usernameToUser = new HashMap<>();
    private static final HashMap<Integer, Tile> allTiles = new HashMap<>();




    public static Menu getActiveMenu() {
        return activeMenu;
    }

    public static Game getActiveGame() {
        return activeGame;
    }

    public static void setActiveGame(Game activeGame) {
        App.activeGame = activeGame;
    }

    public static void setActiveMenu(Menu activeMenu) {
        App.activeMenu = activeMenu;
    }

    public static HashMap<String, User> getUsernameToUser() {
        return usernameToUser;
    }

    public static User getActiveUser() {
        return activeUser;
    }

    public static void addTile(Tile tile) {
        allTiles.put(tile.getId(), tile);
    }

    public static void setActiveUser(User activeUser) {
        App.activeUser = activeUser;
    }


    public static Map<Integer, Tile> tileMap() {
        return Collections.unmodifiableMap(allTiles);
    }

    public static Tile getTile(int index) {
        if(activeGame == null)
            return null;
        return activeGame.getTile(index);
    }


}
