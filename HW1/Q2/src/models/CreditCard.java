package models;

public class CreditCard {
    private final String cardNumber;
    private final Date expirationDate;
    private final String cvv;
    private double value;
    private final long ID;

    public CreditCard(String cardNumber, Date expirationDate, String cvv, double value, long ID) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.value = value;
        this.ID = ID;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass().equals(CreditCard.class) && ((CreditCard) obj).getCardNumber().equals(this.cardNumber);
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getID() {
        return ID;
    }

    public void addValue(double value) {
        this.value += value;
    }
}
