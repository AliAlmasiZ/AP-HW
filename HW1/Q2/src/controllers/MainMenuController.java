package controllers;

import models.App;
import models.Result;
import models.Store;
import models.User;
import models.enums.MainMenuCommands;
import models.enums.Menu;

public class MainMenuController {
    public Result goTo(String input) {
        String menu = MainMenuCommands.GO_TO.getMatcher(input).group("nameOfTheMenu");
        Menu menu1 = Menu.stringToMenu(menu);
        if(menu1 == null || menu1.equals(Menu.EXIT_MENU))
            return new Result(false, "invalid command");
        if (
                menu1.equals(Menu.USER_MENU) &&
                        (App.getLoggedInAccount() == null || !App.getLoggedInAccount().getClass().equals(User.class)))
            return new Result(false, "You need to login as a user before accessing the user menu.");
        if(
                menu1.equals(Menu.STORE_MENU) &&
                        (App.getLoggedInAccount() == null || !App.getLoggedInAccount().getClass().equals(Store.class)))
            return new Result(false, "You should login as store before accessing the store menu.");
        App.setActiveMenu(menu1);
        return new Result(true, "Redirecting to the " + menu + " ...");
    }

    public Result goBack() {
        if(App.getActiveMenu().equals(Menu.MAIN_MENU))
            return new Result(false, "invalid command");
        App.setActiveMenu(Menu.MAIN_MENU);
        return new Result(true, "Redirecting to the " + Menu.MAIN_MENU + " ...");
    }

    public void exit() {
        App.setActiveMenu(Menu.EXIT_MENU);
    }

}
