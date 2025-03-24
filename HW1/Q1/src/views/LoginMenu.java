package views;
/*
Explanation:
- This is a view class for the login menu.
- This class should use to check inputs and print outputs for the login menu.
- notice that : this class should not have any logic and just use it to get inputs and handle it to use correct methods in controller.
 */


import controllers.LoginMenuController;
import models.enums.LoginMenuCommands;

import java.util.Scanner;

public class LoginMenu implements AppMenu {
    private final LoginMenuController loginMenuController = new LoginMenuController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine().trim();

        if (input.startsWith("login")) {
            System.out.println(loginMenuController.loginUser(input));
        } else if (input.startsWith("forget")) {
            System.out.println(loginMenuController.forgetPassword(input));
        } else if (LoginMenuCommands.GOTO_SIGNUP_MENU.getMatcher(input) != null) {
            System.out.println(loginMenuController.goToSignupMenu());
        } else if (LoginMenuCommands.EXIT.getMatcher(input) != null) {
            loginMenuController.exit();
        } else {
            System.out.println("invalid command!");
        }
    }
}
