package models;

import java.util.ArrayList;

public class Product {
    private static long counts = 100;
    private String name;
    private Store seller;
    private String about;
    private long stock;
    public final ArrayList<Integer> rates = new ArrayList<>();
    public final ArrayList<String> reviews = new ArrayList<>();
    public final ArrayList<User> raters = new ArrayList<>();
    private double producerCost;
    private double price;
    private long solds;
    private final long ID;
    private int discount;
    private int dicountProducts;

    public Product(double price, double producerCost, long amount, String about, String name, Store seller) {
        counts++;
        this.ID = counts;
        this.solds = 0;
        this.price = price;
        this.producerCost = producerCost;
        this.stock = amount;
        this.about = about;
        this.name = name;
        discount = 0;
        dicountProducts = 0;
        this.seller = seller;
    }

    public double getRating() {
        if(this.rates.isEmpty()) return 2.5;
        double res = 0;
        for (Integer rate : this.rates) {
            res += rate;
        }
        res /= rates.size();
        return res;
    }

    public double getPrice() {
        double percent = (double) (100 - this.discount) / 100;
        if(dicountProducts != 0)
            return this.price * percent;
        return this.price;
    }

    public double getOrginalPrice() {
        return this.price;
    }

    public double getProducerCost() {
        return producerCost;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getSolds() {
        return solds;
    }

    public void setSolds(long solds) {
        this.solds = solds;
    }

    public void addSolds(long solds) {
        this.solds += solds;
    }

    public long getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getAbout() {
        return about;
    }

    public long getStock() {
        return stock;
    }

    public void addStock(long stock) {
        this.stock += stock;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getDicountProducts() {
        return dicountProducts;
    }

    public void setDicountProducts(int dicountProducts) {
        this.dicountProducts = dicountProducts;
    }

    public Store getSeller() {
        return this.seller;
    }

    public String stirngPrice() {
        String price = String.format(
                "Price: $%.1f",
                this.getPrice()
        );
        if (this.getDicountProducts() > 0) {
            price = String.format(
                    "Price: ~$%.1f~ → $%.1f (-%d%%)",
                    this.getOrginalPrice(),
                    this.getPrice(),
                    this.getDiscount()
            );
        }
        return price;
    }

    public String saleOrSoldOut() {
        String string = "";
        if (this.getDicountProducts() > 0) {
            string = "  **(On Sale! " + this.getDicountProducts() + " units discounted)**";
        }
        if(this.getStock() == 0) {
            string = "  **(Sold out!)**";
        }
        return string;
    }


}
