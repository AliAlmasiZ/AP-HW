package models;

public class Product {
    private static long counts = 0;
    private double rate;
    private double price;
    private long solds;
    private final long ID;

    public Product(double rate, double price) {
        this.rate = rate;
        this.price = price;
        this.solds = 0;
        counts++;
        this.ID = counts;
    }

    @Override
    public String toString() {

    }
}
