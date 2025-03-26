package views;

import controllers.MainMenuController;
import models.App;
import models.enums.MainMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu implements AppMenu {
    MainMenuController controller = new MainMenuController();
    @Override
    public void checker(Scanner scanner) {
        String input = scanner.nextLine().trim();

        if(MainMenuCommands.GO_BACK.getMatcher(input) != null) {
            System.out.println(controller.goBack());
        } else if(MainMenuCommands.GO_TO.getMatcher(input) != null) {
            System.out.println(controller.goTo(input));
        } else if(MainMenuCommands.EXIT.getMatcher(input) != null) {
            controller.exit();
        } else {
            System.out.println("invalid command");
        }
    }
}
