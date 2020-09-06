package dao.InitTable;

import connections.DBConnectionPool;
import dao.insert.UserInsert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class CreateAccount implements CreateTable{
    private String tableName;
    private String sql;
    private Connection conn;
    private DBConnectionPool connectionPool;


    public CreateAccount(){
        this.tableName = "account".toUpperCase();
        this.sql = "create table "+this.tableName+"(username varchar2(255) not null constraint ACCOUNT_PK primary key, password varchar2(255), isadmin number(1) default 0 not null, favorite1 number(10), favorite2 number(10), favorite3 number(10), favorite4 number(10), favorite5 number(10) , register date, last_update date)";
    }

    @Override
    public boolean init() throws IOException, SQLException {
        boolean t = false;
        try{
            if (isExist(this.tableName)) {
                truncateTable(this.tableName);
            }else {
                createTable();
            }
            new UserInsert().insertAdmin();
            t = true;
        }catch (IOException | SQLException e){
            truncateTable(this.tableName);
            throw e;
        }
        return t;
    }

    @Override
    public void createTable() throws IOException, SQLException {
        connectionPool = new DBConnectionPool();
        conn = connectionPool.getDataSource().getConnection();
        conn.createStatement().execute(sql);

        conn.close();
    }

}
