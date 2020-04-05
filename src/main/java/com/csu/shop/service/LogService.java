package com.csu.shop.service;

import com.csu.shop.persistence.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    @Autowired
    private LogMapper logMapper;

    public void log(String username, String action) {
        logMapper.insertLog(username, action);
    }
}
