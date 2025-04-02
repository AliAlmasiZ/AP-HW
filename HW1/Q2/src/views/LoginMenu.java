package views;

import controllers.LoginMenuController;
import models.enums.LoginMenuCommands;
import models.enums.MainMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu extends MainMenu implements AppMenu {
    LoginMenuController controller = new LoginMenuController();

    @Override
    public void checker(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = LoginMenuCommands.CREATE_USER.getMatcher(input)) != null) {
            System.out.println(controller.createUser(
                    matcher.group("firstName"),
                    matcher.group("lastName"),
                    matcher.group("password"),
                    matcher.group("reEnteredPassword"),
                    matcher.group("email"))
            );
        } else if ((matcher = LoginMenuCommands.CREATE_STORE.getMatcher(input)) != null) {
            System.out.println(controller.createStore(
                    matcher.group("brand"),
                    matcher.group("password"),
                    matcher.group("reEnterPassword"),
                    matcher.group("email")
            ));
        } else if ((matcher = LoginMenuCommands.LOGIN_USER.getMatcher(input)) != null) {
            System.out.println(controller.loginUser(matcher.group("email"), matcher.group("password")));
        } else if ((matcher = LoginMenuCommands.LOGIN_STORE.getMatcher(input)) != null) {
            System.out.println(controller.loginStore(matcher.group("email"), matcher.group("password")));
        } else if ((matcher = LoginMenuCommands.LOGOUT.getMatcher(input)) != null) {
            System.out.println(controller.logout());
        } else if ((matcher = LoginMenuCommands.DELETE_ACOUNT.getMatcher(input)) != null) {
            System.out.println(controller.deleteAccount(
                    matcher.group("password"),
                    matcher.group("reEnterPassword")
            ));
        } else if((matcher = MainMenuCommands.GO_BACK.getMatcher(input)) != null) {
            System.out.println(controller.goBack());
        } else {
            System.out.println("invalid command");
        }
    }
}
