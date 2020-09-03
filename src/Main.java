import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonFactoryBuilder;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import createTable.CreateAccount;
import createTable.CreateTable;
import dbConnection.ConnectionPool;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        String json = "{\"stat\":\"OK\",\"date\":\"20200904\",\"title\":\"109年09月 2330 台積電           各日成交資訊\",\"fields\":[\"日期\",\"成交股數\",\"成交金額\",\"開盤價\",\"最高價\",\"最低價\",\"收盤價\",\"漲跌價差\",\"成交筆數\"],\"data\":[[\"109/09/01\",\"50,129,577\",\"21,681,382,412\",\"430.00\",\"435.00\",\"428.00\",\"435.00\",\"+8.50\",\"17,137\"],[\"109/09/02\",\"42,013,407\",\"18,268,716,092\",\"441.00\",\"441.00\",\"430.50\",\"433.00\",\"-2.00\",\"20,242\"],[\"109/09/03\",\"40,683,617\",\"17,768,951,582\",\"439.50\",\"439.50\",\"433.50\",\"436.00\",\"+3.00\",\"17,810\"]],\"notes\":[\"符號說明:+/-/X表示漲/跌/不比價\",\"當日統計資訊含一般、零股、盤後定價、鉅額交易，不含拍賣、標購。\",\"ETF證券代號第六碼為K、M、S、C者，表示該ETF以外幣交易。\"]}";

//        InputStream in = new URL("https://www.twse.com.tw/rsrc/lib/table/zh.json?_=1599150852922").openConnection().getInputStream();

        JSONObject jsonObj = JSON.parseObject(json);
        JSONArray datas = jsonObj.getJSONArray("data");
        JSONArray data1 = datas.getJSONArray(0);
        System.out.println(data1.size());

        CreateTable cr = new CreateAccount();
        boolean b = cr.createTable();
        System.out.println(b);


    }
}
