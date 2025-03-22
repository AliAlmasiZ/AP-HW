package controllers;
/*
Explanation:
- This is a controller class for the sign-up menu Controller.
- This class will be used to implement functions that do sign up menu operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */

import models.App;
import models.Result;
import models.User;
import models.enums.Menu;
import models.enums.SignUpMenuCommands;

import java.util.SimpleTimeZone;
import java.util.regex.Matcher;

public class SignUpMenuController {
    public Result registerUser(String input) {
        input = input.trim();
        Matcher matcher = SignUpMenuCommands.REGISTER.getMatcher(input);
        if (matcher != null) {
            String username = matcher.group("username");
            String password = matcher.group("password");
            String email = matcher.group("email");
            String name = matcher.group("name");

            if (SignUpMenuCommands.USERNAME.getMatcher(username) == null) {
                return new Result(false, "username format is invalid!");
            } else if (App.findUserByUsername(username) != null) {
                return new Result(false, "this username is already taken!");
            } else if (SignUpMenuCommands.PASSWORD.getMatcher(password) == null) {
                return new Result(false, "password format is invalid!");
            } else if (SignUpMenuCommands.EMAIL.getMatcher(email) == null) {
                return new Result(false, "email format is invalid!");
            } else if (SignUpMenuCommands.NAME.getMatcher(name) == null) {
                return new Result(false, "name format is invalid!");
            } else {
                App.addUser(new User(username, password, email, name));
                App.setActiveMenu(Menu.LoginMenu);
                return new Result(true, "user registered successfully.you are now in login menu!");
            }
        } else {
            return new Result(false, "invalid command!");
        }
    }

}
