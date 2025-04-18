package views;

import controllers.MainMenuController;
import models.App;
import models.enums.MainMenuCommands;
import models.enums.SignupMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu implements AppMenu{
    private final MainMenuController controller = new MainMenuController();
    @Override
    public void checker(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if((matcher = MainMenuCommands.SHOW_CURRENT_MENU.getMatcher(input)) != null) {
            System.out.println(App.getActiveMenu());
        } else if((matcher = MainMenuCommands.LEADERBOARD.getMatcher(input)) != null) {
            controller.leaderBoard();
        } else if((matcher = MainMenuCommands.LOGOUT.getMatcher(input)) != null) {
            controller.logout();
        } else if((matcher = MainMenuCommands.EXIT.getMatcher(input)) != null) {
            controller.exit();
        } else {
            System.out.println("invalid command");
        }
    }
}
