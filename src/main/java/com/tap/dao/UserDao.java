package com.tap.dao;

import java.util.List;
import com.tap.model.User;

public interface UserDao {
    void addUser(User user);
    User getUser(String email, String password);
    User getUser(int userId);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUser(int userId);
    
    
}