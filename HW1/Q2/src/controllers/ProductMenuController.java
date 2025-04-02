package controllers;

import models.*;
import views.MainMenu;

import java.util.ArrayList;
import java.util.List;

public class ProductMenuController extends MainMenuController {
    int page = 0;
    String sortBy;

    public Result showProducts(String sortBy, int page) {
        this.sortBy = sortBy;
        List<Product> products = sortedProducts(sortBy);
        if (page * 10 >= products.size() || page < 0)
            return new Result(false, "No more products available.");
        this.page = page;
        StringBuilder stringBuilder = new StringBuilder();
        sortBy = sortBy.substring(0, 1).toUpperCase() + sortBy.substring(1);
        stringBuilder.append("Product List (Sorted by: ").append(sortBy).append(")\n");

        for (int i = 0; i < Integer.min(products.size() - page * 10, 10); i++) {
            stringBuilder.append(printProduct(products.get(i + page * 10)));
        }
        stringBuilder
                .append("------------------------------------------------  \n")
                .append("(Showing ").append(page * 10 + 1).append("-").append(page * 10 + 10)
                .append(" out of ").append(products.size()).append(")");
        if ((page + 1) * 10 < products.size()) {
            stringBuilder.append("\nUse `show next 10 products' to see more.  ");
        }
        return new Result(true, stringBuilder.toString());
    }

    public Result showNextPage() {
        return showProducts(this.sortBy, this.page + 1);
    }

    public Result showPastPage() {
        return showProducts(this.sortBy, this.page - 1);
    }

    public Result showInformation(String productId) {
        long ID = Long.parseLong(productId);
        Product product = App.products.get(ID);
        if (product == null)
            return new Result(false, "No product found.");
        StringBuilder sb = new StringBuilder();
        sb
                .append("Product Details  \n").append("------------------------------------------------\n")
                .append("Name: ").append(product.getName()).append(product.saleOrSoldOut()).append("\n")
                .append("ID: ").append(product.getID()).append("\n")
                .append("Rating: ").append(String.format("%.1f/5\n", product.getRating()))
                .append(product.stirngPrice()).append("\n")
                .append("Brand: ").append(product.getSeller().getBrand()).append("\n")
                .append("Number of Products Remaining: ").append(product.getStock()).append("\n")
                .append("About this item:  \n")
                .append(product.getAbout()).append("\n")
                .append("\n")
                .append("Customer Reviews:  \n")
                .append("------------------------------------------------\n")
                .append(printReviews(product));
        return new Result(true, sb.toString());

    }

    public Result rateProduct(String number, String message, String productID) {
        long id = Long.parseLong(productID);
        int rating = Integer.parseInt(number);
        Product product = App.products.get(id);

        if (product == null)
            return new Result(false, "No product found.");
        if (rating < 1 || rating > 5)
            return new Result(false, "Rating must be between 1 and 5.");
        Account account = App.getLoggedInAccount();//can cause exception
        if (account == null || !account.getClass().equals(User.class))
            return new Result(false, "You must be logged in to rate a product.");
        product.rates.add(rating);
        product.reviews.add(message);
        product.raters.add((User) account);
        return new Result(true, "Thank you! Your rating and review have been submitted successfully.");
    }

    public Result addToCart(String productID, String amount) {
        long id = Long.parseLong(productID);
        int quantity = Integer.parseInt(amount);
        Product product = App.products.get(id);
        if (App.getLoggedInAccount() == null || !App.getLoggedInAccount().getClass().equals(User.class))
            return new Result(false, "You must be logged in to add items to your cart.");
        if (product == null)
            return new Result(false, "No product found.");
        if (quantity < 1)
            return new Result(false, "Quantity must be a positive number.");
        if (quantity > product.getStock())
            return new Result(
                    false,
                    "Only " + product.getStock() + " units of \"" + product.getName() + "\" are available."
            );
        User user = (User) App.getLoggedInAccount();
        user.getActiveCart().addProduct(product, quantity);
        return new Result(
                true,
                "\"" + product.getName() + "\" (x" + amount + ") has been added to your cart."
        );
    }

    private ArrayList<Product> sortedProducts(String sortBy) {
        ArrayList<Product> list = new ArrayList<>(App.products.values());
        switch (sortBy) {
            case "rate" -> list.sort((p2, p1) -> {
                int res =  Double.compare(p1.getRating(), p2.getRating());
                if(res == 0)
                    return Long.compare(p2.getID(), p1.getID());
                return res;
            });
            case "lower price to higher" -> list.sort((p1, p2) -> {
                int res = Double.compare(p1.getPrice(), p2.getPrice());
                if(res == 0)
                    return Long.compare(p1.getID(), p2.getID());
                return res;
            });
            case "higher price to lower" -> list.sort((p1, p2) -> {
                int res = Double.compare(p2.getPrice(), p1.getPrice());
                if(res == 0)
                    return Long.compare(p1.getID(), p2.getID());
                return res;
            });
            case "number of sold" -> list.sort((p2, p1) -> {
                int res = Long.compare(p1.getSolds(), p2.getSolds());
                if(res == 0)
                    return Long.compare(p2.getID(), p1.getID());
                return res;
            });
        }
        return list;
    }

    private String printProduct(Product product) {
        StringBuilder stringBuilder = new StringBuilder();
        String price = product.stirngPrice();
        String ID = "ID: " + product.getID() + product.saleOrSoldOut();

        stringBuilder
                .append("------------------------------------------------\n")
                .append(ID).append("\n")
                .append("Name: ").append(product.getName()).append("\n")
                .append(String.format("Rate: %.1f/5\n", product.getRating()))
                .append(price).append("\n")
                .append("Brand: ").append(product.getSeller().getBrand()).append("\n")
                .append("Stock: ").append(product.getStock()).append("\n");
//                .append("Sold: ").append(product.getSolds()).append("\n");


        return stringBuilder.toString();
    }

    private String printReviews(Product product) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < product.rates.size(); i++) {

            stringBuilder
                    .append(product.raters.get(i).getFullName())
                    .append(" (").append(product.rates.get(i)).append("/5)\n");
            if (product.reviews.get(i) != null)
                    stringBuilder.append("\"").append(product.reviews.get(i)).append("\"\n");
            stringBuilder.append("------------------------------------------------\n");
        }
        return stringBuilder.toString();
    }


}
