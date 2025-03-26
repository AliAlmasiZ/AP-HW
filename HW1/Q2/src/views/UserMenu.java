package views;

import controllers.UserMenuController;
import models.enums.UserMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class UserMenu extends MainMenu implements AppMenu{
    UserMenuController controller = new UserMenuController();

    @Override
    public void checker(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if((matcher = UserMenuCommands.LIST_MY_ORDERS.getMatcher(input)) != null) {
            System.out.println(controller.listMyOrders());
        } else if((matcher = UserMenuCommands.SHOW_ORDER_DETAILS.getMatcher(input)) != null) {
            System.out.println(controller.showOrderDetails(matcher.group("orderId")));
        } else if((matcher = UserMenuCommands.EDIT_NAME.getMatcher(input)) != null) {
            System.out.println(controller.editName(
                    matcher.group("firstName"),
                    matcher.group("lastName"),
                    matcher.group("password")
            ));
        } else if((matcher = UserMenuCommands.EDIT_EMAIL.getMatcher(input)) != null) {
            System.out.println(controller.editEmail(matcher.group("email"), matcher.group("password")));
        } else if((matcher = UserMenuCommands.EDIT_EMAIL.getMatcher(input)) != null) {
            System.out.println(controller.editPassword(matcher.group("newPass"), matcher.group("oldPass")));
        } else if((matcher = UserMenuCommands.SHOW_MY_INFO.getMatcher(input)) != null) {
            System.out.println(controller.showMyInfo());
        } else if((matcher = UserMenuCommands.EDIT_EMAIL.getMatcher(input)) != null) {
            System.out.println();
        } else if((matcher = UserMenuCommands.EDIT_EMAIL.getMatcher(input)) != null) {
            System.out.println();
        } else if((matcher = UserMenuCommands.EDIT_EMAIL.getMatcher(input)) != null) {
            System.out.println();
        } else {
            System.out.println("invalid command");
        }
    }
}
