package controllers;

import models.App;
import models.Result;
import models.Store;
import models.User;
import models.enums.LoginMenuCommands;
import models.enums.Menu;

import java.util.regex.Matcher;

public class LoginMenuController extends MainMenuController {
    public Result createUser(String firstName, String lastName, String password, String reEnteredPassword, String email) {

        if (firstName.length() < 3 || lastName.length() < 3)
            return new Result(false, "Name is too short.");
        if (LoginMenuCommands.NAME.getMatcher(firstName) == null || LoginMenuCommands.NAME.getMatcher(lastName) == null)
            return new Result(false, "Incorrect name format.");

        Result result = checkPasswordAndEmail(password, reEnteredPassword, email);
        if(!result.isSuccessful()) return result;

        App.users.put(email, new User(firstName, lastName, password, email));
        return new Result(true, "User account for " + firstName + " " + lastName + " created successfully.");

    }

    public Result createStore(String brand, String password, String reEnterPassword, String email) {
        if(brand.length() < 3)
            return new Result(false, "Brand name is too short.");

        Result result = checkPasswordAndEmail(password, reEnterPassword, email);
        if(!result.isSuccessful()) return result;

        App.stores.put(email, new Store(brand, password, email));
        return new Result(true, "Store account for \"" + brand + "\" created successfully.");
    }

    public Result loginUser(String input) {
        Matcher matcher = LoginMenuCommands.LOGIN_USER.getMatcher(input);
        String email = matcher.group("email");
        String password = matcher.group("password");
        User user = App.users.get(email);
        if(user == null)
            return new Result(false, "No user account found with the provided email.");
        if(!password.equals(user.getPassword()))
            return new Result(false, "Password is incorrect.");

        App.setLoggedInAccount(user);
        App.setActiveMenu(Menu.MAIN_MENU);
        return new Result(true, "User logged in successfully. Redirecting to the MainMenu ...");
    }

    public Result loginStore(String input) {
        Matcher matcher = LoginMenuCommands.LOGIN_STORE.getMatcher(input);
        String email = matcher.group("email");
        String password = matcher.group("password");
        Store store = App.stores.get(email);
        if(store == null)
            return new Result(false, "No store account found with the provided email.");
        if(password.equals(store.getPassword()))
            return new Result(false, "Password is incorrect.");

        App.setLoggedInAccount(store);
        App.setActiveMenu(Menu.MAIN_MENU);
        return new Result(true, "Store logged in successfully. Redirecting to the MainMenu ...");
    }

    private Result checkPasswordAndEmail(String password, String reEnteredPassword, String email) {
        if (LoginMenuCommands.PASSWORD.getMatcher(password) == null)
            return new Result(false, "Incorrect password format.");
        if (!password.equals(reEnteredPassword))
            return new Result(false, "Re-entered password is incorrect.");
        if (LoginMenuCommands.EMAIL.getMatcher(email) == null)
            return new Result(false, "Incorrect email format.");
        if (App.users.get(email) != null || App.stores.get(email) != null)
            return new Result(false, "Email already exists.");
        return new Result(true, null);
    }

    public Result logout() {
        if(App.getLoggedInAccount() == null)
            return new Result(false, "You should login first.");
        App.setLoggedInAccount(null);
        App.setActiveMenu(Menu.MAIN_MENU);
        return new Result(true, "Logged out successfully. Redirecting to the MainMenu ...");
    }

    public Result deleteAccount(String input) {
        String password = LoginMenuCommands.DELETE_ACOUNT.getMatcher(input).group("password");
        String reEnterPassword = LoginMenuCommands.DELETE_ACOUNT.getMatcher(input).group("reEnterPassword");
        if(App.getLoggedInAccount() == null)
            return new Result(false, "You should login first.");
        if(!password.equals(reEnterPassword))
            return new Result(false, "Re-entered password is incorrect.");
        if(!password.equals(App.getLoggedInAccount().getPassword()))
            return new Result(false, "Password is incorrect.");
        //TODO
        App.setActiveMenu(Menu.MAIN_MENU);
        return new Result(true, "Account deleted successfully. Redirecting to the MainMenu ...");
    }



}
