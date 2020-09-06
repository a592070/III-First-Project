package dao.insert;

import connections.DBConnectionPool;
import dao.stockDTO.StockDataSource;
import pojo.StockDayDO;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StockInsert {
    private String sDate;
    private String sStockNo;
    private String tableName;
    private String sql;

    public StockInsert(String sStockNo) {
        this(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")), sStockNo);
    }

    public StockInsert(String sDate, String sStockNo) {
        this.sDate = sDate;
        this.sStockNo = sStockNo;
        this.tableName = "stock_days".toUpperCase();
        this.sql = "insert into stock_days(stockno, trade_volume, transation, h_price, l_price, opening_price, closing_price, day) values(?, ?, ?, ?, ?, ?, ?, ?)";
    }

    public void insert() throws IOException, SQLException, KeyManagementException, NoSuchAlgorithmException {
        insert(this.sDate, this.sStockNo);
    }
    public void insert(String sDate, String sStockNo) throws IOException, SQLException, NoSuchAlgorithmException, KeyManagementException {
        Connection connection = null;
        PreparedStatement predStmt = null;
        try {
            List<StockDayDO> stockDaysList = new StockDataSource(sDate, sStockNo).getStockDaysList();
            connection = new DBConnectionPool().getDataSource().getConnection();
            predStmt = connection.prepareStatement(sql);
            for (StockDayDO stockDayDO : stockDaysList) {

                predStmt.setBigDecimal(1, stockDayDO.getStockNo());
                predStmt.setBigDecimal(2, stockDayDO.getTradeVolume());
                predStmt.setBigDecimal(3, stockDayDO.getTransAction());
                predStmt.setBigDecimal(4, stockDayDO.getHighestPrice());
                predStmt.setBigDecimal(5, stockDayDO.getLowestPrice());
                predStmt.setBigDecimal(6, stockDayDO.getOpeningPrice());
                predStmt.setBigDecimal(7, stockDayDO.getClosingPrice());
                predStmt.setDate(8, stockDayDO.getDate());

                predStmt.addBatch();
                predStmt.clearParameters();
            }
            predStmt.executeBatch();

            connection.commit();
        }catch (IOException | SQLException | KeyManagementException | NoSuchAlgorithmException e){
            connection.rollback();
            throw e;
        }finally {
            predStmt.clearParameters();
            predStmt.clearBatch();
            predStmt.close();
            connection.close();
        }
    }
}
