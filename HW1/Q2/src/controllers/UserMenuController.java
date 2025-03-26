package controllers;

import models.App;
import models.Result;
import models.User;
import models.enums.UserMenuCommands;


public class UserMenuController {
    public Result listMyOrders() {

    }

    public Result showOrderDetails(String orderId) {

    }

    public Result editName(String firstName, String lastName, String password) {
        User user = (User) App.getLoggedInAccount();
        if(!password.equals(user.getPassword()))
            return new Result(false, "Incorrect password. Please try again.");
        if(!firstName.equals(user.getFirstName()) || !lastName.equals(user.getLastName()))
            return new Result(false, "The new name must be different from the current name.");
        if(firstName.length() < 3 || lastName.length() < 3)
            return new Result(false, "Name is too short.");
        if(UserMenuCommands.NAME.getMatcher(firstName) == null || UserMenuCommands.NAME.getMatcher(lastName) == null)
            return new Result(false, "Incorrect name format.");
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return new Result(true, "Name updated successfully.");
    }

    public Result editEmail(String email, String password) {
        User user = (User) App.getLoggedInAccount();
        if(!password.equals(user.getPassword()))
            return new Result(false, "Incorrect password. Please try again.");
        if(email.equals(user.getEmail()))
            return new Result(false, "The new email must be different from the current email.");
        if(UserMenuCommands.EMAIL.getMatcher(email) == null)
            return new Result(false, "Email already exists.");
        if (App.users.get(email) != null || App.stores.get(email) != null)
            return new Result(false, "Email already exists.");
        user.setEmail(email);
        return new Result(true, "Email updated successfully.");
    }

    public Result editPassword(String newPass, String oldPass) {
        User user = (User) App.getLoggedInAccount();
        if(oldPass.equals(user.getPassword()))
            return new Result(false, "Incorrect password. Please try again.");
        if(oldPass.equals(newPass))
            return new Result(false, "The new password must be different from the old password.");
        if(UserMenuCommands.PASSWORD.getMatcher(newPass) == null)
            return new Result(false, "The new password is weak.");
        user.setPassword(newPass);
        return new Result(true, "Password updated successfully.");
    }

    public Result showMyInfo() {
        User user = (User) App.getLoggedInAccount();
        String message = String.format("Name: %s %s\nEmail: %s", user.getFirstName(), user.getLastName(), user.getEmail());

        return new Result(true, message);
    }

    public Result addAddress()

}
