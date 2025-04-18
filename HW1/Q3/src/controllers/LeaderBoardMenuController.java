package controllers;

import models.App;
import models.enums.Menu;

public class LeaderBoardMenuController {
    public void back() {
        App.setActiveMenu(Menu.MAIN_MENU);
    }
    public void exit() {
        App.setActiveMenu(Menu.EXIT_MENU);
    }
}
