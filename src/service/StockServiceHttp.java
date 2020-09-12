package service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import pojo.StockDayDO;
import pojo.StockTotalNoDO;
import utils.HttpUtil;
import utils.StringUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class StockServiceHttp extends StockServiceSuper {
    private static String sUrlAllStockData;
    private static String sUrlAllStockInfo;
    private static String sUrlUpdateStock;

//    private static List<StockDayDO> list;
//    private static List<StockTotalNoDO> listAll;
//    private static Map<String, List<StockTotalNoDO>> map;


    public StockServiceHttp(String sStockNo) throws IOException, SQLException {
        if(StringUtil.isEmpty(sUrlAllStockData) || StringUtil.isEmpty(sUrlAllStockInfo) || StringUtil.isEmpty(sUrlUpdateStock)) init();
        if(list == null) getList();
        super.stock = new StockDayDO();
        if(!StringUtil.isEmpty(sStockNo)){
            super.sStockNo = sStockNo;
            super.stock.setStockNo(new BigDecimal(sStockNo));

            list.forEach(ele -> {
                if(ele.getStockNo().compareTo(new BigDecimal(sStockNo)) == 0){
                    super.stock.setName(ele.getName());
                }
            });
        }
    }
    public void init() throws IOException {
        Properties prop = new Properties();
        Reader reader = new FileReader("resources/ServerConnInfo.properties");
        prop.load(reader);
        sUrlAllStockData = prop.getProperty("AllStockData");
        sUrlAllStockInfo = prop.getProperty("AllStockInfo");
        sUrlUpdateStock = prop.getProperty("InsertData");
        reader.close();
    }

    public StockServiceHttp() throws IOException {
        if(StringUtil.isEmpty(sUrlAllStockData) || StringUtil.isEmpty(sUrlAllStockInfo) || StringUtil.isEmpty(sUrlUpdateStock)) init();
    }

    @Override
    public Map<String, List<StockTotalNoDO>> getAllStockNo() throws IOException {
        if(map == null){
            map = new HashMap<>();

            String result = HttpUtil.getInfo2(sUrlAllStockInfo, HttpUtil.reqType, "map");
            if(StringUtil.isEmpty(result)) return map;

            JSONObject jsonObject = JSON.parseObject(result);
            JSONArray fields = jsonObject.getJSONArray("field");
            JSONArray datas = jsonObject.getJSONArray("data");

            for (int i = 0; i < fields.size(); i++) {
                String group = fields.getString(i);

                JSONArray jsonArray = datas.getJSONArray(i);
                List<StockTotalNoDO> lis = new ArrayList<>();

                // stockNo, name, codeISIN, dataListed, group
                for (int j = 0; j < jsonArray.size(); j++) {
                    StockTotalNoDO stockTotalNoDO = new StockTotalNoDO();
                    JSONArray ele = jsonArray.getJSONArray(j);

                    String str = ele.getString(0);
                    if(!StringUtil.isEmpty(str)) stockTotalNoDO.setStockNo(new BigDecimal(str));
                    stockTotalNoDO.setName(ele.getString(1));
                    stockTotalNoDO.setCodeISIN(ele.getString(2));

                    str = ele.getString(3);
                    if(!StringUtil.isEmpty(str)) stockTotalNoDO.setDataListed(Date.valueOf(str));

                    stockTotalNoDO.setGroup(ele.getString(4));

                    lis.add(stockTotalNoDO);
                }
                map.put(group, lis);
            }
        }

        return map;
    }

    @Override
    public List<StockTotalNoDO> getAllStockNoList() throws IOException {
        if(listAll == null){
            listAll = new ArrayList<>();

            String result = HttpUtil.getInfo2(sUrlAllStockInfo, HttpUtil.reqType, "list");
            if(StringUtil.isEmpty(result)) return listAll;

            JSONObject jsonObject = JSON.parseObject(result);
            JSONArray data = jsonObject.getJSONArray("data");

            // stockNo, name, codeISIN, dataListed, group
            for (int i = 0; i < data.size(); i++) {
                JSONArray obj = data.getJSONArray(i);

                StockTotalNoDO stockTotalNoDO = new StockTotalNoDO();

                String str = obj.getString(0);
                if(!StringUtil.isEmpty(str)) stockTotalNoDO.setStockNo(new BigDecimal(str));
                stockTotalNoDO.setName(obj.getString(1));
                stockTotalNoDO.setCodeISIN(obj.getString(2));

                str = obj.getString(3);
                if(!StringUtil.isEmpty(str)) stockTotalNoDO.setDataListed(Date.valueOf(str));

                stockTotalNoDO.setGroup(obj.getString(4));

                listAll.add(stockTotalNoDO);
            }
        }
        return listAll;
    }

    @Override
    public List<StockDayDO> updateList() throws IOException {
        list = new ArrayList<>();

        String result = HttpUtil.getInfo1(sUrlAllStockData);
        if(StringUtil.isEmpty(result)) return list;

        JSONObject jsonObject = JSON.parseObject(result);
        JSONArray data = jsonObject.getJSONArray("data");
        List<String> stringList = data.toJavaList(String.class);

        // name, stockNo, date, tradeVolume, transAction, highestPrice, lowestPrice, openingPrice, closingPrice
        for (String sStockDo : stringList) {
            JSONArray array = JSON.parseArray(sStockDo);
            StockDayDO stockDayDO = new StockDayDO();

            stockDayDO.setName(array.getString(0));

            String str = array.getString(1);
            if(!StringUtil.isEmpty(str)) stockDayDO.setStockNo(new BigDecimal(str));

            str = array.getString(2);
            if(!StringUtil.isEmpty(str)) stockDayDO.setDate(Date.valueOf(str));

            str = array.getString(3);
            if(!StringUtil.isEmpty(str)) stockDayDO.setTradeVolume(new BigDecimal(str));

            str = array.getString(4);
            if(!StringUtil.isEmpty(str)) stockDayDO.setTransAction(new BigDecimal(str));

            str = array.getString(5);
            if(!StringUtil.isEmpty(str)) stockDayDO.setHighestPrice(new BigDecimal(str));

            str = array.getString(6);
            if(!StringUtil.isEmpty(str)) stockDayDO.setLowestPrice(new BigDecimal(str));

            str = array.getString(7);
            if(!StringUtil.isEmpty(str)) stockDayDO.setOpeningPrice(new BigDecimal(str));

            str = array.getString(8);
            if(!StringUtil.isEmpty(str)) stockDayDO.setClosingPrice(new BigDecimal(str));

            list.add(stockDayDO);
        }
        return list;
    }

    @Override
    public boolean updateData(String sDate) throws IOException, NoSuchAlgorithmException, SQLException, KeyManagementException {
        if(StringUtil.isEmpty(sDate)){
            sDate = LocalDate.now().toString();
        }
        sDate = sDate.replace("-", "");

        String result = HttpUtil.getInfo3(sUrlUpdateStock, HttpUtil.stockno, sStockNo, HttpUtil.date, sDate);
        if(StringUtil.isEmpty(result)) return false;

        JSONObject jsonObject = JSON.parseObject(result);
        String isSuccess = jsonObject.getString("isSuccess");

        return Objects.equals(isSuccess, "true");
    }
}
