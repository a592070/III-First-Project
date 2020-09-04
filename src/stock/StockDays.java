package stock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import pojo.StockDayBean;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockDays {
    private String sUrl1 = "https://www.twse.com.tw/en/exchangeReport/STOCK_DAY?response=json";
    private String sUrl2 = "&date=";
    private String sUrl3 = "&stockNo=";
    private String sUrl;
    private String sDate;
    private String sStockNo;
    private String tableName = "STOCK_DAYS_";

    public StockDays(String sDate, String sStockNo) {
        this.sDate = sDate;
        this.sStockNo = sStockNo;
        sUrl = sUrl1+sUrl2+sDate+sUrl3+sStockNo;
        tableName+=sStockNo;
    }
    public void dataLoading() throws IOException, SQLException, KeyManagementException, NoSuchAlgorithmException {
//        CreateTable ct = new CreateStockDays();
//        ct.init(this.tableName);
//        ct.createTable();

        String json = getJson();
        List<List<String>> lists = analyticJson(json);
        List<StockDayBean> beans = new ArrayList<>();

        lists.forEach(ele -> beans.add(toBean(ele)));

        beans.forEach(ele-> System.out.println(ele));

    }
    public String getJson() throws NoSuchAlgorithmException, KeyManagementException, IOException {
        SslUtils.ignoreSSL();
        HttpURLConnection conn = (HttpURLConnection) new URL(sUrl).openConnection();
        conn.setRequestMethod("GET");
        if(conn.getResponseCode() == 200){
            BufferedReader bufIn = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();

            String temp;
            while((temp=bufIn.readLine()) != null){
                stringBuffer.append(temp);
            }
            return stringBuffer.toString();
        }else{
            return null;
        }
    }
    public List<List<String>> analyticJson(String data){
        List<String> inList = new ArrayList<>();
        List<List<String>> list = new ArrayList<>();

        JSONObject jsonObject = JSON.parseObject(data);
        JSONArray dataArray = jsonObject.getJSONArray("data");



        for (int i = 0; i < dataArray.size(); i++) {
            JSONArray jsonArray = dataArray.getJSONArray(i);
            list.add(jsonArray.toJavaList(String.class));
        }

        return list;
    }
    public <E> List<E> getFields(String data){
        JSONObject jsonObject = JSON.parseObject("fields");
        JSONArray dataArray = jsonObject.getJSONArray("fields");
        return null;
    }

    /**
     "Date",
     "Trade Volume",
     "Trade Value", //no add
     "Opening Price",
     "Highest Price",
     "Lowest Price",
     "Closing Price",
     "Change",  //no add
     "Transaction"
     * @param list
     * @param <E>
     */
    public <E> StockDayBean toBean(List<E> list){
//        int size = list.size();
        StockDayBean dayBean = new StockDayBean();
        dayBean.setId(Integer.valueOf(this.sStockNo));
        dayBean.setDate(Date.valueOf(String.valueOf(list.get(0)).replace("/", "-")));
        dayBean.setTradeVolume(new BigDecimal(String.valueOf(list.get(1)).replace(",", "")));
        dayBean.setOpeningPrice(new BigDecimal(String.valueOf(list.get(3)).replace(",", "")));
        dayBean.setHighestPrice(new BigDecimal(String.valueOf(list.get(4)).replace(",", "")));
        dayBean.setLowestPrice(new BigDecimal(String.valueOf(list.get(5)).replace(",", "")));
        dayBean.setClosingPrice(new BigDecimal(String.valueOf(list.get(6)).replace(",", "")));
        dayBean.setTransAction(new BigDecimal(String.valueOf(list.get(8)).replace(",", "")));
        return dayBean;
    }
}
