package views;

import models.App;
import models.enums.Menu;

import java.util.Scanner;

public class AppView {
    public void runApp() {
        Scanner scanner = new Scanner(System.in);

        while(!App.getActiveMenu().equals(Menu.EXIT_MENU)) {
            App.getActiveMenu().checker(scanner);
        }
    }
}
