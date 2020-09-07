package service.login;

import dao.insert.UserInsert;
import dao.query.UserQuery;
import pojo.UserDO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class LoginService {
    private String userName;
    private String password;
    private UserDO user;
    private Map<String , UserDO> map;

    public LoginService(String userName , String password) throws IOException, SQLException {
        this.userName = userName;
        this.password = password;
        map = new UserQuery().listUsers();
    }

    public LoginService() throws IOException, SQLException {
        this("guest" , "guest");
    }

    public boolean isRegistered(){
        if(map.containsKey(userName)){
            user = map.get(userName);
            return true;
        }
        return false;
    }

    public void register() throws IOException, SQLException {
        new UserInsert().insert(new UserDO(userName, password));
    }

    public boolean isAdmin(){
        return user.isAdmin();
    }

    public void updateUser(UserDO user){

    }
}
