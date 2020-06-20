package org.csu.jpetstore.domain;

import java.util.Date;

public class UserLog {
    private Date time;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    private String userid;
    private String actions;

    public UserLog() {
    }

    public UserLog(Date timeStamp, String log) {
        this.time = timeStamp;
        this.actions = log;
    }

    public Date getTimeStamp() {
        return time;
    }

    public void setTimeStamp(Date timeStamp) {
        this.time = timeStamp;
    }

    public String getLog() {
        return actions;
    }

    public void setLog(String log) {
        this.actions = log;
    }
}
