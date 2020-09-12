package pojo;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OpenLineVO {
    private List<BigDecimal> diffOpenPrices;
    private List<BigDecimal> diffOCPrices;
    private List<Date> dates;

    public OpenLineVO(List<BigDecimal> diffOpenPrices, List<BigDecimal> diffOCPrices, List<Date> dates) {
        this.diffOpenPrices = diffOpenPrices;
        this.diffOCPrices = diffOCPrices;
        this.dates = dates;
    }

    public OpenLineVO() {
        diffOpenPrices = new ArrayList<>();
        diffOCPrices = new ArrayList<>();
        dates = new ArrayList<>();
    }

    public void addDiffOpenPrices(BigDecimal ele){
        diffOpenPrices.add(ele);
    }
    public void addDiffOCPrices(BigDecimal ele){
        diffOCPrices.add(ele);
    }
    public void addDates(Date date){
        dates.add(date);
    }


    public List<BigDecimal> getDiffOpenPrices() {
        return diffOpenPrices;
    }

    public void setDiffOpenPrices(List<BigDecimal> diffOpenPrices) {
        this.diffOpenPrices = diffOpenPrices;
    }

    public List<BigDecimal> getDiffOCPrices() {
        return diffOCPrices;
    }

    public void setDiffOCPrices(List<BigDecimal> diffOCPrices) {
        this.diffOCPrices = diffOCPrices;
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }
}
