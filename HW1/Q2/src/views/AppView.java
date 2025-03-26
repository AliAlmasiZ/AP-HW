package views;

import models.App;
import models.enums.Menu;

import java.util.Scanner;

public class AppView {
    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Menu activeMenu;
        while((activeMenu = App.getActiveMenu()).equals(Menu.EXIT_MENU)) {
            activeMenu.checker(scanner);
        }
    }
}
