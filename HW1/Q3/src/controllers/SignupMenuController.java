package controllers;

import models.App;
import models.Result;
import models.User;
import models.enums.Menu;
import models.enums.SignupMenuCommands;

public class SignupMenuController {


    public Result register(String username, String password, String email) {
        if (SignupMenuCommands.USERNAME.getMatcher(username) == null) {
            return new Result(false, "invalid username");
        }
        if (User.getUserByUsername(username) != null) {
            return new Result(false, "username is already taken");
        }
        if (SignupMenuCommands.PASSWORD_LENGTH.getMatcher(password) == null) {
            return new Result(false, "invalid length");
        }
        if (SignupMenuCommands.PASSWORD_WHITESPACE.getMatcher(password) == null) {
            return new Result(false, "don't use whitespace in password");
        }
        if (SignupMenuCommands.PASSWORD_STARTS.getMatcher(password) == null) {
            return new Result(false, "password must start with English letters");
        }
        if (SignupMenuCommands.PASSWORD_SPECIAL_LETTERS.getMatcher(password) == null) {
            return new Result(false, "password doesn't have special characters");
        }
        if (SignupMenuCommands.EMAIL.getMatcher(password) == null) {
            return new Result(false, "invalid email format");
        }
        return registerUser(username, password, email);
    }

    public void goToLoginMenu() {
        App.setActiveMenu(Menu.LOGIN_MENU);
    }


    private Result registerUser(String username, String password, String email) {
        try {
            App.getUsernameToUser().put(username, new User(username, password, email));
            return new Result(true, "user registered successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
            //return new Result(false, "Something went wrong");
        }
    }

}
