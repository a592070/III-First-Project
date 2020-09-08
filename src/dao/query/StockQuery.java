package dao.query;

import connections.DBConnectionPool;
import org.apache.commons.dbcp2.BasicDataSource;
import pojo.StockDayDO;

import javax.sql.DataSource;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class StockQuery {
    private DataSource dataSource;
    private Connection conn;
    private String sql;
    private StockDayDO stock;
    private List<StockDayDO> list;

    public StockQuery(StockDayDO stock) throws IOException {
        this.stock = stock;
        dataSource = new DBConnectionPool().getDataSource();
    }
    public List<StockDayDO> query() throws SQLException {
        sql = "select d.stockno, t.name, d.trade_volume, d.transation, d.h_price, d.l_price, d.opening_price, d.closing_price, d.day from stock_days d, stock_total_no t where d.stockno=? and d.stockno=t.stockno";
        conn = dataSource.getConnection();
        PreparedStatement predStmt = conn.prepareStatement(sql);
        predStmt.setBigDecimal(1, stock.getStockNo());
        ResultSet rs = predStmt.executeQuery();

        list = new ArrayList<>();
        while(rs.next()){
            StockDayDO stock = new StockDayDO();
            stock.setStockNo(rs.getBigDecimal("stockno"));
            stock.setName(rs.getString("name"));
            stock.setDate(rs.getDate("day"));
            stock.setTradeVolume(rs.getBigDecimal("trade_volume"));
            stock.setTransAction(rs.getBigDecimal("transation"));
            stock.setHighestPrice(rs.getBigDecimal("h_price"));
            stock.setLowestPrice(rs.getBigDecimal("l_price"));
            stock.setOpeningPrice(rs.getBigDecimal("opening_price"));
            stock.setClosingPrice(rs.getBigDecimal("closing_price"));

            list.add(stock);
        }
        predStmt.clearParameters();
        predStmt.close();
        conn.close();
        return list;
    }
    public StockDayDO queryDate(String date) throws SQLException {
        if(list == null) list = query();
        Iterator<StockDayDO> iterator = list.iterator();
        while(iterator.hasNext()){
            StockDayDO next = iterator.next();
            if(Objects.equals(date, next.getDate())){
                return next;
            }
        }
        return null;
    }
    public boolean isExist() throws SQLException {
        sql = "select stockno from stock_days where stockno=?";
        conn = dataSource.getConnection();
        PreparedStatement predStmt = conn.prepareStatement(sql);
        predStmt.setBigDecimal(1, stock.getStockNo());
        ResultSet rs = predStmt.executeQuery();
        if(rs.next()) return true;
        return false;
    }


}
