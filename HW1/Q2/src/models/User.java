package models;

public class User extends Account{
    private String firstName;
    private String lastName;


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
}
