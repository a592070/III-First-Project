import dao.stockDTO.StockDataSource;
import pojo.StockTotalNoDO;
import service.StockService;
import view.Login;

import java.io.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, SQLException, NoSuchAlgorithmException, KeyManagementException {

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

        String json = new StockDataSource("20200910", "3008").getJson();
        System.out.println(json);

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
