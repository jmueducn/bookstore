package com.example.bookdemo.DAOimpl;

import com.example.bookdemo.DAO.UserDAO;
import com.example.bookdemo.entity.User;
import com.example.bookdemo.entity.UserAuth;
import com.example.bookdemo.repository.UserAuthRepository;
import com.example.bookdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private UserRepository userRepository;
    private UserAuthRepository userAuthRepository;

    @Autowired
    public UserDAOImpl(UserRepository userRepository, UserAuthRepository userAuthRepository) {
        this.userRepository = userRepository;
        this.userAuthRepository = userAuthRepository;
    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public  User findByEmail(String email){ return userRepository.findByEmail(email);}
    @Override
    public UserAuth findByUserId(Long userId) {
        return userAuthRepository.findByUserId(userId);
    }
    @Override
    public User save_user(User user){return userRepository.save(user);}
    @Override
    public UserAuth save_userAuth(UserAuth userAuth){return userAuthRepository.save(userAuth);}
    @Override
    public List<User> getAllUsers(){return userRepository.findAll();}
}
