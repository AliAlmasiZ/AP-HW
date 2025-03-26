package models.enums;

import views.*;

import java.util.Scanner;

public enum Menu {
    MAIN_MENU(new MainMenu(), "MainMenu"),
    LOGIN_MENU(new LoginMenu(), "LoginMenu"),
    USER_MENU(new UserMenu(), "UserMenu"),
    PRODUCT_MENU(new ProductMenu(), "ProductMenu"),
    STORE_MENU(new StoreMenu(), "StoreMenu"),
    EXIT_MENU(new ExitMenu(), "ExitMenu");

    private final AppMenu menu;
    private final String name;

    Menu(AppMenu menu, String name) {
        this.menu = menu;
        this.name = name;
    }

    public void checker(Scanner scanner) {
        this.menu.checker(scanner);
    }


    @Override
    public String toString() {
        return this.name;
    }

    public static Menu stringToMenu(String input) {
        if(input.equals(MAIN_MENU.name)) return MAIN_MENU;
        if(input.equals(LOGIN_MENU.name)) return LOGIN_MENU;
        if(input.equals(USER_MENU.name)) return USER_MENU;
        if(input.equals(PRODUCT_MENU.name)) return PRODUCT_MENU;
        if(input.equals(STORE_MENU.name)) return STORE_MENU;
        if(input.equals(EXIT_MENU.name)) return EXIT_MENU;
        return null;
    }

}
