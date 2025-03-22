package models;

import models.enums.Menu;

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
    private static ArrayList<Group> groups = new ArrayList<Group>();
    private static Menu activeMenu = Menu.SIGN_UP_MENU;
    private static User activeUser = null;


    public static Menu getActiveMenu() {
        return activeMenu;
    }
    public static Integer getGroupsCount() {
        return groups.size();
    }
    public static User getActiveUser() {
        return activeUser;
    }

    public static void setActiveMenu(Menu activeMenu) {
        App.activeMenu = activeMenu;
    }

    public static void setActiveUser(User activeUser) {
        App.activeUser = activeUser;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static void addGroup(Group group) {
        groups.add(group);
    }
    public static User findUserByUsername(String username) {
        for (User user : users) {
            if(user.getUsername().equals(username)) return user;
        }
        return null;
    }

}
