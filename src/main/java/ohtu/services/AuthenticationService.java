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
            if (correctUsernameAndPassword(
                username, password,
                user.getUsername(), user.getPassword())) {
                return true;
            }  
        }
        return false;
    }
    
    public boolean correctUsernameAndPassword(
        String usernameInput, String passwordInput,
        String usernameCorrect, String passwordCorrect) {
        return usernameInput.equals(usernameCorrect) &&
            passwordInput.equals(passwordCorrect);
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
        return usernameInvalid(username) || passwordInvalid(password);
    }
    
    private boolean passwordInvalid(String password) {
        //too short password
        //no digit in password
        return (password.length()<8 || !containsDigit(password));
    }
    
    private boolean usernameInvalid(String username) {
        //too short username
        //username taken
        return usernameTooShort(username) || usernameTaken(username);
    }
    private boolean usernameTooShort(String username) {
        return (username.length()<3);
    }
    
    private boolean usernameTaken(String username) {
        for (User user : userDao.listAll()) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean containsDigit(String s) {
        if (isNullOrEmpty(s)) {
            return false;
        }   
        return containsDigit(s.toCharArray());
    }
    private boolean containsDigit(char[] ca) {
        for (char c : ca) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    } 
}
