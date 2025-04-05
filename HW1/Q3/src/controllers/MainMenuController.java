package controllers;

import models.App;
import models.enums.Menu;


public class MainMenuController {
    public void exit() {
        App.setActiveMenu(Menu.EXIT_MENU);
    }
}
