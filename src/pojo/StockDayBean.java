package pojo;

import java.math.BigDecimal;
import java.sql.Date;

public class StockDayBean {
    private String name;
    private int id;
    private Date date;
    private BigDecimal tradeVolume;
    private BigDecimal transAction;
    private BigDecimal highestPrice;
    private BigDecimal lowestPrice;
    private BigDecimal openingPrice;
    private BigDecimal closingPrice;

    public StockDayBean() {
    }

    public StockDayBean(String name, int id, Date date, BigDecimal tradeVolume, BigDecimal transAction, BigDecimal highestPrice, BigDecimal lowestPrice, BigDecimal openingPrice, BigDecimal closingPrice) {
        this.name = name;
        this.id = id;
        this.date = date;
        this.tradeVolume = tradeVolume;
        this.transAction = transAction;
        this.highestPrice = highestPrice;
        this.lowestPrice = lowestPrice;
        this.openingPrice = openingPrice;
        this.closingPrice = closingPrice;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getTradeVolume() {
        return tradeVolume;
    }

    public void setTradeVolume(BigDecimal tradeVolume) {
        this.tradeVolume = tradeVolume;
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

    public BigDecimal getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(BigDecimal openingPrice) {
        this.openingPrice = openingPrice;
    }

    public BigDecimal getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(BigDecimal closingPrice) {
        this.closingPrice = closingPrice;
    }

    @Override
    public String toString() {
        return "StockDayBean{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", date=" + date +
                ", tradeVolume=" + tradeVolume +
                ", transAction=" + transAction +
                ", highestPrice=" + highestPrice +
                ", lowestPrice=" + lowestPrice +
                ", openingPrice=" + openingPrice +
                ", closingPrice=" + closingPrice +
                '}';
    }
}
