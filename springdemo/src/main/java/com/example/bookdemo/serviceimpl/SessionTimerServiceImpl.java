package com.example.bookdemo.serviceimpl;

import com.example.bookdemo.service.SessionTimerService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Scope("session")
public class SessionTimerServiceImpl implements SessionTimerService {

    private  long startTime;

    @Override
    public void startTimer() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public  long stopTimer() {
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
    @Override
    public long getStartTime(){
        return startTime;
    }
}
