package dao.insert;

import connections.DBConnectionPool;
import dao.query.UserQuery;
import org.apache.commons.dbcp2.BasicDataSource;
import org.omg.CORBA.PRIVATE_MEMBER;
import pojo.UserDO;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;

public class UserInsert {
    private BasicDataSource dataSource;
    private String sql;
    private int insertCount;
    private PreparedStatement predStmt = null;
    private Connection conn = null;

    public UserInsert() throws IOException {
        dataSource = new DBConnectionPool().getDataSource();
        sql = "insert into account(username, password, isadmin, register, last_update) values(?, ?, 0, ?, ?)";
    }

    public void insertAdmin() throws SQLException, IOException {
        sql = "insert into account(username, password, isadmin, register, last_update) values(?, ?, 1, ?, ?)";

        Properties prop = new Properties();
        prop.load(new FileReader("resources/admin.properties"));
        String admin = prop.getProperty("username").toUpperCase();
        String password = prop.getProperty("password");

        UserDO userAdmin = new UserDO();
        userAdmin.setUserName(admin);
        userAdmin.setPassword(password);
        userAdmin.setAdmin(true);

        insert(userAdmin);
    }
    public void insert(UserDO user) throws SQLException, IOException {
        if (!new UserQuery().isUserExist(user)) {
            try {
                conn = dataSource.getConnection();
                predStmt = conn.prepareStatement(sql);
                predStmt.setString(1, user.getUserName());
                predStmt.setString(2, user.getPassword());
                predStmt.setDate(3, Date.valueOf(LocalDate.now()));
                predStmt.setDate(4, Date.valueOf(LocalDate.now()));

                insertCount = predStmt.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                if(conn != null) conn.rollback();
                throw e;
            } finally {
                if(predStmt != null) {
                    predStmt.clearParameters();
                    predStmt.close();
                }
                if(conn != null) conn.close();
            }
        }
    }
}
