package com.example.bookdemo.controller;

import com.example.bookdemo.DTO.LoginResponse;
import com.example.bookdemo.DTO.ManageUserResponse;
import com.example.bookdemo.DTO.RegisterRequest;
import com.example.bookdemo.entity.User;
import com.example.bookdemo.DTO.UserCredentials;
import com.example.bookdemo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.bookdemo.service.SessionTimerService;

import javax.net.ssl.HandshakeCompletedEvent;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true") // Allow cross-origin requests from port 3000
@RequestMapping("/api")
@Controller
@Scope("session")

public class UserController { private final UserService userService;
    private final SessionTimerService sessionTimerService;
    @Autowired
    public UserController(UserService userService ,SessionTimerService sessionTimerService) {
        this.userService = userService;
        this.sessionTimerService=sessionTimerService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody UserCredentials userCredentials,HttpSession session ){
        // Initialize and start the session timer
            String username = userCredentials.getUsername();
        String password = userCredentials.getPassword();session.setAttribute("username", username);
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        sessionTimerService.startTimer();
        System.out.println(sessionTimerService.getStartTime());System.out.println( (String) session.getAttribute("username"));
        return userService.login(username, password);}
    @PostMapping("/logout")
    public String logout(HttpSession session ){
        // 在登出时停止计时并获取计时值
        long sessionTime = sessionTimerService.stopTimer();
        System.out.println( this);// 执行其他登出逻辑
      return "Session lasted for " + sessionTime + " milliseconds";
    }

    @PostMapping("/register")
    public LoginResponse registerUser(@RequestBody RegisterRequest registerRequest ) {
        LoginResponse a=new LoginResponse(0,0);
        try {

            a.setUserId(Math.toIntExact(userService.registerUser(registerRequest).getId()));
            a.setRole(1);
            return a;
        } catch (Exception e) {
            return a;
        }
    }
    @GetMapping("/users")
    public List<ManageUserResponse> getAllUsers()

    {
        return userService.getAllUsers();
    }
    @PutMapping("users/{id}/block")
    public ResponseEntity<String> toggleUserBlock(@PathVariable Long id) {
        if (userService.toggleUserBlock(id)) {
            return ResponseEntity.ok("User status updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user status");
        }
    }
}
