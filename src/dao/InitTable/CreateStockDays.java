package dao.InitTable;

import connections.DBConnectionPool;
import dao.insert.StockInsert;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateStockDays implements CreateTable{
    private String sStockNo;
    private String tableName;
    private String[] columnName;
    private String sql;

    public CreateStockDays(String sStockNo) throws IOException {
        this.sStockNo = sStockNo;
        this.tableName = "stock_days".toUpperCase();
    }

    @Override
    public boolean init() throws IOException, SQLException, NoSuchAlgorithmException, KeyManagementException {
        boolean t = false;
        try {
            if (isExist(this.tableName)) {
                truncateTable(this.tableName);
            }else{
                createTable();
            }
            new StockInsert(sStockNo).insert();
            t = true;
        }catch (IOException | SQLException | KeyManagementException | NoSuchAlgorithmException e){
            truncateTable(this.tableName);
            throw e;
        }
        return t;
    }

    @Override
    public void createTable() throws IOException, SQLException {
        sql = "create table "+this.tableName+"(stockno number(10) not null , trade_volume numeric(20), transation numeric(10), h_price numeric(10,2), l_price numeric(10,2), opening_price numeric(10,2), closing_price numeric(10,2), day date)";

        Connection conn = null;
        PreparedStatement predStmt = null;
        boolean isCreated = false;
        try {
            DBConnectionPool connectionPool = new DBConnectionPool();
            conn = connectionPool.getDataSource().getConnection();

            conn.createStatement().execute(sql);

            conn.commit();
        }catch (IOException | SQLException e){
            if(conn != null) conn.rollback();
            throw e;
        }finally {
            if(conn != null) conn.close();
        }
    }
}
