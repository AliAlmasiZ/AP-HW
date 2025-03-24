package controllers;

import models.*;
import models.enums.DashboardCommands;
import models.enums.Menu;

import java.util.ArrayList;
import java.util.regex.Matcher;

/*
Explanation:
- This is a controller class for the dashboard Controller.
- This class will be used to implement functions that do dashboard operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */

public class DashboardController {


    public Result createGroup(String input) {
        Matcher matcher = DashboardCommands.CREATE_GROUP.getMatcher(input);
        if (matcher != null) {
            if (DashboardCommands.NAME.getMatcher(matcher.group("name")) == null) {
                return new Result(false, "group name format is invalid!");
            }
            if (DashboardCommands.TYPE.getMatcher(matcher.group("type")) == null) {
                return new Result(false, "group type is invalid!");
            }
            Group newGroup = new Group(matcher.group("type"), matcher.group("name"), App.getActiveUser());
            App.addGroup(newGroup);
            App.getActiveUser().addGroup(newGroup);
            newGroup.addMember(App.getActiveUser());

            return new Result(true, "group created successfully!");
        } else return new Result(false, "invalid command!");
    }

    public void showMyGroups() {
        for (Group group : App.getActiveUser().getUserGroups()) {
            System.out.println("group name : " + group.getName());
            System.out.println("id : " + group.getID());
            System.out.println("type : " + group.getType());
            System.out.println("creator : " + group.getCreator().getName());
            System.out.println("members :");
            for (User member : group.getMembers()) {
                System.out.println(member.getName());
            }
            System.out.println("--------------------");
        }
        if (App.getActiveUser().getUserGroups().isEmpty())
            System.out.println();
    }

    public Result addUserToGroup(String input) {
        Matcher matcher = DashboardCommands.ADD_USER_TO_GROUP.getMatcher(input);
        if (matcher != null) {
            String username = matcher.group("username");
            String email = matcher.group("email");
            Integer groupID = Integer.parseInt(matcher.group("groupId"));
            Group group = App.getGroupByID(groupID);
            User user = App.getUserByUsername(username);

            if (user == null)
                return new Result(false, "user not found!");
            if (group == null)
                return new Result(false, "group not found!");
            if (group.getMembers().contains(user))
                return new Result(false, "user already in the group!");
            if (!user.getEmail().equals(email))
                return new Result(false, "the email provided does not match the username!");
            if (!group.getCreator().equals(App.getActiveUser()))
                return new Result(false, "only the group creator can add users!");
            group.addMember(user);
            user.addGroup(group);
            return new Result(true, "user added to the group successfully!");

        } else return new Result(false, "invalid command!");
    }

    public Result addExpense(String input, ArrayList<String> lines) {
        Matcher matcher = DashboardCommands.ADD_EXPENSE.getMatcher(input);
        if (matcher != null) {
            Integer groupID = Integer.parseInt(matcher.group("groupId"));
            Group group = App.getGroupByID(groupID);
            if (group == null) {
                return new Result(false, "group not found!");
            }
            Result result = checkAllUsers(lines, group);
            if (!result.isSuccessful()) {
                return result;
            }
            if (DashboardCommands.EXPENSE.getMatcher(matcher.group("totalExpense")) == null) {
                return new Result(false, "expense format is invalid!");
            }

            int totalExpense = Integer.parseInt(matcher.group("totalExpense"));
            if (matcher.group("equality").equals("equally")) {
                return addExpenseEqually(totalExpense, lines, group);
            }
            result = checkExpenseUnequally(lines, totalExpense);
            if (result.isSuccessful())
                return addExpenseUnequally(lines, group);
            return result;

        } else return new Result(false, "invalid command!");
    }

    private Result addExpenseEqually(Integer totalExpense, ArrayList<String> lines, Group group) {
        int individualExpense = totalExpense / lines.size();
        for (String line : lines) {
            line = line.trim();
            group.getExpense(
                    App.getActiveUser(),
                    App.getUserByUsername(line)
            ).addExpense(individualExpense);
        }
        return new Result(true, "expense added successfully!");
    }

    private Result addExpenseUnequally(ArrayList<String> lines, Group group) {
        for (String line : lines) {
            String[] split = line.trim().split("\\s+");
            group.getExpense(
                    App.getActiveUser(),
                    App.getUserByUsername(split[0])
            ).addExpense(Integer.parseInt(split[1]));
        }
        return new Result(true, "expense added successfully!");
    }

    public Result showBalance(String input) {
        Matcher matcher = DashboardCommands.SHOW_BALANCE.getMatcher(input);
        if (matcher != null) {
            User user = App.getUserByUsername(matcher.group("username"));
            if (user == null)
                return new Result(false, "user not found!");

            long oweAmount = getAllOwe(App.getActiveUser(), user); // bedehkari

            String output;
            if (oweAmount > 0) {
                output = String.format(
//                        "you owe %s %d %s in %s",
                        "%s owes you %d %s in %s",
                        user.getUsername(),
                        oweAmount / App.getActiveUser().getCurrency().getValue(), // makes value to Currency value!
                        App.getActiveUser().getCurrency().toString(),
                        commonGroups(user, App.getActiveUser())
                );
                return new Result(true, output);
            } else if (oweAmount < 0) {
                output = String.format(
//                        "%s owes you %d %s in %s",
                        "you owe %s %d %s in %s",
                        user.getUsername(),
                        -oweAmount / App.getActiveUser().getCurrency().getValue(),
                        App.getActiveUser().getCurrency().toString(),
                        commonGroups(user, App.getActiveUser())
                );
                return new Result(true, output);
            } else {
                return new Result(true, "you are settled with " + user.getUsername());
            }


        } else return new Result(false, "invalid command!");
    }

