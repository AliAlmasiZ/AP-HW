package models;

public class Date {
    private final int year;
    private final int month;

    public Date(int year, int month) {
        this.year = year;
        this.month = month;
    }

    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().equals(Date.class)) return false;
        Date date = (Date) obj;
        return date.month == this.month && date.year == this.year;
    }
}
