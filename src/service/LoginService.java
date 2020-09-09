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
    private List<UserDO> list;

    public LoginService(String userName , String password) throws IOException, SQLException {
        this.userName = userName;
        this.password = password;
        user = new UserDO(this.userName, this.password);
        list = new UserQuery().listUsers();
    }

    public LoginService() throws IOException, SQLException {
        this("guest" , "guest");
    }

    public boolean isRegistered(){
        return list.stream().anyMatch(ele -> ele.getUserName() == this.userName);
    }

    public boolean register() throws IOException, SQLException {
        return register(false);
    }
    public boolean register(boolean isAdmin) throws IOException, SQLException {
        if(isAdmin) user.setAdmin(true);
        return new UserDAOImpl().addElement(user);
    }

    public boolean isAdmin(){
        return user.isAdmin();
    }

    public boolean updateUser(UserDO user) throws IOException, SQLException {
        return new UserDAOImpl().setElement(user);
    }

    public boolean remove() throws IOException, SQLException {
        return new UserDelete(user).delete();
    }
}
