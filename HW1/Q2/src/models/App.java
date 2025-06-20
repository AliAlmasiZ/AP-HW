package models;

import models.enums.Menu;

import java.util.ArrayList;
import java.util.HashMap;

public class App {
    public static final HashMap<Long, Product> products = new HashMap<>();//id to product
    public static final HashMap<String, User> users = new HashMap<>();//email to user
    public static final HashMap<String, Store> stores = new HashMap<>();//email to store
    private static Menu activeMenu = Menu.MAIN_MENU;
    private static Account loggedInAccount = null;


    public static Menu getActiveMenu() {
        return activeMenu;
    }

    public static void setActiveMenu(Menu activeMenu) {
        App.activeMenu = activeMenu;
    }

    public static Account getLoggedInAccount() {
        return loggedInAccount;
    }

    public static void setLoggedInAccount(Account loggedInAccount) {
        App.loggedInAccount = loggedInAccount;
    }
}
