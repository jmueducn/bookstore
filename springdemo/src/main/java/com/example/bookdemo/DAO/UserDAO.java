package com.example.bookdemo.DAO;

import com.example.bookdemo.entity.User;
import com.example.bookdemo.entity.UserAuth;

import java.util.List;

public interface UserDAO {
    User findByUsername(String username);
    UserAuth findByUserId(Long userid);
    User findByEmail(String email);
    User save_user(User user);
    UserAuth save_userAuth(UserAuth userAuth);
    List<User> getAllUsers();
}
