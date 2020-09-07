import dao.InitTable.CreateAccount;
import dao.InitTable.CreateAllStockTable;
import dao.InitTable.CreateStockDays;
import dao.InitTable.CreateTable;
import dao.query.StockQuery;
import dao.query.UserQuery;
import pojo.StockDayDO;
import pojo.UserDO;

import java.io.*;
import java.rmi.server.ExportException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, SQLException, NoSuchAlgorithmException, KeyManagementException {
//        String json = "{\"stat\":\"OK\",\"date\":\"20200904\",\"title\":\"109年09月 2330 台積電           各日成交資訊\",\"fields\":[\"日期\",\"成交股數\",\"成交金額\",\"開盤價\",\"最高價\",\"最低價\",\"收盤價\",\"漲跌價差\",\"成交筆數\"],\"data\":[[\"109/09/01\",\"50,129,577\",\"21,681,382,412\",\"430.00\",\"435.00\",\"428.00\",\"435.00\",\"+8.50\",\"17,137\"],[\"109/09/02\",\"42,013,407\",\"18,268,716,092\",\"441.00\",\"441.00\",\"430.50\",\"433.00\",\"-2.00\",\"20,242\"],[\"109/09/03\",\"40,683,617\",\"17,768,951,582\",\"439.50\",\"439.50\",\"433.50\",\"436.00\",\"+3.00\",\"17,810\"]],\"notes\":[\"符號說明:+/-/X表示漲/跌/不比價\",\"當日統計資訊含一般、零股、盤後定價、鉅額交易，不含拍賣、標購。\",\"ETF證券代號第六碼為K、M、S、C者，表示該ETF以外幣交易。\"]}";

//        InputStream in = new URL("https://www.twse.com.tw/rsrc/lib/table/zh.json?_=1599150852922").openConnection().getInputStream();

//        JSONObject jsonObj = JSON.parseObject(json);
//        JSONArray datas = jsonObj.getJSONArray("data");
//        JSONArray data1 = datas.getJSONArray(0);
//        System.out.println(data1.size());
//


        new CreateAccount().init();
//        System.out.println(a);
        boolean b = new CreateAllStockTable().init();
        System.out.println(b);

        CreateTable cr = new CreateStockDays("3008");
//        cr.dropTable("account".toUpperCase());
        boolean c = cr.init();
        System.out.println(c);



//        UserDO user = new UserQuery().query("admin");
//        System.out.println(user);
//
//        List<StockDayDO> query = new StockQuery().query(3008);
//        System.out.println(query);

//        new StockDays("20200801", "2330").dataLoading();

//        Scanner sc =  new Scanner(System.in);
//
//        System.out.println(
//                "input: "
//        );
//        String line = sc.nextLine();
//        System.out.println(line);
//        System.out.println(sc.nextInt());
//        System.out.println(sc.next());




    }
    static Scanner sc = new Scanner(System.in);
    public static void input(){

        try {
            while (true) {
                int a = sc.nextInt();
                System.out.println(a);
                if(a == 1){}
                if(a == 2){}
                if(a == 000){
                    break;
                }
            }
        }catch (Exception e){
            input();
        }
    }
}
