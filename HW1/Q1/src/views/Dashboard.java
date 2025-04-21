package views;
/*
Explanation:
- This is a view class for the dashboard.
- This class should use to check inputs and print outputs for the dashboard.
- notice that : this class should not have any logic and just use it to get inputs and handle it to use correct methods in controller.
 */

import controllers.DashboardController;
import jdk.jfr.DataAmount;
import models.App;
import models.enums.DashboardCommands;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class Dashboard implements AppMenu {
    private final DashboardController dashboardController = new DashboardController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;

        if ((matcher = DashboardCommands.CREATE_GROUP.getMatcher(input)) != null) { //handle create group command
            System.out.println(dashboardController.createGroup(
                    matcher.group("name"),
                    matcher.group("type")
            ));
        }
        else if (DashboardCommands.SHOW_MY_GROUPS.getMatcher(input) != null) { //handle show my groups command
            dashboardController.showMyGroups();
        }
        else if ((matcher = DashboardCommands.ADD_USER_TO_GROUP.getMatcher(input)) != null) { //handle add user to group command
            System.out.println(dashboardController.addUserToGroup(
                    matcher.group("username"),
                    matcher.group("email"),
                    matcher.group("groupId")
            ));
        }
        else if ((matcher = DashboardCommands.ADD_EXPENSE.getMatcher(input)) != null) { //handle add expense command
            int numberOfUsers = Integer.parseInt(matcher.group("numberOfUsers"));
            ArrayList<String> lines = new ArrayList<>();
            for (int i = 0; i < numberOfUsers; i++) {
                lines.add(scanner.nextLine());
            }
            System.out.println(dashboardController.addExpense(
                    matcher.group("groupId"),
                    matcher.group("totalExpense"),
                    matcher.group("equality"),
                    lines
            ));
        }
        else if ((matcher = DashboardCommands.SHOW_BALANCE.getMatcher(input)) != null) { //handle show balance command
            System.out.println(dashboardController.showBalance(matcher.group("username")));
        }
        else if ((matcher = DashboardCommands.SETTLE_UP.getMatcher(input)) != null) { // handle settle up command
            System.out.println(dashboardController.settleUp(
                    matcher.group("username"),
                    matcher.group("inputMoney")
            ));
        }
        else if ((matcher = DashboardCommands.GOTO_PROFILE_MENU.getMatcher(input)) != null) { // handle go to profile menu command
            System.out.println(dashboardController.goToProfileMenu());
        }
        else if (DashboardCommands.LOGOUT.getMatcher(input) != null) { // handle logout
            System.out.println(dashboardController.logout());
        }
        else if(DashboardCommands.EXIT.getMatcher(input) != null) {
            dashboardController.exit();
        }
        else {
            System.out.println("invalid command!");
        }
    }
}
