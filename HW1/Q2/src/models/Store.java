package models;

public class Store extends Account{
    private String brand;


    public Store(String brand, String password, String email) {
        super(email, password);
        this.brand = brand;
    }
}
