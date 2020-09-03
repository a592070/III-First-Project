package createTable;

import dbConnection.ConnectionPool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface CreateTable {

    boolean createTable() throws IOException, SQLException;

    default boolean isExist(String tableName) throws IOException, SQLException {
        boolean t = false;
        Connection conn = new ConnectionPool().getDataSource().getConnection();


        String query = "select table_name from user_tables where table_name = ?";
        PreparedStatement predStmt = conn.prepareStatement(query);
        predStmt.setString(1, tableName);
        ResultSet rs = predStmt.executeQuery();
        if (rs.next()) {
            t = true;
        }
        predStmt.clearParameters();
        conn.close();
        return t;
    }
}
