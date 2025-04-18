package controllers;

import models.App;
import models.enums.Menu;


public class MainMenuController {
    public void exit() {
        App.setActiveMenu(Menu.EXIT_MENU);
    }

    public void logout() {
        App.setActiveMenu(Menu.SIGNUP_MENU);
    }

    public void leaderBoard() {
        App.setActiveMenu(Menu.LEADERBOARD_MENU);
    }
}
