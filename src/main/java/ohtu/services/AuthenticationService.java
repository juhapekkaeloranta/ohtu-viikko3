package ohtu.services;

import ohtu.domain.User;
import java.util.ArrayList;
import java.util.List;
import ohtu.data_access.UserDao;

public class AuthenticationService {

    private UserDao userDao;

    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }

        if (invalid(username, password)) {
            return false;
        }

        userDao.add(new User(username, password));

        return true;
    }
    /**
     * Validity check of username and password
     * when creating a new user to system
     * @param username
     * @param password
     * @return true if any check fails
     */
    private boolean invalid(String username, String password) {
        // validity check of username and password
        //too short password
        if (password.length()<8) {
            return true;
        }
        //no digit in password
        if (!containsDigit(password)) {
            return true;
        }
        //too short username
        if (username.length()<3) {
            return true;
        }
        //username taken
        for (User user : userDao.listAll()) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean containsDigit(String s) {
        boolean containsDigit = false;
        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (containsDigit = Character.isDigit(c)) {
                    break;
                }
            }
        }
        return containsDigit;
    }
}
