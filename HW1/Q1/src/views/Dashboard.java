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

public class Dashboard implements AppMenu {
    private final DashboardController dashboardController = new DashboardController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine().trim();

        if (DashboardCommands.CREATE_GROUP.getMatcher(input) != null) { //handle create group command
            System.out.println(dashboardController.createGroup(input));
        }
        else if (DashboardCommands.SHOW_MY_GROUPS.getMatcher(input) != null) { //handle show my groups command
            dashboardController.showMyGroups();
        }
        else if (DashboardCommands.ADD_USER_TO_GROUP.getMatcher(input) != null) { //handle add user to group command
            System.out.println(dashboardController.addUserToGroup(input));
        }
        else if (DashboardCommands.ADD_EXPENSE.getMatcher(input) != null) { //handle add expense command
            int numberOfUsers = Integer.parseInt(DashboardCommands.ADD_EXPENSE.getMatcher(input).group("numberOfUsers"));
            ArrayList<String> lines = new ArrayList<String>();
            for (int i = 0; i < numberOfUsers; i++) {
                lines.add(scanner.nextLine());
            }
            System.out.println(dashboardController.addExpense(input, lines));
        }
        else if (DashboardCommands.SHOW_BALANCE.getMatcher(input) != null) { //handle show balance command
            System.out.println(dashboardController.showBalance(input));
        }
        else if (DashboardCommands.SETTLE_UP.getMatcher(input) != null) { // handle settle up command
            System.out.println(dashboardController.settleUp(input));
        }
        else if (DashboardCommands.GOTO_PROFILE_MENU.getMatcher(input) != null) { // handle go to profile menu command
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
