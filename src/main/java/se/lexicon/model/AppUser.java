package se.lexicon.model;

public class AppUser {
    private int id;
    private String username;
    private String password;

    public AppUser(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public AppUser(String username, String password) {
        this(0, username, password);
    }

    AppUser() {
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
