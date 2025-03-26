package Services;

import User.User;
import java.util.ArrayList;

public class Service {
    private ArrayList<User> users;
    int currentUserId = 0;
    // Constructor
    public Service() {
        users = new ArrayList<>();
        users.add(new User(currentUserId, "admin", "pass", "admin"));
        currentUserId++;
    }

    // Login method to authenticate users
    public int login(String name, String password) {
        for (User user : users) {
            if (user != null && user.getName().equals(name) && user.checkCredentials(password)) {
                return user.getUserId();
            }
        }
        return -1;
    }

    public String getUserRole(int userId){
        if (userId < 0 || userId >= users.size() || users.get(userId) == null) return "unregistered";
        return users.get(userId).getRole();
    }
}
