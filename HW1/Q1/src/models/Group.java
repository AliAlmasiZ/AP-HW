package models;
/*
Explanation:
- In our app, we have groups that have some information.
- Group is something that we need to make it an object because it looks like an object (:
- put those information here and use them in your code.
 */

import models.enums.GroupType;

import java.util.ArrayList;

public class Group {
    private GroupType type;
    private String name;
    private Integer ID;
    private User creator;
    private ArrayList<User> members;


    public Group(GroupType type, String name, User creator) {
        this.type = type;
        this.name = name;
        this.creator = creator;

        this.ID = App.getGroupsCount() + 1;
        this.members = new ArrayList<User>();
    }

    public GroupType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Integer getID() {
        return ID;
    }

    public User getCreator() {
        return creator;
    }

    public ArrayList<User> getMembers() {
        return members;
    }
}
