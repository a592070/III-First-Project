package pojo;

import java.math.BigDecimal;

public class StockMonthBean {
    private String name;
    private int id;
    private int month;
    private BigDecimal tradeVolume;
    private BigDecimal tradeValue;
    private BigDecimal transAction;
    private BigDecimal highestPrice;
    private BigDecimal lowestPrice;

    public StockMonthBean(String name, int id, int month, BigDecimal tradeVolume, BigDecimal tradeValue, BigDecimal transAction, BigDecimal highestPrice, BigDecimal lowestPrice) {
        this.name = name;
        this.id = id;
        this.month = month;
        this.tradeVolume = tradeVolume;
        this.tradeValue = tradeValue;
        this.transAction = transAction;
        this.highestPrice = highestPrice;
        this.lowestPrice = lowestPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
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

    public BigDecimal getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(BigDecimal lowestPrice) {
        this.lowestPrice = lowestPrice;
    }
}
