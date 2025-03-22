package models;

import models.enums.Menu;
import views.AppMenu;
import views.SignUpMenu;

import java.util.ArrayList;

/*
Explanation:
- In out app, we need somewhere to keep our general data like list of users and list of groups and logged-in user etc.
- This class is the place for that.
- Put your general data here and use them in your code.
- you should put some functions here to manage your data too.
 */
public class App {
    private static ArrayList<User> users = new ArrayList<User>();
    private static Menu activeMenu = Menu.SignUpMenu;

    public boolean isValidUsername(String username) {
        for(User user : App.users) {
            if(user.getUsername().equals(username)) return false;
        }
        return true;
    }

    public static Menu getActiveMenu() {
        return activeMenu;
    }

    public static void setActiveMenu(Menu activeMenu) {
        App.activeMenu = activeMenu;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static User findUserByUsername(String username) {
        for (User user : users) {
            if(user.getUsername().equals(username)) return user;
        }
        return null;
    }
}
