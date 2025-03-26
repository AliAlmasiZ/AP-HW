package models;

public class Address {
    private final String country;
    private final String city;
    private final String street;
    private final String postal;
    private final long ID;

    public Address(String country, String city, String street, String postal, long ID) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.postal = postal;
        this.ID = ID;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getPostal() {
        return postal;
    }

    public long getID() {
        return ID;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass().equals(Address.class) && ((Address)obj).getPostal().equals(this.postal);
    }
}
