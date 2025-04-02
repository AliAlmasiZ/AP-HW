package views;


import controllers.ProductMenuController;
import models.Product;
import models.Result;
import models.enums.MainMenuCommands;
import models.enums.ProductMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProductMenu extends MainMenu implements AppMenu{
    private final ProductMenuController controller = new ProductMenuController();


    @Override
    public void checker(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if((matcher = ProductMenuCommands.SHOW_PRODUCTS.getMatcher(input)) != null) {
            System.out.println(controller.showProducts(matcher.group("sortBy"), 0));
        } else if((matcher = ProductMenuCommands.NEXT_PAGE.getMatcher(input)) != null) {
            System.out.println(controller.showNextPage());
        } else if((matcher = ProductMenuCommands.PAST_PAGE.getMatcher(input)) != null) {
            System.out.println(controller.showPastPage());
        } else if((matcher = ProductMenuCommands.SHOW_INFORMATION.getMatcher(input)) != null) {
            System.out.println(controller.showInformation(matcher.group("productId")));
        } else if((matcher = ProductMenuCommands.RATE_PRODUCT.getMatcher(input)) != null) {
            System.out.println(controller.rateProduct(matcher.group("number"), matcher.group("message"), matcher.group("id")));
        } else if((matcher = ProductMenuCommands.ADD_TO_CART.getMatcher(input)) != null) {
            System.out.println(controller.addToCart(matcher.group("productID"), matcher.group("amount")));
        } else if((matcher = MainMenuCommands.GO_BACK.getMatcher(input)) != null) {
            System.out.println(controller.goBack());
        } else {
            System.out.println("invalid command");
        }
    }
}
