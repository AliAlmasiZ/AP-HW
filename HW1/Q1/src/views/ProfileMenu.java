package views;
/*
Explanation: 
- This is a view class for profile menu.
- This class should use to check inputs and print outputs for profile menu.
- notice that : this class should not have any logic and just use it to get inputs and handle it to use correct methods in controller.
 */


import controllers.ProfileMenuController;
import models.enums.ProfileMenuCommands;

import java.util.Scanner;

public class ProfileMenu implements AppMenu {
    private final ProfileMenuController profileMenuController = new ProfileMenuController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine().trim();

        if (ProfileMenuCommands.SHOW_USER_INFO.getMatcher(input) != null) {
            profileMenuController.showUserInfo();
        } else if (ProfileMenuCommands.CHANGE_CURRENCY.getMatcher(input) != null) {
            System.out.println(profileMenuController.changeCurrency(input));
        } else if (ProfileMenuCommands.CHANGE_USERNAME.getMatcher(input) != null) {
            System.out.println(profileMenuController.changeUsername(input));
        } else if (ProfileMenuCommands.CHANGE_PASSWORD.getMatcher(input) != null) {
            System.out.println(profileMenuController.changePassword(input));
        } else if (ProfileMenuCommands.BACK.getMatcher(input) != null) {
            System.out.println(profileMenuController.back());
        } else if (ProfileMenuCommands.EXIT.getMatcher(input) != null) {
            profileMenuController.exit();
        } else {
            System.out.println("invalid command!");
        }
    }
}
