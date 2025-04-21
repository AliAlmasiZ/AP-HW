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
import java.util.regex.Matcher;

public class ProfileMenu implements AppMenu {
    private final ProfileMenuController profileMenuController = new ProfileMenuController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;

        if ((matcher = ProfileMenuCommands.SHOW_USER_INFO.getMatcher(input)) != null) {
            profileMenuController.showUserInfo();
        } else if ((matcher = ProfileMenuCommands.CHANGE_CURRENCY.getMatcher(input)) != null) {
            System.out.println(profileMenuController.changeCurrency(matcher.group("newCurrency")));
        } else if ((matcher = ProfileMenuCommands.CHANGE_USERNAME.getMatcher(input)) != null) {
            System.out.println(profileMenuController.changeUsername(matcher.group("newUsername")));
        } else if ((matcher = ProfileMenuCommands.CHANGE_PASSWORD.getMatcher(input)) != null) {
            System.out.println(profileMenuController.changePassword(
                    matcher.group("oldPassword"),
                    matcher.group("newPassword")
            ));
        } else if ((matcher = ProfileMenuCommands.BACK.getMatcher(input)) != null) {
            System.out.println(profileMenuController.back());
        } else if ((matcher = ProfileMenuCommands.EXIT.getMatcher(input)) != null) {
            profileMenuController.exit();
        } else {
            System.out.println("invalid command!");
        }
    }
}