    public Result settleUp(String input) {
        Matcher matcher = DashboardCommands.SETTLE_UP.getMatcher(input);
        if (matcher != null) {
            User user = App.getUserByUsername(matcher.group("username"));
            if (user == null)
                return new Result(false, "user not found!");
            if (DashboardCommands.EXPENSE.getMatcher(matcher.group("inputMoney")) == null)
                return new Result(false, "input money format is invalid!");
            settleUp(Long.parseLong(matcher.group("inputMoney")), user);
            long oweAmount = getAllOwe(App.getActiveUser(), user) / App.getActiveUser().getCurrency().getValue();
            if (oweAmount > 0)
//                return new Result(
//                        true,
//                        "you owe " + user.getUsername() + " " +
//                                oweAmount + " " +
//                                App.getActiveUser().getCurrency() +  " now!"
//                );
                return new Result(
                        true,
                        user.getUsername() + " owes you " +
                                oweAmount + " " +
                                App.getActiveUser().getCurrency() + " now!"
                );
            if (oweAmount < 0)
//                return new Result(
//                        true,
//                        user.getUsername() + " owes you " +
//                                -oweAmount + " " +
//                                App.getActiveUser().getCurrency() +  " now!"
//                );
                return new Result(
                        true,
                        "you owe " + user.getUsername() + " " +
                                -oweAmount + " " +
                                App.getActiveUser().getCurrency() + " now!"
                );
            else
                return new Result(true, "you are settled with " + user.getUsername() + " now!");
        } else return new Result(false, "invalid command!");
    }

    public Result goToProfileMenu() {
        App.setActiveMenu(Menu.PROFILE_MENU);
        return new Result(true, "you are now in profile menu!");
    }

    public Result logout() {
        App.setActiveMenu(Menu.LOGIN_MENU);
        App.setActiveUser(null);
        return new Result(true, "user logged out successfully.you are now in login menu!");
    }

    public void exit() {
        App.setActiveMenu(Menu.EXIT_MENU);
    }

    private Result checkExpenseUnequally(ArrayList<String> lines, Integer totalExpense) {
        int currentExpense = 0;
        for (String line : lines) {

            String expense = DashboardCommands.UNEQUALLY.getMatcher(line.trim()).group("expense");
            if (DashboardCommands.EXPENSE.getMatcher(expense) == null)
                return new Result(false, "expense format is invalid!");

            currentExpense += Integer.parseInt(expense);
        }
        if (currentExpense != totalExpense)
            return new Result(false, "the sum of individual costs does not equal the total cost!");
        return new Result(true, "");
    }

    private Result checkAllUsers(ArrayList<String> lines, Group group) {
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        for (String line : lines) {
            String username = line.trim().split("\\s+")[0];
            if (App.getUserByUsername(username) == null || !App.getUserByUsername(username).getUserGroups().contains(group)) {
                if(flag) sb.append("\n");
                else flag = true;

                sb.append(username).append(" not in group!");
            }
        }

        if (!sb.isEmpty()) {
            return new Result(false, sb.toString());
        }
        return new Result(true, "");
    }

    private String commonGroups(User user1, User user2) {
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        for (Group group : user1.getUserGroups()) {
            if (user2.getUserGroups().contains(group)) {
                if (flag) {
                    sb.append(", ");
                } else {
                    flag = true;
                }
                sb.append(group.getName());
            }
        }
        sb.append("!");
        return sb.toString();
    }

    private long getAllOwe(User user1, User user2) {
        long bedehkari = 0;
        for (Group group : user1.getUserGroups()) {
            Expense expense1 = group.getExpense(user1, user2);
            Expense expense2 = group.getExpense(user2, user1);
            bedehkari += expense1.getValue() - expense2.getValue();
        }
        return bedehkari;
    }

    private void settleUp(long amount, User user) {
        amount = amount * App.getActiveUser().getCurrency().getValue();
        for (Group group : user.getUserGroups()) {
            if (App.getActiveUser().getUserGroups().contains(group)) {
                Expense owe1 = group.getExpense(user, App.getActiveUser());
                Expense owe2 = group.getExpense(App.getActiveUser(), user);
                amount += owe2.getValue();
                owe2.setValue(0);
                if (owe1.getValue() > amount) {
                    owe1.addValue(-amount);
                    amount = 0;
                } else {
                    amount -= owe1.getValue();
                    owe1.setValue(0);
                }
            }
            if (amount <= 0) break;
        }
        if (amount > 0) {
            for (Group group : user.getUserGroups()) {
                if (App.getActiveUser().getUserGroups().contains(group)) {
                    Expense owe1 = group.getExpense(user, App.getActiveUser());
                    Expense owe2 = group.getExpense(App.getActiveUser(), user);
                    owe2.setValue(amount);
                    return;
                }
            }
        }

    }
}