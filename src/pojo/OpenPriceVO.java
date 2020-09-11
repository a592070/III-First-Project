package pojo;

import java.math.BigDecimal;
import java.sql.Date;

public class OpenPriceVO {
    private String stockNo;
    private String name;
    private BigDecimal openPrice;
    private BigDecimal diffOpenPrice;
    private BigDecimal diffOCPrice;
    private Date date;

    public OpenPriceVO(String stockNo, String name, BigDecimal openPrice, BigDecimal diffOpenPrice, BigDecimal diffOCPrice, Date date) {
        this.stockNo = stockNo;
        this.name = name;
        this.openPrice = openPrice;
        this.diffOpenPrice = diffOpenPrice;
        this.date = date;
        this.diffOCPrice = diffOCPrice;
    }

    public OpenPriceVO() {
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

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    public BigDecimal getDiffOpenPrice() {
        return diffOpenPrice;
    }

    public void setDiffOpenPrice(BigDecimal diffOpenPrice) {
        this.diffOpenPrice = diffOpenPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getDiffOCPrice() {
        return diffOCPrice;
    }

    public void setDiffOCPrice(BigDecimal diffOCPrice) {
        this.diffOCPrice = diffOCPrice;
    }
}
