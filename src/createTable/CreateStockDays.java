package createTable;

import Connections.DBConnectionPool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateStockDays implements CreateTable{
    private String tableName;
    private String[] columnName;
    private String sql;


    @Override
    public void init(String tableName) {
        this.tableName = tableName;
        this.sql = "create table ?(day date, trade_volume numeric(20), transation numeric(10), h_price numeric(10,2), l_price numeric(10,2), opening_price numeric(10,2), closing_price numeric(10,2))";
    }

    @Override
    public boolean createTable() throws IOException, SQLException {
        if(isExist(this.tableName)) return false;
        DBConnectionPool connectionPool = new DBConnectionPool();
        Connection conn = connectionPool.getDataSource().getConnection();

        PreparedStatement predStmt = conn.prepareStatement(this.sql);
        return predStmt.execute();
    }
}
