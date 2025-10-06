package se.kth.webshop.service_bo;

import se.kth.webshop.database_db.UserDAO;

public class UserService {
    private static final UserDAO dao = new UserDAO();

    public static boolean register(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) return false;
        return dao.register(username, password);
    }

    public static boolean validate(String username, String password) {
        if (username == null || password == null) return false;
        return dao.validate(username, password);
    }
}