/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.data_access;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import ohtu.domain.User;

/**
 *
 * @author juhapekm
 */
public class FileUserDAO implements UserDao {
    private String fileName;

    public FileUserDAO(String fileName) {
        this.fileName = fileName;
    }
    
    
    
    @Override
    public List<User> listAll() {
        return readAll();
    }

    @Override
    public User findByName(String name) {
        List<User> all = readAll();
        for (User userInFile : all) {
            if (userInFile.getUsername().equals(name)) {
                return userInFile;
            }
        }
        return null;
    }

    @Override
    public void add(User user) {
        List<User> all = readAll();
        all.add(user);
        String allAsString = "";
        for (User oneUser : all) {
            allAsString += oneUser.getUsername() + " " + oneUser.getPassword();
        }
        writeToFile(allAsString);
    }
    
    private List<User> readAll() {
        File read = new File(this.fileName);
        List<User> userlist = new ArrayList();
        try {
            Scanner lukija = new Scanner(read);
            while (lukija.hasNextLine()) {
                String rivi = lukija.nextLine();
                String[] nameAndPass = rivi.split(" ");
                userlist.add(new User(nameAndPass[0], nameAndPass[1]));
            }
            lukija.close();
        } catch (FileNotFoundException ex) {
            
//Logger.getLogger(FileUserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return userlist;
    }
    
    private void writeToFile(String userInfo){
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(this.fileName);
            fileWriter.write(userInfo);
            fileWriter.close();
        } catch (IOException ex) {
            //Logger.getLogger(FileUserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
