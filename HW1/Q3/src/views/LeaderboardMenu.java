package views;

import controllers.LeaderBoardMenuController;
import models.App;
import models.enums.LeaderboardMenuCommands;
import models.enums.SignupMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LeaderboardMenu implements AppMenu {
    private final LeaderBoardMenuController controller = new LeaderBoardMenuController();
    @Override
    public void checker(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if((matcher = LeaderboardMenuCommands.SHOW_CURRENT_MENU.getMatcher(input)) != null) {
            System.out.println(App.getActiveMenu());
        } else if((matcher = LeaderboardMenuCommands.BACK.getMatcher(input)) != null) {
            controller.back();
        } else if((matcher = LeaderboardMenuCommands.EXIT.getMatcher(input)) != null) {
            controller.exit();
        } else {
            System.out.println("invalid command");
        }
    }
}
