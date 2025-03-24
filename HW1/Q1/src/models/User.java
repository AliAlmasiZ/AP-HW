package models;

/*
Explanation:
- User is definitely an object in our app.
- put the information that you need to store about the user here.
- you can put some functions here to manage the user data too.
 */

import java.util.ArrayList;
import models.enums.Currency;

public class User {

    private String username;
    private String password;
    private final String email;
    private final String name;
    private Currency currency;
    private final ArrayList<Group> userGroups = new ArrayList<Group>();


    public User(String username, String password, String email, String name) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.currency = Currency.GTC;

        App.addUser(this);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Currency getCurrency() {
        return currency;
    }


    public ArrayList<Group> getUserGroups() {
        return userGroups;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addGroup(Group group) {
        this.userGroups.add(group);
    }


//    public boolean equals(User user) {
//        return this.username.equals(user.getUsername());
//    }
}