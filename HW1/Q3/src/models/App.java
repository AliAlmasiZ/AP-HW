package models;

import models.enums.Menu;

import java.util.ArrayList;
import java.util.HashMap;

public class App {
    private static User activeUser = null;
    private static Menu activeMenu =  Menu.SIGNUP_MENU;
    private static final HashMap<String, User> usernameToUser = new HashMap<>();


    public static Menu getActiveMenu() {
        return activeMenu;
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

    public static void setActiveUser(User activeUser) {
        App.activeUser = activeUser;
    }
}
