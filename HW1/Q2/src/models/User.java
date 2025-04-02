package models;

import java.util.ArrayList;
import java.util.HashMap;

public class User extends Account{
    private ShoppingCart activeCart;
    public final HashMap<Long, Order> idToOrder = new HashMap<>();
    private long addressId = 0;
    private long cardId = 0;
    private long orderID = 100;
    private String firstName;
    private String lastName;
    public final ArrayList<Address> addresses = new ArrayList<>();
    public final ArrayList<CreditCard> cards = new ArrayList<>();


    public User(String firstName, String lastName, String password, String email) {
        super(email, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.activeCart = new ShoppingCart();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
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

    public ShoppingCart getActiveCart() {
        return activeCart;
    }

    public long checkout() {
        Order order = activeCart;
        orderID++;
        order.setID(orderID);
        idToOrder.put(orderID, order);
        for (Product product : order.productsToQuantity.keySet()) {
            Store store = product.getSeller();
            store.addRevenue(order.productsToQuantity.get(product) * order.productsToPrice.get(product));
            product.addSolds(order.productsToQuantity.get(product));
        }
        activeCart = new ShoppingCart();
        return orderID;
    }


}
