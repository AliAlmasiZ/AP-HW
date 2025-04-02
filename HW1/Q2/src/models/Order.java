package models;

import java.util.ArrayList;
import java.util.HashMap;

public class Order {
    private long ID;
    public final HashMap<Product, Integer> productsToQuantity;
    public final HashMap<Product, Double> productsToPrice;
    protected Address shippingAddress;
    protected double totalCost;

    public Order() {

        this.productsToQuantity = new HashMap<>();
        this.productsToPrice = new HashMap<>();
        totalCost = 0;
    }


    @Override
    public String toString() {
        ArrayList<Product> products = new ArrayList<>(this.productsToQuantity.keySet());
        products.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
        int total = 0;
        for (Integer value : this.productsToQuantity.values()) {
            total += value;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n")
                .append("Order ID: ").append(this.ID).append("\n")
                .append("Shipping Address: ").append(this.shippingAddress.toString()).append("\n")
                .append("Total Items Ordered: ").append(total).append("\n")
                .append("\n")
                .append("Products (Sorted by Name):  \n");
        for (int i = 0; i < products.size(); i++) {
            stringBuilder.append(i + 1).append("- ").append(products.get(i).getName()).append("\n");
        }
        stringBuilder.append("\n").append("━━━━━━━━━━━━━━━━━━━━━━━━━━  \n");
        return stringBuilder.toString();
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void addProduct(Product product, int quantity) {
        productsToQuantity.put(product, productsToQuantity.getOrDefault(product, 0) + quantity);
        productsToPrice.put(product, product.getPrice());
        totalCost += product.getPrice() * quantity;
        if(product.getDicountProducts() > 0)
            product.setDicountProducts(product.getDicountProducts() - quantity);
        product.addStock(-quantity);
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void removeProduct(Product product, int quantity) {
        totalCost -= productsToPrice.get(product) * quantity;
        product.addStock(quantity);
        productsToQuantity.computeIfPresent(product, (key, oldValue) -> oldValue - quantity);

    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }
}