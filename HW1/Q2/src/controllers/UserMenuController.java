package controllers;

import models.*;
import models.enums.UserMenuCommands;
import views.MainMenu;

import java.util.ArrayList;
import java.util.regex.Matcher;


public class UserMenuController extends MainMenuController {
    public Result listMyOrders() {
        User user =(User) App.getLoggedInAccount();
        if(user.idToOrder.isEmpty())
            return new Result(false, "No orders found.");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("order History\n").append("━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        for (Order order : user.idToOrder.values()) {
            stringBuilder.append(order.toString());
        }
        return new Result(true, stringBuilder.toString());
    }

    public Result showOrderDetails(String orderId) {
        long id = Long.parseLong(orderId);
        User user = (User) App.getLoggedInAccount();
        Order order = user.idToOrder.get(id);
        if(order == null)
            return new Result(false, "Order not found.");
        ArrayList<Product> products = new ArrayList<>(order.productsToQuantity.keySet());
        products.sort((p1, p2) -> Long.compare(p1.getID(), p2.getID()));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Products in this order:\n\n");
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            stringBuilder.append(i + 1).append("-").append(printOrderProduct(
                    product,
                    order.productsToQuantity.get(product),
                    order.productsToPrice.get(product)
            ));
        }
        stringBuilder
                .append("━━━━━━━━━━━━━━━━━━━━━━━━━━\n")
                .append(String.format("**Total Cost: $%.1f**", order.getTotalCost()));
        return new Result(true, stringBuilder.toString());

    }

    public Result editName(String firstName, String lastName, String password) {
        User user = (User) App.getLoggedInAccount();
        if (!password.equals(user.getPassword()))
            return new Result(false, "Incorrect password. Please try again.");
        if (firstName.equals(user.getFirstName()) || lastName.equals(user.getLastName()))
            return new Result(false, "The new name must be different from the current name.");
        if (firstName.length() < 3 || lastName.length() < 3)
            return new Result(false, "Name is too short.");
        if (UserMenuCommands.NAME.getMatcher(firstName) == null || UserMenuCommands.NAME.getMatcher(lastName) == null)
            return new Result(false, "Incorrect name format.");
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return new Result(true, "Name updated successfully.");
    }

    public Result editEmail(String email, String password) {
        User user = (User) App.getLoggedInAccount();
        if (!password.equals(user.getPassword()))
            return new Result(false, "Incorrect password. Please try again.");
        if (email.equals(user.getEmail()))
            return new Result(false, "The new email must be different from the current email.");
        if (UserMenuCommands.EMAIL.getMatcher(email) == null)
            return new Result(false, "Incorrect email format.");
        if (App.users.get(email) != null || App.stores.get(email) != null)
            return new Result(false, "Email already exists.");
        App.users.remove(user.getEmail());
        user.setEmail(email);
        App.users.put(email, user);
        return new Result(true, "Email updated successfully.");
    }

    public Result editPassword(String newPass, String oldPass) {
        User user = (User) App.getLoggedInAccount();
        if (!oldPass.equals(user.getPassword()))
            return new Result(false, "Incorrect password. Please try again.");
        if (oldPass.equals(newPass))
            return new Result(false, "The new password must be different from the old password.");
        if (UserMenuCommands.PASSWORD.getMatcher(newPass) == null)
            return new Result(false, "The new password is weak.");
        user.setPassword(newPass);
        return new Result(true, "Password updated successfully.");
    }

    public Result showMyInfo() {
        User user = (User) App.getLoggedInAccount();
        String message = String.format("Name: %s %s\nEmail: %s", user.getFirstName(), user.getLastName(), user.getEmail());

        return new Result(true, message);
    }

    public Result addAddress(String country, String city, String street, String postal) {
        User user = (User) App.getLoggedInAccount();

        if (UserMenuCommands.POSTAL.getMatcher(postal) == null)
            return new Result(false, "Invalid postal code. It should be a 10-digit number.");
        if (containsAddressPostal(user, postal))
            return new Result(false, "This postal code is already associated with an existing address.");
        Address address = new Address(country, city, street, postal, user.getAndAddAddressId());
        user.addresses.add(address);
        return new Result(true, "Address successfully added with id " + address.getID() + ".");
    }

    public Result deleteAddress(String ID) {
        int index = getAddressIndexById(Long.parseLong(ID));
        if (index == -1) return new Result(false, "No address found.");
        ((User) App.getLoggedInAccount()).addresses.remove(index);
        return new Result(true, "Address with id " + ID + " deleted successfully.");
    }

    public Result listMyAddresses() {
        User user = (User) App.getLoggedInAccount();
        if (user.addresses.isEmpty()) {
            return new Result(false, "No addresses found. Please add an address first.");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Saved Addresses\n").append("━━━━━━━━━━━━━━━━━━━━━━━━━━");
        for (Address address : user.addresses) {
            sb
                    .append("\n\n")
                    .append("Address ").append(address.getID()).append(":\n")
                    .append("Postal Code: ").append(address.getPostal()).append("\n")
                    .append("Country: ").append(address.getCountry()).append("\n")
                    .append("City: ").append(address.getCity()).append("\n")
                    .append("Street: ").append(address.getStreet()).append("\n")
                    .append("\n━━━━━━━━━━━━━━━━━━━━━━━━━━");
        }
        return new Result(true, sb.toString());

    }

    public Result addCreditCard(String cardNumber, String expirationDate, String cvv, String initialValue) {
        User user = (User) App.getLoggedInAccount();
        if(UserMenuCommands.CARD_NUMBER.getMatcher(cardNumber) == null)
            return new Result(false, "The card number must be exactly 16 digits.");
        Matcher matcher = UserMenuCommands.EXPIRATION_DATE.getMatcher(expirationDate);
        int month;
        if(matcher == null || (month = Integer.parseInt(matcher.group("month"))) > 12 || month < 1)
            return new Result(
                    false,
                    "Invalid expiration date. Please enter a valid date in MM/YY format."
            );
        if(UserMenuCommands.CVV.getMatcher(cvv) == null)
            return new Result(false, "The CVV code must be 3 or 4 digits.");
        double value = Double.parseDouble(initialValue);
        if(value < 0) return new Result(false, "The initial value cannot be negative.");
        Date date = new Date(
                Integer.parseInt(expirationDate.substring(0,2)),
                Integer.parseInt(expirationDate.substring(3,5))
        );
        if(containsCardNumber(user, cardNumber))
            return new Result(false, "This card is already saved in your account.");
        CreditCard card = new CreditCard(cardNumber, date, cvv, value, user.getAndAddCardId());
        user.cards.add(card);
        return new Result(true, "Credit card with Id " + card.getID() + " has been added successfully.");
    }

    public Result chargeCreditCard(String amount, String ID) {
        double value = Double.parseDouble(amount);
        if(value <= 0) return new Result(false, "The amount must be greater than zero.");
        int index = getCardIndexById(Long.parseLong(ID));
        if(index < 0) return new Result(false, "No credit card found.");
        CreditCard card = ((User) App.getLoggedInAccount()).cards.get(index);
        card.addValue(value);
        amount = String.format("%.1f", value);
        String newBalance = String.format("%.1f", card.getValue());
        return new Result(
                true,
                "$" + amount + " has been added to the credit card " + card.getID() +
                        ". New balance: $" + newBalance + "."
        );
    }

    public Result checkCreditCardBalance(String ID) {
        int index = getCardIndexById(Long.parseLong(ID));
        if(index < 0) return new Result(false, "No credit card found.");
        double value = ((User) App.getLoggedInAccount()).cards.get(index).getValue();
        return new Result(true, String.format("Current balance : $%.1f", value));
    }

    public Result showProductsInCart() {
        User user = (User) App.getLoggedInAccount();
        if(user.getActiveCart().productsToQuantity.isEmpty())
            return new Result(false, "Your shopping cart is empty.");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Your Shopping Cart:\n").append("------------------------------------");
        ArrayList<Product> products = new ArrayList<>(user.getActiveCart().productsToQuantity.keySet());
        products.sort((p1, p2) -> {
            int res = p1.getName().compareTo(p2.getName());
            if(res == 0)
                return Long.compare(p1.getID(), p2.getID());
            return res;
        });
        for (Product product : products) {
            stringBuilder.append(printCartProduct(
                    product,
                    user.getActiveCart().productsToQuantity.get(product),
                    user.getActiveCart().productsToPrice.get(product)
            ));
        }
        return new Result(true, stringBuilder.toString());
    }

    public Result checkout(String cardID, String addressID) {
        User user = (User) App.getLoggedInAccount();
        ShoppingCart cart = user.getActiveCart();
        if(cart.productsToQuantity.isEmpty())
            return new Result(false, "Your shopping cart is empty.");
        int addressIndex = getAddressIndexById(Long.parseLong(addressID));
        int cardIndex = getCardIndexById(Long.parseLong(cardID));
        if(addressIndex == -1)
            return new Result(false, "The provided address ID is invalid.");
        if(cardIndex == -1)
            return new Result(false, "The provided card ID is invalid.");
        CreditCard card = user.cards.get(cardIndex);
        if(cart.getTotalCost() > card.getValue())
            return new Result(false, "Insufficient balance in the selected card.");
        card.addValue(-cart.getTotalCost());
        cart.setShippingAddress(user.addresses.get(addressIndex));
        long ID = user.checkout();
        return new Result(true, String.format("""
                Order has been placed successfully!
                Order ID: %d
                Total Paid: $%.1f
                Shipping to: %s""",
                ID,
                user.idToOrder.get(ID).getTotalCost(),
                user.idToOrder.get(ID).getShippingAddress().toString()
        ));
    }

    public Result removeFromCart(String productID, String amount) {
        User user = (User) App.getLoggedInAccount();
        ShoppingCart cart = user.getActiveCart();
        long id = Long.parseLong(productID);
        int quantity = Integer.parseInt(amount);
        if(cart.productsToQuantity.isEmpty())
            return new Result(false, "Your shopping cart is empty.");
        Product product = cart.findProductById(id);
        if(product == null)
            return new Result(false, "The product with ID " + productID + " is not in your cart.");
        if(quantity < 1)
            return new Result(false, "Quantity must be a positive number.");
        if(cart.productsToQuantity.get(product) < quantity)
            return new Result(
                    false,
                    "You only have " + cart.productsToQuantity.get(product) +
                            " of \"" + product.getName() +"\" in your cart."
            );
        cart.removeProduct(product, quantity);
        if(cart.productsToQuantity.get(product) == 0) {
            cart.productsToQuantity.remove(product);
            cart.productsToPrice.remove(product);
            cart.productsToDiscount.remove(product);
            return new Result(
                    true,
                    "\"" + product.getName() + "\" has been completely removed from your cart."
            );
        }
        return new Result(
                true,
                "Successfully removed " + quantity + " of \"" + product.getName() + "\" from your cart."
        );
    }

    private int getAddressIndexById(long ID) {
        User user = (User) App.getLoggedInAccount();
        int res = 0;
        for (Address address : user.addresses) {
            if (address.getID() == ID) return res;
            res++;
        }
        return -1;
    }

    private int getCardIndexById(long ID) {
        User user = (User) App.getLoggedInAccount();
        int res = 0;
        for (CreditCard card : user.cards) {
            if(card.getID() == ID) return res;
            res++;
        }
        return -1;
    }

    private boolean containsAddressPostal(User user, String postal) {
        for (Address address : user.addresses) {
            if(address.getPostal().equals(postal)) return true;
        }
        return false;
    }

    private boolean containsCardNumber(User user, String cardNumber) {
        for (CreditCard card : user.cards) {
            if(card.getCardNumber().equals(cardNumber)) return true;
        }
        return false;
    }

    private String printOrderProduct(Product product, int quantity, double price) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(" Product Name: ").append(product.getName()).append("\n")
                .append("    ID: ").append(product.getID()).append("\n")
                .append("    Brand: ").append(product.getSeller().getBrand()).append("\n")
                .append(String.format("    Rating: %.1f/5", product.getRating())).append("\n")
                .append("    Quantity: ").append(quantity).append("\n")
                .append(String.format("    Price: $%.1f", price));
        if(quantity > 1)
            stringBuilder.append(" each");
        stringBuilder.append("\n\n");
        return stringBuilder.toString();
    }

    private String printCartProduct(Product product, int quantity, double price) { // :(
        return String.format("""
                        
                        Product ID  : %d
                        Name        : %s
                        Quantity    : %d
                        Price       : $%.1f (each)
                        Total Price : $%.1f
                        Brand       : %s
                        Rating      : %.1f/5
                        ------------------------------------""",
                product.getID(),
                product.getName(),
                quantity,
                price,
                price * quantity,
                product.getSeller().getBrand(),
                product.getRating()
        );
    }


}
