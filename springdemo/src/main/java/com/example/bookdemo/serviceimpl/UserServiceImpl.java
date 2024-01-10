package com.example.bookdemo.serviceimpl;

import com.example.bookdemo.DAO.UserDAO;
import com.example.bookdemo.DTO.LoginResponse;
import com.example.bookdemo.DTO.ManageUserResponse;
import com.example.bookdemo.DTO.RegisterRequest;
import com.example.bookdemo.entity.User;
import com.example.bookdemo.entity.UserAuth;
import com.example.bookdemo.service.UserService;
import com.example.bookdemo.util.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    @Override
    public LoginResponse login(String username, String password) {
        User user = userDAO.findByUsername(username);
        int value;
        if (user == null) {
            System.out.println("Username does not exist: " + username);
            value=0;
        }

        UserAuth userAuth = userDAO.findByUserId(user.getId());
        if (userAuth == null) {
            System.out.println("User authentication information not found for user: " + username);
            value=0;
        }

        if (!password.equals(userAuth.getPassword())) {
            System.out.println("Incorrect password for user: " + username);
            value=0;
        }

        if (userAuth.getRole().equals("admin")) {
            System.out.println("Login successful for admin user: " + username);
            value=2;
        } else if(userAuth.getRole().equals("user")){
            System.out.println("Login successful for user: " + username);
            value=1;
        }
        else value=-1;
        LoginResponse retval= new LoginResponse(0,value);
        System.out.println("val: " + value);
        if(value!=0)
        retval.setUserId(Math.toIntExact(user.getId()));
        return retval;
    }

    @Override
    public User registerUser(RegisterRequest registerRequest) {
        // Check if password and repeat password match
        if (!registerRequest.getPassword().equals(registerRequest.getRepeatPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        if (isUsernameTaken(registerRequest.getUsername())) {
            throw new IllegalArgumentException("Username is taken");
        }
        if (isEmailTaken(registerRequest.getEmail())) {
            throw new IllegalArgumentException("Email is taken");
        }
        if(!EmailValidator.isValidEmail(registerRequest.getEmail())){
            throw new IllegalArgumentException("Email is wrong");
        }
        // Create User entity
        User user = new User(registerRequest.getUsername(), registerRequest.getEmail());

        userDAO.save_user(user);

        // Create UserAuth entity
        UserAuth userAuth = new UserAuth(user, "1", "user", registerRequest.getPassword());

        userDAO.save_userAuth(userAuth);
        return user;
    }
    @Override
    public boolean isUsernameTaken(String username) {
        User existingUser = userDAO.findByUsername(username);
        return existingUser != null;
    }

    @Override
    public boolean isEmailTaken(String email) {
        User existingUser = userDAO.findByEmail(email);
        return existingUser != null;
    }
    @Override
    public List<ManageUserResponse> getAllUsers(){
        List<User> userList = userDAO.getAllUsers();
        List<ManageUserResponse> responseList = new ArrayList<>();

        for (User user : userList) {
            UserAuth userAuth = userDAO.findByUserId(user.getId());
            String role = userAuth != null ? userAuth.getRole() : null;

            ManageUserResponse response = new ManageUserResponse(user.getId(), user.getUsername(), user.getEmail(), role);
            responseList.add(response);
        }

        return responseList;

    }
    @Override
    public boolean toggleUserBlock(Long id){
        Optional<UserAuth> optionalUserAuth = Optional.ofNullable(userDAO.findByUserId(id));
        if (optionalUserAuth.isPresent()) {
            UserAuth userAuth = optionalUserAuth.get();
            if (userAuth.getRole().equals("user")) {
                userAuth.setRole("blocked");
            } else {
                userAuth.setRole("user");
            }
            userDAO.save_userAuth(userAuth);
            return true;
        }
        return false;
    }

}
