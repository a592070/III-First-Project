package service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import utils.HttpUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class LoginServiceHttp extends LoginService{
    private String userName;
    private String password;
    private static final String sUrl = "http://localhost:8080/UserLogin";
    private String reqType1 = "login";
    private String reqType2 = "isRegister";
    private String reqType3 = "register";


    public LoginServiceHttp(String userName, String password) throws IOException, SQLException {
        this.userName = userName;
        this.password = password;
    }

    public LoginServiceHttp() throws IOException, SQLException {
        this("guest", "guest");
    }

    @Override
    public boolean login() throws IOException {
        String login = HttpUtil.getInfo4(sUrl, HttpUtil.username, userName, HttpUtil.password, password, HttpUtil.reqType, reqType1);
        JSONObject jsonObject = JSON.parseObject(login);
        String isSuccess = jsonObject.getString("isSuccess");

        return Objects.equals(isSuccess, "true");
    }

    @Override
    public boolean isRegistered() throws IOException {
        String login = HttpUtil.getInfo4(sUrl, HttpUtil.username, userName, HttpUtil.password, password, HttpUtil.reqType, reqType2);
        JSONObject jsonObject = JSON.parseObject(login);
        String isSuccess = jsonObject.getString("isSuccess");
        return super.isRegistered();
    }

    @Override
    public boolean register() throws IOException, SQLException {
        String login = HttpUtil.getInfo4(sUrl, HttpUtil.username, userName, HttpUtil.password, password, HttpUtil.reqType, reqType3);
        JSONObject jsonObject = JSON.parseObject(login);
        String isSuccess = jsonObject.getString("isSuccess");
        return super.isRegistered();
    }

    @Override
    public boolean register(boolean isAdmin) throws IOException, SQLException {
        return super.register(isAdmin);
    }
}
