package org.csu.jpetstore.service;

import org.apache.catalina.User;
import org.csu.jpetstore.domain.UserLog;
import org.csu.jpetstore.persistence.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {
    @Autowired
    LogMapper logMapper;

    public List<UserLog> getLog() {
        return logMapper.getLog();
    }
}
