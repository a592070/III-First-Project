package service;

import dao.InitTable.CreateStockDays;
import dao.StockDAOImpl;
import dao.insert.StockInsert;
import dao.update.StockUpdate;
import pojo.StockDayDO;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class StockService {
    private StockDayDO stock;
    private List<StockDayDO> list;
    private String sStockNo;

    public StockService(String sStockNo) throws IOException, SQLException {
        this.sStockNo = sStockNo;
        stock = new StockDayDO();
        stock.setStockNo(new BigDecimal(sStockNo));
        list = new StockDAOImpl(stock).getLists();
    }

    public List<StockDayDO> getList() {
        return list;
    }
    // yyyy-MM-dd
    public List<StockDayDO> getStockByDate(String date){
        return getStockByDate(date, null);
    }
    public List<StockDayDO> getStockByDate(String beginDate, String endDate){
        LocalDate begin = LocalDate.parse(beginDate.subSequence(0, beginDate.length()));

        LocalDate end;
        if(endDate == null){
            end = LocalDate.now();
        }else {
            end = LocalDate.parse(endDate.subSequence(0, endDate.length()));
        }

        List<StockDayDO> pick = new ArrayList<>();
        for (StockDayDO stockDayDO : list) {
            if(begin.compareTo(stockDayDO.getDate().toLocalDate()) <= 0 && end.compareTo(stockDayDO.getDate().toLocalDate()) >= 0){
                pick.add(stockDayDO);
            }
        }
        return pick;
    }

    public boolean updateData() throws SQLException, NoSuchAlgorithmException, KeyManagementException, IOException {
        return updateData(null);
    }
    // yyyyMMdd
    public boolean updateData(String sMonth) throws IOException, NoSuchAlgorithmException, SQLException, KeyManagementException {
        return new StockInsert(stock).insert(sMonth);
    }

    public void initTable() throws IOException, NoSuchAlgorithmException, SQLException, KeyManagementException {
        new CreateStockDays().init();
    }

}
