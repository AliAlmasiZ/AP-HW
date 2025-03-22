package views;
/*
Explanation:
- This is a view class for the dashboard.
- This class should use to check inputs and print outputs for the dashboard.
- notice that : this class should not have any logic and just use it to get inputs and handle it to use correct methods in controller.
 */

import jdk.jfr.DataAmount;
import models.App;
import models.enums.DashboardCommands;

import java.util.Scanner;

public class Dashboard implements AppMenu {
    private final Dashboard dashboard = new Dashboard();
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine().trim();
        if(DashboardCommands.CREATE_GROUP.getMatcher(input) != null) {

        } else if()
    }
}
