package service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.StockDAOImpl;
import org.apache.commons.codec.binary.StringUtils;
import pojo.StockDayDO;
import pojo.StockTotalNoDO;
import utils.HttpUtil;
import utils.StringUtil;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockServiceHttp extends StockServiceInterface{
    private static final String sUrlAllStockData = "http://localhost:8080/AllStockData";
    private static final String sUrlAllStockInfo = "http://localhost:8080/AllStockInfo";

//    private static List<StockDayDO> list;
//    private static List<StockTotalNoDO> listAll;
//    private static Map<String, List<StockTotalNoDO>> map;


    public StockServiceHttp(String sStockNo) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        super.stock = new StockDayDO();
        if(!StringUtil.isEmpty(sStockNo)){
            super.sStockNo = sStockNo;
            super.stock.setStockNo(new BigDecimal(sStockNo));
        }
        if(list == null) getList();
    }

    public StockServiceHttp() {
    }

    @Override
    public Map<String, List<StockTotalNoDO>> getAllStockNo() throws IOException {
        if(map == null){
            map = new HashMap<>();

            String mapJson = HttpUtil.getInfo2(sUrlAllStockInfo, HttpUtil.reqType, "map");
            JSONObject jsonObject = JSON.parseObject(mapJson);
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

                    stockTotalNoDO.setStockNo(ele.getBigDecimal(0));
                    stockTotalNoDO.setName(ele.getString(1));
                    stockTotalNoDO.setCodeISIN(ele.getString(2));
                    stockTotalNoDO.setDataListed(Date.valueOf(ele.getString(3)));
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
        if(listAll != null){
            listAll = new ArrayList<>();

            String listJson = HttpUtil.getInfo2(sUrlAllStockInfo, HttpUtil.reqType, "list");

            JSONObject jsonObject = JSON.parseObject(listJson);
            JSONArray data = jsonObject.getJSONArray("data");

            // stockNo, name, codeISIN, dataListed, group
            for (int i = 0; i < data.size(); i++) {
                JSONArray obj = data.getJSONArray(i);

                StockTotalNoDO stockTotalNoDO = new StockTotalNoDO();

                stockTotalNoDO.setStockNo(obj.getBigDecimal(0));
                stockTotalNoDO.setName(obj.getString(1));
                stockTotalNoDO.setCodeISIN(obj.getString(2));
                stockTotalNoDO.setDataListed(Date.valueOf(obj.getString(3)));
                stockTotalNoDO.setGroup(obj.getString(4));

                listAll.add(stockTotalNoDO);
            }
        }
        return listAll;
    }

    @Override
    public List<StockDayDO> updateList() throws IOException {
        list = new ArrayList<>();

        String listJson = HttpUtil.getInfo1(sUrlAllStockData);
        JSONObject jsonObject = JSON.parseObject(listJson);

        JSONArray data = jsonObject.getJSONArray("data");
        List<String> stringList = data.toJavaList(String.class);

        // name, stockNo, date, tradeVolume, transAction, highestPrice, lowestPrice, openingPrice, closingPrice
        for (String sStockDo : stringList) {
            JSONArray array = JSON.parseArray(sStockDo);
            StockDayDO stockDayDO = new StockDayDO();

            stockDayDO.setName(array.getString(0));
            stockDayDO.setStockNo(array.getBigDecimal(1));
            stockDayDO.setDate(Date.valueOf(array.getString(2)));
            stockDayDO.setTradeVolume(array.getBigDecimal(3));
            stockDayDO.setTransAction(array.getBigDecimal(4));
            stockDayDO.setHighestPrice(array.getBigDecimal(5));
            stockDayDO.setLowestPrice(array.getBigDecimal(6));
            stockDayDO.setOpeningPrice(array.getBigDecimal(7));
            stockDayDO.setClosingPrice(array.getBigDecimal(8));

            list.add(stockDayDO);
        }
        return list;
    }
}
