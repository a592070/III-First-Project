package dao.query;

import connections.DBConnectionPool;
import org.apache.commons.dbcp2.BasicDataSource;
import pojo.StockDayDO;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockQuery {
    private BasicDataSource dataSource;
    private Connection conn;
    private String sql;

    public StockQuery() throws IOException {
        dataSource = new DBConnectionPool().getDataSource();
    }
    public List<StockDayDO> query(int stockNo) throws SQLException {
        sql = "select d.stockno, t.name, d.trade_volume, d.transation, d.h_price, d.l_price, d.opening_price, d.closing_price, d.day from stock_days d, stock_total_no t where d.stockno=? and d.stockno=t.stockno";
        conn = dataSource.getConnection();
        PreparedStatement predStmt = conn.prepareStatement(sql);
        predStmt.setBigDecimal(1, new BigDecimal(stockNo));
        ResultSet rs = predStmt.executeQuery();

        List<StockDayDO> list = new ArrayList<>();
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
}
