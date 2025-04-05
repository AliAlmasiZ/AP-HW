package views;

import controllers.SignupMenuController;
import models.App;
import models.enums.SignupMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SignupMenu implements AppMenu{
    private final SignupMenuController controller = new SignupMenuController();
    @Override
    public void checker(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if((matcher = SignupMenuCommands.SHOW_CURRENT_MENU.getMatcher(input)) != null) {
            System.out.println(App.getActiveMenu());
        } else if((matcher = SignupMenuCommands.REGISTER.getMatcher(input)) != null) {
            System.out.println(controller.register(
                    matcher.group("username"),
                    matcher.group("password"),
                    matcher.group("email")
            ));
        } else if((matcher = SignupMenuCommands.LOGIN.getMatcher(input)) != null) {
            controller.goToLoginMenu();
        } else if((matcher = SignupMenuCommands.EXIT.getMatcher(input)) != null) {
            controller.exit();
        } else {
            System.out.println("invalid command");
        }
    }
}
