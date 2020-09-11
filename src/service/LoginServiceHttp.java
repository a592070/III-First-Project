package service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import utils.HttpUtil;
import utils.StringUtil;

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
        String result = HttpUtil.getInfo4(sUrl, HttpUtil.username, userName, HttpUtil.password, password, HttpUtil.reqType, reqType1);
        if(StringUtil.isEmpty(result)) return false;

        JSONObject jsonObject = JSON.parseObject(result);
        String isSuccess = jsonObject.getString("isSuccess");

        return Objects.equals(isSuccess, "true");
    }

    @Override
    public boolean isRegistered() throws IOException {
        String result = HttpUtil.getInfo4(sUrl, HttpUtil.username, userName, HttpUtil.password, password, HttpUtil.reqType, reqType2);
        if(StringUtil.isEmpty(result)) return false;

        JSONObject jsonObject = JSON.parseObject(result);
        String isSuccess = jsonObject.getString("isSuccess");
        return Objects.equals(isSuccess, "true");
    }

    @Override
    public boolean register() throws IOException, SQLException {
        String result = HttpUtil.getInfo4(sUrl, HttpUtil.username, userName, HttpUtil.password, password, HttpUtil.reqType, reqType3);
        if(StringUtil.isEmpty(result)) return false;

        JSONObject jsonObject = JSON.parseObject(result);
        String isSuccess = jsonObject.getString("isSuccess");
        return Objects.equals(isSuccess, "true");
    }

}
