import dao.DataBaseDAO;
import dao.InitTable.CreateAccount;
import dao.InitTable.CreateAllStockTable;
import dao.InitTable.CreateStockDays;
import dao.InitTable.CreateTable;
import dao.query.StockQuery;
import dao.query.UserQuery;
import pojo.StockDayDO;
import pojo.UserDO;
import service.StockService;

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

        StockService stockService = new StockService("2330");

        List<StockDayDO> list = stockService.getList();
//        System.out.println(list);
//        List<StockDayDO> list1 = stockService.getStockByDate("2020-09-01");
//        System.out.println(list1);

        stockService.updateData();


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
