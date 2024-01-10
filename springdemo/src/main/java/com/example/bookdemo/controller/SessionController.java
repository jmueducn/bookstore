package com.example.bookdemo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SessionController {

    @GetMapping("/set-session")
    public String setSession(HttpSession session) {
        // 在会话中设置一个属性
        session.setAttribute("username", "exampleUser");
        return "Session attribute set!";
    }

    @GetMapping("/get-session")
    public String getSession(HttpSession session) {
        // 从会话中获取属性
        String username = (String) session.getAttribute("username");
        return "Session attribute: " + username;
    }

    @GetMapping("/invalidate-session")
    public String invalidateSession(HttpSession session) {
        // 使会话失效
        session.invalidate();
        return "Session invalidated!";
    }
}
