package models.enums;

import views.*;

import java.util.Scanner;

public enum Menu {
    SIGNUP_MENU(new SignupMenu()),
    LOGIN_MENU(new LoginMenu()),
    MAIN_MENU(new MainMenu()),
    LEADERBOARD_MENU(new LeaderboardMenu()),
    GAME_MENU(new GameMenu()),
    EXIT_MENU(new ExitMenu())

    ;

    private final AppMenu menu;

    Menu(AppMenu menu) {
        this.menu = menu;
    }

    public void checker(Scanner scanner) {
        menu.checker(scanner);
    }


    @Override
    public String toString() {
        switch (this) {
            case SIGNUP_MENU -> {
                return "signup menu";
            }
            case LOGIN_MENU -> {
                return "login menu";
            }
            case LEADERBOARD_MENU -> {
                return "leaderboard menu";
            }
            case MAIN_MENU -> {
                return "main menu";
            }
            case GAME_MENU -> {
                return "game menu";
            }
        }
        return null;
    }
}
