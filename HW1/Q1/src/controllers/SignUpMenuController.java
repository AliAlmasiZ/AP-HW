package controllers;

import models.App;
import models.Result;
import models.User;
import models.enums.Menu;
import models.enums.SignUpMenuCommands;

import java.util.regex.Matcher;

public class SignUpMenuController {
    public Result registerUser(String username, String password, String email, String name ) {

            if (SignUpMenuCommands.USERNAME.getMatcher(username) == null) {
                return new Result(false, "username format is invalid!");
            } else if (App.getUserByUsername(username) != null) {
                return new Result(false, "this username is already taken!");
            } else if (SignUpMenuCommands.PASSWORD.getMatcher(password) == null) {
                return new Result(false, "password format is invalid!");
            } else if (SignUpMenuCommands.EMAIL.getMatcher(email) == null) {
                return new Result(false, "email format is invalid!");
            } else if (SignUpMenuCommands.NAME.getMatcher(name) == null) {
                return new Result(false, "name format is invalid!");
            } else {
                App.addUser(new User(username, password, email, name));

                App.setActiveMenu(Menu.LOGIN_MENU);
                return new Result(true, "user registered successfully.you are now in login menu!");
            }
    }
    public Result goToLoginMenu() {
        App.setActiveMenu(Menu.LOGIN_MENU);
        return new Result(true, "you are now in login menu!");
    }

    public void exit() {
        App.setActiveMenu(Menu.EXIT_MENU);
    }
}
