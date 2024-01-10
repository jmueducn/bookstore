package com.example.bookdemo.service;

import org.springframework.context.annotation.Scope;


public interface SessionTimerService {
    long startTime = 0;
    void startTimer();
      long stopTimer();

    long getStartTime();
}

