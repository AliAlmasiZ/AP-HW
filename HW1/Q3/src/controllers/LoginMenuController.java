package controllers;

import models.App;
import models.Result;
import models.User;
import models.enums.Menu;

public class LoginMenuController {

    public Result login(String username, String password) {
        User user = User.getUserByUsername(username);
        if(user == null) {
            return new Result(false, "username doesn't exist!");
        }
        if(!user.getPassword().equals(password)) {
            return new Result(false, "password is incorrect!");
        }
        App.setActiveUser(user);
        App.setActiveMenu(Menu.MAIN_MENU);
        return new Result(true, "user logged in successfully");
    }

    public Result forgetPassword(String username, String email) {
        User user = User.getUserByUsername(username);
        if(user == null) {
            return new Result(false, "username doesn't exist!");
        }
        if(!user.getEmail().equals(email)) {
            return new Result(false, "email doesn't match!");
        }
        return new Result(true, "password: " + user.getPassword());
    }


}
