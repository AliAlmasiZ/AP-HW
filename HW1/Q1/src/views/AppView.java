package views;

/*
Explanation:
- This is a view class for the App.
- our app needs a place that handle menus (:
 */

import models.App;
import models.enums.Menu;

import java.util.Scanner;

public class AppView {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        do {
            App.getActiveMenu().checkCommand(scanner);
        } while (!App.getActiveMenu().equals(Menu.EXIT_MENU));
    }
}
