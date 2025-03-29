package controllers;

import models.*;
import models.enums.UserMenuCommands;

import java.util.regex.Matcher;


public class UserMenuController {
    public Result listMyOrders() {

    }

    public Result showOrderDetails(String orderId) {

    }

    public Result editName(String firstName, String lastName, String password) {
        User user = (User) App.getLoggedInAccount();
        if (!password.equals(user.getPassword()))
            return new Result(false, "Incorrect password. Please try again.");
        if (!firstName.equals(user.getFirstName()) || !lastName.equals(user.getLastName()))
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
            return new Result(false, "Email already exists.");
        if (App.users.get(email) != null || App.stores.get(email) != null)
            return new Result(false, "Email already exists.");
        user.setEmail(email);
        return new Result(true, "Email updated successfully.");
    }

    public Result editPassword(String newPass, String oldPass) {
        User user = (User) App.getLoggedInAccount();
        if (oldPass.equals(user.getPassword()))
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
        sb.append("Saved Addresses\n").append("━━━━━━━━━━━━━━━━━━━━━━━━━━  \n");
        for (Address address : user.addresses) {
            sb
                    .append("\n")
                    .append("Address ").append(address.getID()).append(":\n")
                    .append("Postal Code: ").append(address.getPostal()).append("\n")
                    .append("Country: ").append(address.getCountry()).append("\n")
                    .append("City: ").append(address.getCity()).append("\n")
                    .append("Street: ").append(address.getStreet()).append("\n")
                    .append("\n━━━━━━━━━━━━━━━━━━━━━━━━━━  \n");
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
        return new Result(
                true,
                "$" + amount + " has been added to the credit card " + card.getID() +
                        ". New balance: $" + card.getValue() + "."
        );
    }

    public Result checkCreditCardBalance(String ID) {
        int index = getCardIndexById(Long.parseLong(ID));
        if(index < 0) return new Result(false, "No credit card found.");
        double value = ((User) App.getLoggedInAccount()).cards.get(index).getValue();
        return new Result(true, "Current balance : $" + value);
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


}
