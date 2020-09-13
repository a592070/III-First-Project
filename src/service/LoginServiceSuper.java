package service;

import pojo.UserDO;

import java.io.IOException;
import java.sql.SQLException;

public abstract class LoginServiceSuper {
    protected String userName;
    protected String password;
    protected UserDO user;

    public LoginServiceSuper(UserDO user) {
        this.user = user;
    }

    public LoginServiceSuper(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public LoginServiceSuper() {
    }

    public UserDO getUser(){
        return user;
    }

    public abstract boolean login() throws IOException;

    public abstract boolean isRegistered() throws IOException;

    public abstract boolean register() throws IOException, SQLException;
}
