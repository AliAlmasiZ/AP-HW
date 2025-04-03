package models;

import java.util.HashMap;

public class ShoppingCart extends Order {
    public final HashMap<Product, Integer> productsToDiscount;

    public ShoppingCart() {
        super();
        productsToDiscount = new HashMap<>();
    }

    public Product findProductById(long id) {
        for (Product product : this.productsToQuantity.keySet()) {
            if(product.getID() == id)
                return product;
        }
        return null;
    }

    @Override
    public double getTotalCost() {
        totalCost = 0;
        for (Product product : productsToQuantity.keySet()) {
            double price = product.getOrginalPrice();
            if(productsToDiscount.get(product) != null) {
                price *= ((double) (100 - productsToDiscount.get(product)) / 100);
            }
            productsToPrice.replace(product, price);

            totalCost += price * productsToQuantity.get(product);
        }
        return totalCost;
    }

    public void addProduct(Product product, int quantity) {
        productsToQuantity.put(product, productsToQuantity.getOrDefault(product, 0) + quantity);
        productsToPrice.put(product, product.getPrice());
        if(product.getDicountProducts() > 0) {
            productsToDiscount.put(product, product.getDiscount());
            product.setDicountProducts(product.getDicountProducts() - quantity);
        }
        product.addStock(-quantity);
    }

    public void removeProduct(Product product, int quantity) {
        product.addStock(quantity);
        if(productsToDiscount.containsKey(product)) {
            product.setDicountProducts(product.getDicountProducts() + quantity);
        }
        productsToQuantity.computeIfPresent(product, (key, oldValue) -> oldValue - quantity);

    }
}
