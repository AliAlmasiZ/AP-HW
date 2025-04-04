package views;

import models.App;
import models.enums.Menu;

import java.util.Scanner;

public class AppView {

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (!App.getActiveMenu().equals(Menu.EXIT_MENU)) {

        }
    }
}
