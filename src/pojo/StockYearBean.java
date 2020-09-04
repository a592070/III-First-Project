package pojo;

import java.math.BigDecimal;
import java.sql.Date;

public class StockYearBean {
    private int id;
    private String name;
    private int year;
    private BigDecimal tradeVolume; // 成交股數
    private BigDecimal tradeValue;  // 成交金額
    private BigDecimal transAction; // 成交筆數
    private BigDecimal highestPrice;    // 最高價
    private Date highestDate;           // 最高價日期
    private BigDecimal lowestPrice;     // 最低價
    private Date LowestDate;            // 最低價日期
    private BigDecimal AverageClosingPrice; //平均收盤價

    public StockYearBean(int id, String name, int year, BigDecimal tradeVolume, BigDecimal tradeValue, BigDecimal transAction, BigDecimal highestPrice, Date highestDate, BigDecimal lowestPrice, Date lowestDate, BigDecimal averageClosingPrice) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.tradeVolume = tradeVolume;
        this.tradeValue = tradeValue;
        this.transAction = transAction;
        this.highestPrice = highestPrice;
        this.highestDate = highestDate;
        this.lowestPrice = lowestPrice;
        LowestDate = lowestDate;
        AverageClosingPrice = averageClosingPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public BigDecimal getTradeVolume() {
        return tradeVolume;
    }

    public void setTradeVolume(BigDecimal tradeVolume) {
        this.tradeVolume = tradeVolume;
    }

    public BigDecimal getTradeValue() {
        return tradeValue;
    }

    public void setTradeValue(BigDecimal tradeValue) {
        this.tradeValue = tradeValue;
    }

    public BigDecimal getTransAction() {
        return transAction;
    }

    public void setTransAction(BigDecimal transAction) {
        this.transAction = transAction;
    }

    public BigDecimal getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(BigDecimal highestPrice) {
        this.highestPrice = highestPrice;
    }

    public Date getHighestDate() {
        return highestDate;
    }

    public void setHighestDate(Date highestDate) {
        this.highestDate = highestDate;
    }

    public BigDecimal getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(BigDecimal lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public Date getLowestDate() {
        return LowestDate;
    }

    public void setLowestDate(Date lowestDate) {
        LowestDate = lowestDate;
    }

    public BigDecimal getAverageClosingPrice() {
        return AverageClosingPrice;
    }

    public void setAverageClosingPrice(BigDecimal averageClosingPrice) {
        AverageClosingPrice = averageClosingPrice;
    }
}
