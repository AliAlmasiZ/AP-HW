package controllers;
/*
Explanation:
- This is a controller class for the profile menu Controller.
- This class will be used to implement functions that do profile menu operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */

import models.App;
import models.Result;
import models.User;
import models.enums.Menu;
import models.enums.ProfileMenuCommands;
import models.enums.Currency;

import java.util.regex.Matcher;

public class ProfileMenuController {
    public void showUserInfo() {
        User activeUser = App.getActiveUser();

        System.out.println("username : " + activeUser.getUsername());
        System.out.println("password : " + activeUser.getPassword());
        System.out.println("currency : " + activeUser.getCurrency());
        System.out.println("email : " + activeUser.getEmail());
        System.out.println("name : " + activeUser.getName());
    }

    public Result changeCurrency(String newCurrency) {
            Currency temp = Currency.stringToCurrency(newCurrency);
            if (temp == null) {
                return new Result(false, "currency format is invalid!");
            }
            App.getActiveUser().setCurrency(temp);
            return new Result(true, "your currency changed to " + temp + " successfully!");
    }

    public Result changeUsername(String newUsername) {
            if (newUsername.equals(App.getActiveUser().getUsername())) {
                return new Result(false, "please enter a new username!");
            }
            if (App.getUserByUsername(newUsername) != null) {
                return new Result(false, "this username is already taken!");
            }
            if (ProfileMenuCommands.USERNAME.getMatcher(newUsername) == null) {
                return new Result(false, "new username format is invalid!");
            }
            App.getActiveUser().setUsername(newUsername);
            return new Result(true, "your username changed to " + newUsername + " successfully!");
    }

    public Result changePassword(String oldPassword, String newPassword) {
            if(!oldPassword.equals(App.getActiveUser().getPassword())) {
                return new Result(false, "password incorrect!");
            }
            if(newPassword.equals(App.getActiveUser().getPassword())) {
                return new Result(false, "please enter a new password!");
            }
            if(ProfileMenuCommands.PASSWORD.getMatcher(newPassword) == null) {
                return new Result(false, "new password format is invalid!");
            }
            App.getActiveUser().setPassword(newPassword);
            return new Result(true, "your password changed successfully!");
    }

    public Result back() {
        App.setActiveMenu(Menu.DASHBOARD);
        return new Result(true, "you are now in dashboard!");
    }

    public void exit() {
        App.setActiveMenu(Menu.EXIT_MENU);
    }
}
