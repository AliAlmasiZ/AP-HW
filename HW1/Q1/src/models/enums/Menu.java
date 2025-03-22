package models.enums;

import views.*;

import java.util.Scanner;

/*
Explanation:
- In your code, you have some menus that are constants, and we move between them.
- a good way to handle this is to use enums to define them and use them in your code.
 */
public enum Menu {
    PROFILE_MENU(new ProfileMenu()),
    EXIT_MENU(new ExitMenu()),
    LOGIN_MENU(new LoginMenu()),
    SIGN_UP_MENU(new SignUpMenu()),
    DASHBOARD(new Dashboard());

    private final AppMenu menu ;

    Menu(AppMenu menu) {
        this.menu = menu;
    }

    public void checkCommand(Scanner scanner) {
        this.menu.check(scanner);
    }

}
