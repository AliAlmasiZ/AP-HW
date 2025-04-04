package views;

import controllers.LoginMenuController;
import models.App;
import models.enums.LoginMenuCommands;
import models.enums.SignupMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu implements AppMenu{
    LoginMenuController controller = new LoginMenuController();
    @Override
    public void checker(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if((matcher = LoginMenuCommands.SHOW_CURRENT_MENU.getMatcher(input)) != null) {
            System.out.println(App.getActiveMenu());
        } else if((matcher = LoginMenuCommands.LOGIN.getMatcher(input)) != null) {
            System.out.println(controller.login(matcher.group("username"), matcher.group("password")));
        } else if((matcher = LoginMenuCommands.FORGET_PASSWORD.getMatcher(input)) != null) {
            System.out.println(controller.forgetPassword(matcher.group("username"), matcher.group("email")));
        } else {
            System.out.println("invalid command");
        }
    }
}
