package models;

public class Address {
    private final String country;
    private final String city;
    private final String street;
    private final String postal;

    public Address(String country, String city, String street, String postal) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.postal = postal;
    }
}
