package User;

public class User {
    int userId;
    String name;
    String role;
    String password;

    public User(int userId, String name, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public Boolean checkCredentials(String password) {
        return password.equals(this.password);
    }
}
