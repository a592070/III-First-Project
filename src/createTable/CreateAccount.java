package createTable;

import Connections.DBConnectionPool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class CreateAccount implements CreateTable{
    private String tableName;
    private String[] columnName;
    private String sql;
    private Connection conn;
    private DBConnectionPool connectionPool;

    public CreateAccount(){
        this.sql = "create table account(username varchar2(255) not null constraint ACCOUNT_PK primary key, password varchar2(255), register date)";
        this.tableName = "ACCOUNT";
    }

    @Override
    public void init(String tableName) {
    }

    @Override
    public boolean createTable() throws IOException, SQLException {
        if(isExist(this.tableName)) return false;
        connectionPool = new DBConnectionPool();
        conn = connectionPool.getDataSource().getConnection();
        boolean update = conn.createStatement().execute(this.sql);

        return update;
    }
}
