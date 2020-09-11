package service;

import dao.UserDAOImpl;
import dao.delete.UserDelete;
import dao.query.UserQuery;
import pojo.UserDO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LoginService {
    private String userName;
    private String password;
    private UserDO user;
    private static List<UserDO> list;

    public LoginService(String userName , String password) throws IOException, SQLException {
        this.userName = userName.toUpperCase();
        this.password = password;
        user = new UserDO(this.userName, this.password);
        if(list == null)getList();
    }


    public LoginService() throws IOException, SQLException {
        this("guest" , "guest");
    }

    public List<UserDO> getList() throws IOException, SQLException {
        list = new UserQuery().listUsers();
        return list;
    }

    public boolean login() throws IOException {
        for (UserDO ele : list) {
            if(userName.equals(ele.getUserName()) && password.equals(ele.getPassword())) return true;
        }
        return false;
    }

    public boolean isRegistered() throws IOException {
        for (UserDO ele : list) {
            if(userName.equals(ele.getUserName())) return true;
        }
        return false;
    }

    public boolean register() throws IOException, SQLException {
        return register(false);
    }
    public boolean register(boolean isAdmin) throws IOException, SQLException {
        if(isAdmin) user.setAdmin(true);
        boolean b = new UserDAOImpl().addElement(user);
        getList();
        return b;
    }

    public boolean isAdmin(){
        return user.isAdmin();
    }

    public boolean updateUser(UserDO user) throws IOException, SQLException {
        boolean b = new UserDAOImpl().setElement(user);
        getList();
        return b;
    }

    public boolean remove() throws IOException, SQLException {
        boolean b = new UserDelete(user).delete();
        getList();
        return b;
    }
}
