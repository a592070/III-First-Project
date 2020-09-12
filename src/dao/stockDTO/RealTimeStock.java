package dao.stockDTO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonFactoryBuilder;
import pojo.RealTimeVO;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RealTimeStock {
    private HttpURLConnection connection;
    private String surl = "https://mis.twse.com.tw/stock/api/getStockInfo.jsp?ex_ch=";

    private String stockNo;
    private RealTimeVO realTimeVO;

    public RealTimeStock(String stockNo) {
        this.surl = surl+"tse_"+stockNo+".tw";
        this.stockNo = stockNo;
    }

    public RealTimeVO init() throws Exception{
        realTimeVO = new RealTimeVO();

        SslUtils.ignoreSSL();
        URL url = new URL(this.surl);
        connection = (HttpsURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int status = connection.getResponseCode();
        if(status == 200){
            BufferedReader reader;
            String line;
            StringBuffer responseContent = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((line = reader.readLine())!=null){
                responseContent.append(line);
            }
            reader.close();


            realTimeVO = analyticJson2(responseContent.toString());
        }
        return realTimeVO;
    }

    private void analyticJson1(String responseBody){
        JSONObject jsonObject = new JSONObject(Boolean.parseBoolean(responseBody));

        String tmp = jsonObject.getJSONArray("msgArray").get(0).toString();
        /**將"msgArray"中的object抓出來*/
        JSONObject jsonObject1 =new JSONObject(Boolean.parseBoolean(tmp));

        System.out.println(jsonObject.getJSONArray("msgArray").get(0));

        /**"z"的位置就是目前價格 "o"是開盤價格*/
        System.out.println("目前成交價格:\t"+jsonObject1.get("z"));

        Double nowPrice = Double.valueOf(jsonObject1.get("z").toString());
        System.out.println("目前成交價格:\t"+nowPrice);


        Double openPrice = Double.valueOf(jsonObject1.get("o").toString());

        System.out.println("開盤價格:\t"+openPrice);
        System.out.println("當日最高:\t"+jsonObject1.get("h"));
        System.out.println("當日最低:\t"+jsonObject1.get("l"));

    }
    // "c":股票代號, "n":股票名稱, "z":最近成交價, "h":今日最高, "l":今日最低, "o":開盤價, "y":昨收, "u":漲停點, "a":最佳五檔賣出價格, "b":最佳五檔買入價格
    private RealTimeVO analyticJson2(String sJsonData){
        JSONObject jsonObject = JSON.parseObject(sJsonData);
        JSONObject msgArray = jsonObject.getJSONArray("msgArray").getJSONObject(0);

        if(realTimeVO == null) realTimeVO = new RealTimeVO();

        realTimeVO.setStockNo(this.stockNo);
        realTimeVO.setName(msgArray.getString("n"));
        realTimeVO.setLatestTradePrice(msgArray.getString("z"));
        realTimeVO.setLimitUp(msgArray.getString("u"));
        realTimeVO.setHigh(msgArray.getString("h"));
        realTimeVO.setLow(msgArray.getString("l"));
        realTimeVO.setOpen(msgArray.getString("o"));
        realTimeVO.setClose(msgArray.getString("y"));

        String[] splitAsk = msgArray.getString("a").split("_");
        if(splitAsk.length >= 1) realTimeVO.setBestAskPrice1(splitAsk[0]);
        if(splitAsk.length >= 2) realTimeVO.setBestAskPrice2(splitAsk[1]);
        if(splitAsk.length >= 3) realTimeVO.setBestAskPrice3(splitAsk[2]);
        if(splitAsk.length >= 4) realTimeVO.setBestAskPrice4(splitAsk[3]);
        if(splitAsk.length >= 5) realTimeVO.setBestAskPrice5(splitAsk[4]);

        String[] splitBid = msgArray.getString("b").split("_");
        if(splitBid.length >= 1) realTimeVO.setBestBidPrice1(splitBid[0]);
        if(splitBid.length >= 2) realTimeVO.setBestBidPrice2(splitBid[1]);
        if(splitBid.length >= 3) realTimeVO.setBestBidPrice3(splitBid[2]);
        if(splitBid.length >= 4) realTimeVO.setBestBidPrice4(splitBid[3]);
        if(splitBid.length >= 5) realTimeVO.setBestBidPrice5(splitBid[4]);

        return realTimeVO;
    }
}
