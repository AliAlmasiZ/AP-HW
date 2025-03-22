package controllers;
/*
Explanation:
- This is a controller class for the login menu Controller.
- This class will be used to implement functions that do log in menu operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */

import models.App;
import models.Result;
import models.User;
import models.enums.LoginMenuCommands;
import models.enums.Menu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenuController {
    public Result loginUser(String input) {
        Matcher matcher = LoginMenuCommands.LOGIN_USER.getMatcher(input);
        if (matcher != null) {
            User user = App.findUserByUsername(matcher.group("username"));
            if (user == null) {
                return new Result(false, "username doesn't exist!");
            }
            if (!user.getPassword().equals(matcher.group("password"))) {
                return new Result(false, "password is incorrect!");
            }
            App.setActiveUser(user);
            App.setActiveMenu(Menu.DASHBOARD);
            return new Result(true, "user logged in successfully.you are now in dashboard!");
        }
        return new Result(false, "invalid command!");
    }

    public Result forgetPassword(String input) {
        Matcher matcher = LoginMenuCommands.FORGET_PASSWORD.getMatcher(input);
        if (matcher != null) {
            User user = App.findUserByUsername(matcher.group("username"));
            if (user == null) {
                return new Result(false, "username doesn't exist!");
            }
            if (!user.getEmail().equals(matcher.group("email"))) {
                return new Result(false, "email doesn't match!");
            }
            return new Result(true, "password : " + user.getPassword());
        }
        return new Result(false, "invalid command!");
    }

    public Result goToSignupMenu() {
        App.setActiveMenu(Menu.SIGN_UP_MENU);
        return new Result(true, "you are now in signup menu!");
    }
}
