package controllers;

import models.App;
import models.Product;
import models.Result;
import models.Store;

import java.util.ArrayList;
import java.util.List;

public class StoreMenuController extends MainMenuController{
    public Result addProduct(String name, String producerCost, String price, String about, String numberOfProducts) {
        Store store = (Store) App.getLoggedInAccount();
        double pc = Double.parseDouble(producerCost);
        double priceDouble = Double.parseDouble(price);
        if (pc > priceDouble) return new
                Result(false, "Selling price must be greater than or equal to the producer cost.");
        int productsNum = Integer.parseInt(numberOfProducts);
        if (productsNum <= 0)
            return new Result(false, "Number of products must be a positive number.");
        Product product = new Product(priceDouble, pc, productsNum, about, name, store);
        store.products.put(product.getID(), product);
        store.addCosts(pc * productsNum);
        App.products.put(product.getID(), product);
        return new Result(
                true,
                "Product \"" + product.getName() + "\" has been added successfully with ID "
                        + product.getID() + "."
        );
    }

    public Result applyDiscount(String ID, String discountPercent, String quantity) {
        long id = Long.parseLong(ID);
        int discount = Integer.parseInt(discountPercent);
        int q = Integer.parseInt(quantity);
        Store store = (Store) App.getLoggedInAccount();
        Product product = store.products.get(id);
        if (discount < 1 || discount > 100)
            return new Result(false, "Discount percentage must be between 1 and 100.");
        if (product == null)
            return new Result(false, "No product found.");
        if (q > product.getStock())
            return new Result(false, "Oops! Not enough stock to apply the discount to that many items.");
        product.setDiscount(discount);
        product.setDicountProducts(q);
        return new Result(
                true,
                "A " + discount + "% discount has been applied to " + quantity + " units of product ID " + id + "."
        );
    }

    public Result showProfit() {
        Store store = (Store) App.getLoggedInAccount();
        String message;
        double revenue = store.getRevenue();
        double costs = store.getCosts();
        double profit = revenue - costs;
        message = String.format("Total Profit: $%.1f\n(Revenue: $%.1f - Costs: $%.1f)", profit, revenue, costs);
        return new Result(true, message);
    }

    public Result showListOfProducts() {
        StringBuilder stringBuilder = new StringBuilder();
        Store store = (Store) App.getLoggedInAccount();
        if(store.products.isEmpty())
            return new Result(false, "No products available in the store.");
        List<Product> products = new ArrayList<>(store.products.values());
        products.sort((p1, p2) -> Long.compare(p1.getID(), p2.getID()));
        stringBuilder.append("Store Products (Sorted by date added)\n");
        for (Product product : products) {
            stringBuilder.append(printProduct(product));
        }
        stringBuilder.append("------------------------------------------------");
        return new Result(true, stringBuilder.toString());
    }

    public Result addStock(String productID, String amount) {
        long ID = Long.parseLong(productID);
        long stock = Long.parseLong(amount);
        Product product = ((Store) App.getLoggedInAccount()).products.get(ID);
        if(product == null)
            return new Result(false, "No product found.");
        if(stock < 1)
            return new Result(false, "Amount must be a positive number.");
        product.getSeller().addCosts(product.getProducerCost() * stock);
        product.addStock(stock);
        return new Result(
                true,
                amount + " units of \"" + product.getName() + "\" have been added to the stock."
        );
    }

    public Result updatePrice(String productID, String newPrice) {
        long ID = Long.parseLong(productID);
        double price = Double.parseDouble(newPrice);
        Product product = ((Store) App.getLoggedInAccount()).products.get(ID);
        if(product == null)
            return new Result(false, "No product found.");
        if(price <= 0)
            return new Result(false, "Price must be a positive number.");
        product.setPrice(price);
        String out = String.format("Price of \"%s\" has been updated to $%.1f.", product.getName(), price);
        return new Result(true, out);
    }



    private String printProduct(Product product) {
        StringBuilder stringBuilder = new StringBuilder();
        String price = product.stirngPrice();
        String ID = "ID: " + product.getID();
        if (product.getDicountProducts() > 0) {
            ID += "  **(On Sale! " + product.getDicountProducts() + " units discounted)**";
        }
        if(product.getStock() == 0) {
            ID += "  (**Sold out!**) ";
        }

        stringBuilder
                .append("------------------------------------------------\n")
                .append(ID).append("\n")
                .append("Name: ").append(product.getName()).append("\n")
                .append(price).append("\n")
                .append("Stock: ").append(product.getStock()).append("\n")
                .append("Sold: ").append(product.getSolds()).append("\n");


        return stringBuilder.toString();
    }
}
