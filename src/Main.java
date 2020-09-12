import dao.stockDTO.ImmediateStock;
import pojo.StockDayDO;
import pojo.StockTotalNoDO;
import service.StockServiceClient;
import service.StockServiceHttp;
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

//        StockService stockService = new StockService("3008");

//        List<StockDayDO> list = stockService.getList();
//        System.out.println(list);
//        List<StockDayDO> list1 = stockService.getStockByDate("2020-09-01");
//        System.out.println(list1);

//        stockService.updateData("20200801");
//        list = stockService.getStockByDate("2020-08-01", "2020-09-03");

//        int count = stockService.delete("2020-08-15", "2020-09-10");
//        list.forEach(ele-> System.out.println(ele));
//        System.out.println(count);

//        StockDayDO avg = stockService.showAvg("2020-08-01", "2020-09-03");
//        System.out.println(avg);

//        List<List<BigDecimal>> line = stockService.openingPriceLine("2020-08-01", "2020-09-03");
//        System.out.println(line);

//        new Login().init();
//        Map<String, List<StockTotalNoDO>> map = new StockService("2330").getAllStockNo();

//        map.forEach((k,v)-> System.out.println(k));
//        System.out.println(map.size());
//        System.out.println(map.keySet());

//        String json = new StockDataSource("20200910", "3008").getJson();
//        System.out.println(json);

//        List<UserDO> list = new LoginService().getList();
//        List<StockDayDO> list = StockService.getList();
//        String s = JsonUtil.listDO2Json(list, JsonUtil.ANALYTIC_STACKDO);
//        System.out.println(s);

//        URL url = new URL("http://localhost:8080/UserLogin");
//        HttpURLConnection conn = (HttpURLConnection) new URL("http://192.168.1.163:8080/UserLogin").openConnection();
//        HttpURLConnection conn = (HttpURLConnection) new URL("http://localhost:8080/AllStockInfo").openConnection();
//        HttpURLConnection conn = (HttpURLConnection) new URL("http://localhost:8080/AllStockData").openConnection();

//        conn.setRequestProperty("Content-Type", "application/json");
//
//        conn.setRequestProperty("username", "abcd");
//        conn.setRequestProperty("password", "abcd");
//        conn.setRequestProperty("reqType", "map");
//        conn.setRequestMethod("GET");
//        conn.connect();
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//        String s;
//        while((s=br.readLine()) != null){
//            System.out.println(s);
//        }

//        List<StockDayDO> list = new StockServiceHttp().getList();
//        System.out.println(list);

//        Map<String, List<StockTotalNoDO>> allStockNo = new StockServiceHttp().getAllStockNo();
//        System.out.println(allStockNo);


//        String beginDate = "20200801";
//        LocalDate begin = LocalDate.parse(beginDate.subSequence(0, beginDate.length()), DateTimeFormatter.ofPattern("yyyyMMdd"));
//        System.out.println(begin);
//        List<StockDayDO> list = new StockServiceClient().getList();
//        List<StockDayDO> stockNoList = new ArrayList<>();
//        list.forEach(ele -> {
//            if(ele.getStockNo().toString().equals("3231")){
//                stockNoList.add(ele);
//            }
//        });
//        Collections.sort(stockNoList, new Comparator<StockDayDO>() {
//            @Override
//            public int compare(StockDayDO o1, StockDayDO o2) {
//                return o1.getDate().compareTo(o2.getDate());
//            }
//        });
//        System.out.println(stockNoList);

        new ImmediateStock("2330").getJsonData();
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
