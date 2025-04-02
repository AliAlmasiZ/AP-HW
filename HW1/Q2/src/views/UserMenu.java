package views;

import controllers.UserMenuController;
import models.enums.MainMenuCommands;
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
        } else if((matcher = UserMenuCommands.EDIT_PASSWORD.getMatcher(input)) != null) {
            System.out.println(controller.editPassword(matcher.group("newPass"), matcher.group("oldPass")));
        } else if((matcher = UserMenuCommands.SHOW_MY_INFO.getMatcher(input)) != null) {
            System.out.println(controller.showMyInfo());
        } else if((matcher = UserMenuCommands.ADD_ADDRESS.getMatcher(input)) != null) {
            System.out.println(controller.addAddress(
                    matcher.group("country"),
                    matcher.group("city"),
                    matcher.group("street"),
                    matcher.group("postal")
            ));
        } else if((matcher = UserMenuCommands.DELETE_ADDRESS.getMatcher(input)) != null) {
            System.out.println(controller.deleteAddress(matcher.group("id")));
        } else if((matcher = UserMenuCommands.LIST_ADDRESS.getMatcher(input)) != null) {
            System.out.println(controller.listMyAddresses());
        } else if((matcher = UserMenuCommands.ADD_CREDIT_CARD.getMatcher(input)) != null) {
            System.out.println(
                    controller.addCreditCard(
                    matcher.group("cardNumber"),
                    matcher.group("expirationDate"),
                    matcher.group("cvv"),
                    matcher.group("initialValue")
            ));
        } else if((matcher = UserMenuCommands.CHARGE_CREDIT_CARD.getMatcher(input)) != null) {
            System.out.println(controller.chargeCreditCard(matcher.group("amount"), matcher.group("id")));
        } else if((matcher = UserMenuCommands.CHECK_CREDIT_CARD_BALANCE.getMatcher(input)) != null) {
            System.out.println(controller.checkCreditCardBalance(matcher.group("id")));
        } else if((matcher = UserMenuCommands.SHOW_PRODUCTS_IN_CART.getMatcher(input)) != null) {
            System.out.println(controller.showProductsInCart());
        } else if((matcher = UserMenuCommands.CHECKOUT.getMatcher(input)) != null) {
            System.out.println(controller.checkout(matcher.group("cardID"), matcher.group("addressId")));
        } else if((matcher = UserMenuCommands.REMOVE_PRODUCT.getMatcher(input)) != null) {
            System.out.println(controller.removeFromCart(matcher.group("productID"), matcher.group("amount")));
        } else if((matcher = MainMenuCommands.GO_BACK.getMatcher(input)) != null) {
            System.out.println(controller.goBack());
        } else {
            System.out.println("invalid command");
        }
    }


}
