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
            stringBuilder.append("  ").append(i + 1).append("- ").append(products.get(i).getName()).append("\n");
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



    public double getTotalCost() {
        return totalCost;
    }



    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }
}