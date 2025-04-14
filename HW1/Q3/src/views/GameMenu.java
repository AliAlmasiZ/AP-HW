package views;

import models.App;
import models.enums.GameMenuCommands;
import models.enums.SignupMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements AppMenu {
    @Override
    public void checker(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if((matcher = GameMenuCommands.SHOW_CURRENT_MENU.getMatcher(input)) != null) {
            System.out.println(App.getActiveMenu());
        } if((matcher = GameMenuCommands.SHOW_DETAIL.getMatcher(input)) != null) {
            System.out.println(App.getActiveMenu());
        } else {
            System.out.println("invalid command");
        }
    }
}
