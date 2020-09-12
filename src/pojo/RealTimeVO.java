package pojo;

import java.math.BigDecimal;

public class RealTimeVO {
    private String stockNo;
    private String name;
    private String latestTradePrice;
    private String limitUp;
    private String high;
    private String low;
    private String open;
    private String close;
    private String bestAskPrice1;
    private String bestAskPrice2;
    private String bestAskPrice3;
    private String bestAskPrice4;
    private String bestAskPrice5;
    private String bestBidPrice1;
    private String bestBidPrice2;
    private String bestBidPrice3;
    private String bestBidPrice4;
    private String bestBidPrice5;

    public RealTimeVO() {
    }

    public String getStockNo() {
        return stockNo;
    }

    public void setStockNo(String stockNo) {
        this.stockNo = stockNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatestTradePrice() {
        return latestTradePrice;
    }

    public void setLatestTradePrice(String latestTradePrice) {
        this.latestTradePrice = latestTradePrice;
    }

    public String getLimitUp() {
        return limitUp;
    }

    public void setLimitUp(String limitUp) {
        this.limitUp = limitUp;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getBestAskPrice1() {
        return bestAskPrice1;
    }

    public void setBestAskPrice1(String bestAskPrice1) {
        this.bestAskPrice1 = bestAskPrice1;
    }

    public String getBestAskPrice2() {
        return bestAskPrice2;
    }

    public void setBestAskPrice2(String bestAskPrice2) {
        this.bestAskPrice2 = bestAskPrice2;
    }

    public String getBestAskPrice3() {
        return bestAskPrice3;
    }

    public void setBestAskPrice3(String bestAskPrice3) {
        this.bestAskPrice3 = bestAskPrice3;
    }

    public String getBestAskPrice4() {
        return bestAskPrice4;
    }

    public void setBestAskPrice4(String bestAskPrice4) {
        this.bestAskPrice4 = bestAskPrice4;
    }

    public String getBestAskPrice5() {
        return bestAskPrice5;
    }

    public void setBestAskPrice5(String bestAskPrice5) {
        this.bestAskPrice5 = bestAskPrice5;
    }

    public String getBestBidPrice1() {
        return bestBidPrice1;
    }

    public void setBestBidPrice1(String bestBidPrice1) {
        this.bestBidPrice1 = bestBidPrice1;
    }

    public String getBestBidPrice2() {
        return bestBidPrice2;
    }

    public void setBestBidPrice2(String bestBidPrice2) {
        this.bestBidPrice2 = bestBidPrice2;
    }

    public String getBestBidPrice3() {
        return bestBidPrice3;
    }

    public void setBestBidPrice3(String bestBidPrice3) {
        this.bestBidPrice3 = bestBidPrice3;
    }

    public String getBestBidPrice4() {
        return bestBidPrice4;
    }

    public void setBestBidPrice4(String bestBidPrice4) {
        this.bestBidPrice4 = bestBidPrice4;
    }

    public String getBestBidPrice5() {
        return bestBidPrice5;
    }

    public void setBestBidPrice5(String bestBidPrice5) {
        this.bestBidPrice5 = bestBidPrice5;
    }

    @Override
    public String toString() {
        return "stockNo='" + stockNo + '\'' +
                ", name='" + name + '\'' +
                ", latestTradePrice='" + latestTradePrice + '\'' +
                ", limitUp='" + limitUp + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", open='" + open + '\'' +
                ", close='" + close + '\'' +
                ", bestAskPrice1='" + bestAskPrice1 + '\'' +
                ", bestAskPrice2='" + bestAskPrice2 + '\'' +
                ", bestAskPrice3='" + bestAskPrice3 + '\'' +
                ", bestAskPrice4='" + bestAskPrice4 + '\'' +
                ", bestAskPrice5='" + bestAskPrice5 + '\'' +
                ", bestBidPrice1='" + bestBidPrice1 + '\'' +
                ", bestBidPrice2='" + bestBidPrice2 + '\'' +
                ", bestBidPrice3='" + bestBidPrice3 + '\'' +
                ", bestBidPrice4='" + bestBidPrice4 + '\'' +
                ", bestBidPrice5='" + bestBidPrice5 + '\'';
    }
}
