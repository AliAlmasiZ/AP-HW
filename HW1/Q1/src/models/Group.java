package models;
/*
Explanation:
- In our app, we have groups that have some information.
- Group is something that we need to make it an object because it looks like an object (:
- put those information here and use them in your code.
 */

import models.enums.Currency;
import models.enums.GroupType;

import java.util.ArrayList;

public class Group {

    private GroupType type;
    private String name;
    private Integer ID;
    private final User creator;
    private final ArrayList<User> members;
    private final ArrayList<Expense> allExpense = new ArrayList<Expense>();


    public Group(String type, String name, User creator) {
        this.type = GroupType.stringToGroupType(type);
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

    public void addMember(User user) {
        this.members.add(user);
    }

    public Expense getExpense(User payer, User payee) {
        for (Expense expense : allExpense) {
            if(expense.getPayer().equals(payer) && expense.getPayee().equals(payee)) {
                return expense;
            }
        }
        Expense expense = new Expense(payer, payee, 0);
        allExpense.add(expense);
        return expense;
    }

}
