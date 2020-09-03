package createTable;

import dbConnection.ConnectionPool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CreateAccount implements CreateTable{
    private String tableName;
    private String[] columnName;
    private String sql;
    private Connection conn;
    private ConnectionPool connectionPool;

    public CreateAccount(){
        this.sql = "create table account(username varchar2(255) not null constraint ACCOUNT_PK primary key, password varchar2(255), register date)";
        this.tableName = "ACCOUNT";
    }

    public void init(String tableName) {
    }

    @Override
    public boolean createTable() throws IOException, SQLException {
        if(isExist(this.tableName)) return false;
        connectionPool = new ConnectionPool();
        conn = connectionPool.getDataSource().getConnection();
        boolean update = conn.createStatement().execute(this.sql);

        return update;
    }
}
