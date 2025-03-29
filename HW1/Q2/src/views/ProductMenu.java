package views;


import controllers.ProductMenuController;
import models.enums.ProductMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProductMenu extends MainMenu implements AppMenu{
    ProductMenuController controller = new ProductMenuController();

    @Override
    public void checker(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if((matcher = ProductMenuCommands.SHOW_PRODUCTS.getMatcher(input)) != null) {
            System.out.println();
        } else if((matcher = ProductMenuCommands.SHOW_INFORMATION.getMatcher(input)) != null) {
            System.out.println();
        } else if((matcher = ProductMenuCommands.RATE_PRODUCT.getMatcher(input)) != null) {
            System.out.println();
        } else {
            System.out.println("invalid command");
        }
    }
}
