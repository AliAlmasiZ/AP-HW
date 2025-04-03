package models;

import java.util.ArrayList;
import java.util.HashMap;

public class Store extends Account{
    private String brand;
    private double costs;
    private double revenue;
    public final HashMap<Long, Product> products = new HashMap<>();


    public Store(String brand, String password, String email) {
        super(email, password);
        this.brand = brand;
        costs = 0;
        revenue = 0;
    }

    public double getCosts() {
        return costs;
    }

    public void addCosts(double costs) {
        this.costs += costs;
    }

    public double getRevenue() {
        return revenue;
    }

    public void addRevenue(double revenue) {
        this.revenue += revenue;
    }

    public String getBrand() {
        return this.brand;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this ) return true;
        if(!obj.getClass().equals(this.getClass())) return false;
        Store store = (Store) obj;
        return this.getEmail().equals(store.getEmail());
    }
}
