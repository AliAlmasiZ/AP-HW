package views;

import controllers.StoreMenuController;
import models.enums.MainMenuCommands;
import models.enums.StoreMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class StoreMenu extends MainMenu implements AppMenu{
    StoreMenuController controller = new StoreMenuController();

    @Override
    public void checker(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if((matcher = StoreMenuCommands.ADD_PRODUCT.getMatcher(input)) != null) {
            System.out.println(controller.addProduct(
                    matcher.group("name"),
                    matcher.group("producerCost"),
                    matcher.group("price"),
                    matcher.group("aboutThisItem"),
                    matcher.group("NumberOfProductToSell")
            ));
        } else if((matcher = StoreMenuCommands.APPLY_DISCOUNT.getMatcher(input)) != null) {
            System.out.println(controller.applyDiscount(
                    matcher.group("productID"),
                    matcher.group("discountPercentage"),
                    matcher.group("quantity")
            ));
        } else if((matcher = StoreMenuCommands.SHOW_PROFIT.getMatcher(input)) != null) {
            System.out.println(controller.showProfit());
        } else if((matcher = StoreMenuCommands.SHOW_LIST_OF_PRODUCTS.getMatcher(input)) != null) {
            System.out.println(controller.showListOfProducts());
        } else  if((matcher = StoreMenuCommands.ADD_STOCK.getMatcher(input)) != null) {
            System.out.println(controller.addStock(matcher.group("productId"), matcher.group("amount")));
        } else  if((matcher = StoreMenuCommands.UPDATE_PRICE.getMatcher(input)) != null) {
            System.out.println(controller.updatePrice(matcher.group("productId"), matcher.group("newPrice")));
        } else if((matcher = MainMenuCommands.GO_BACK.getMatcher(input)) != null) {
            System.out.println(controller.goBack());
        } else {
            System.out.println("invalid command");
        }
    }
}
