package se.lexicon.model;

public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private AppUser userCredentials;

    public Person(int id, String firstName, String lastName, String email, AppUser userCredentials) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userCredentials = userCredentials;
    }

    public Person(String firstName, String lastName, String email, AppUser userCredentials) {
        this(0, firstName, lastName, email, userCredentials);
    }

    Person(){}

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AppUser getUserCredentials() {
        return userCredentials;
    }

    public void setUserCredentials(AppUser userCredentials) {
        this.userCredentials = userCredentials;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
