package models;
/*
Explanation:
- when we create an expense, we need to store some information about it.
- Expense is something that we need to make it an object.
- put those information here and use them in your code.
 */

import models.enums.Currency;

public class Expense{
    private final User payer;
    private final User payee;
    private Long value;

    public Expense(User payer, User payee, long value) {
        this.payer = payer;
        this.payee = payee;
        this.value = value;
    }

    public User getPayer() {
        return payer;
    }

    public User getPayee() {
        return payee;
    }

    public long getValue() {
        return value;
    }

    public void addValue(long value) {
        this.value += value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public void addExpense(Integer value) {
        switch (App.getActiveUser().getCurrency()) {
            case GTC -> {
                this.addValue(Currency.GTC.getValue() * value);
            }
            case QTR -> {
                this.addValue(Currency.QTR.getValue() * value);
            }
            case SUD -> {
                this.addValue(Currency.SUD.getValue() * value);
            }
            default -> {

            }
        }
    }
}
