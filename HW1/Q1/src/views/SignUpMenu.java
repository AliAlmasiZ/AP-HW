package views;

/*
Explanation:
- This is a view class for the SignUpMenu.
- This class should use to check inputs and print outputs for the SignUpMenu.
- notice that : this class should not have any logic and just use it to get inputs and handle it to use correct methods in controller.
 */

import controllers.SignUpMenuController;
import models.Result;
import models.enums.SignUpMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SignUpMenu implements AppMenu {
    private final SignUpMenuController signUpMenuController = new SignUpMenuController();
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if((matcher = SignUpMenuCommands.GOTO_LOGIN_MENU.getMatcher(input)) != null) {
            System.out.println(signUpMenuController.goToLoginMenu());
        } else if((matcher = SignUpMenuCommands.REGISTER.getMatcher(input)) != null){
            System.out.println(signUpMenuController.registerUser(
                    matcher.group("username"),
                    matcher.group("password"),
                    matcher.group("email"),
                    matcher.group("name")
            ));
        } else if((matcher = SignUpMenuCommands.EXIT.getMatcher(input)) != null) {
            signUpMenuController.exit();
        }else {
            System.out.println("invalid command!");
        }
    }
}
