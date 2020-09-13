package service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import pojo.UserDO;
import utils.HttpUtil;
import utils.StringUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class LoginServiceHttp extends LoginServiceSuper{

    private static String sUrl;
    private String reqType1 = "login";
    private String reqType2 = "isRegister";
    private String reqType3 = "register";



    public LoginServiceHttp(String userName, String password) throws IOException, SQLException {
        if(StringUtil.isEmpty(sUrl)) init();
        super.userName = userName;
        super.password = password;
    }

    public LoginServiceHttp() throws IOException, SQLException {
        this("guest", "guest");
    }
    public void init() throws IOException {
        Properties prop = new Properties();
        Reader reader = new FileReader("resources/ServerConnInfo.properties");
        prop.load(reader);
        sUrl = prop.getProperty("UserLoginSYS");
        reader.close();
    }

    @Override
    public boolean login() throws IOException {
        String result = HttpUtil.getInfo4(sUrl, HttpUtil.username, userName, HttpUtil.password, password, HttpUtil.reqType, reqType1);
        if(StringUtil.isEmpty(result)) return false;

        JSONObject jsonObject = JSON.parseObject(result);
        String isSuccess = jsonObject.getString("isSuccess");
        boolean isLogin = Objects.equals(isSuccess, "true");
        user = new UserDO();
        if(isLogin){
            JSONArray data = jsonObject.getJSONArray("data");

            // userName, password, favorite1, favorite2, favorite3, favorite4, favorite5, register, lastDate, isAdmin
            for (int i = 0; i < data.size(); i++) {
                user.setUserName(data.getString(0));
                user.setPassword(data.getString(1));
                String string = data.getString(2);
                if(!StringUtil.isEmpty(string)) user.setFavorite1(new BigDecimal(string));
                string = data.getString(3);
                if(!StringUtil.isEmpty(string)) user.setFavorite2(new BigDecimal(string));
                string = data.getString(4);
                if(!StringUtil.isEmpty(string)) user.setFavorite3(new BigDecimal(string));
                string = data.getString(5);
                if(!StringUtil.isEmpty(string)) user.setFavorite4(new BigDecimal(string));
                string = data.getString(6);
                if(!StringUtil.isEmpty(string)) user.setFavorite5(new BigDecimal(string));

                string = data.getString(7);
                if(!StringUtil.isEmpty(string)) user.setRegister(Date.valueOf(string));

                string = data.getString(8);
                if(!StringUtil.isEmpty(string)) user.setLastDate(Date.valueOf(string));

                user.setAdmin(Objects.equals(data.getString(9), "true"));
            }
        }
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
