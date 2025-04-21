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
    private static final ArrayList<Game> games = new ArrayList<>();



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

    public static ArrayList<Game> getLastGames() {
        ArrayList<Game> result = new ArrayList<>();
        int count = 0;
        for (int i = games.size() - 1; i >= 0; i--) {
            count++;
            result.add(games.get(i));
            if(count == 5)
                break;
        }
        return result;
    }

    public static ArrayList<User> getSortedUsers() {
        ArrayList<User> result = new ArrayList<>(usernameToUser.values());
        result.sort(App::compare);
        return result;
    }

    private static int compare(User u1, User u2) {
        int res = Integer.compare(u2.getPoint(), u1.getPoint());
        if(res != 0)
            return res;
        return String.CASE_INSENSITIVE_ORDER.compare(u1.getUsername(), u2.getUsername());
    }

    public static void addGame(Game g) {
        games.add(g);
    }

}
