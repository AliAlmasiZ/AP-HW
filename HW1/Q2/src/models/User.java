package models;

import java.util.ArrayList;
import java.util.HashMap;

public class User extends Account{
    private long addressId = 0;
    private long cardId = 0;
    private String firstName;
    private String lastName;
    public final ArrayList<Address> addresses = new ArrayList<>();
    public final ArrayList<CreditCard> cards = new ArrayList<>();


    public User(String firstName, String lastName, String password, String email) {
        super(email, password);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getAndAddAddressId() {
        this.addressId++;
        return this.addressId;
    }

    public long getAndAddCardId() {
        this.cardId++;
        return this.cardId;
    }

}
