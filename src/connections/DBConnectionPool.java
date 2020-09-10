package connections;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DBConnectionPool {
    private static BasicDataSource dataSource;

    private String sConnInfo;
    private String sUser;
    private String sPassword;
    private int sInitialSize;
    private int sMaxTotal;
    private int sMaxIdle;
    private int sMaxWait;
    private int sRemoveAbandonedTimeout;
    private int sAis_batch_num;
    private String sAutoCommit;

    private DBConnectionPool() throws IOException {
    }

    public void init() throws IOException {
        dataSource = new BasicDataSource();
        readProperties();
        setPool();
    }
    public void readProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader("resources/db.properties"));
        sConnInfo = properties.getProperty("ConnInfo");
        sUser = properties.getProperty("User");
        sPassword = properties.getProperty("Password");
        sInitialSize = Integer.parseInt(properties.getProperty("InitialSize"));
        sMaxTotal = Integer.parseInt(properties.getProperty("MaxTotal"));
        sMaxIdle = Integer.parseInt(properties.getProperty("MaxIdle"));
        sMaxWait = Integer.parseInt(properties.getProperty("MaxWait"));
        sRemoveAbandonedTimeout = Integer.parseInt(properties.getProperty("RemoveAbandonedTimeout"));
        sAis_batch_num = Integer.parseInt(properties.getProperty("ais_batch_num"));
        sAutoCommit = properties.getProperty("AutoCommit");
    }
    public void setPool(){
//        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        dataSource.setUrl(sConnInfo);
        dataSource.setUsername(sUser);
        dataSource.setPassword(sPassword);
        dataSource.setInitialSize(sInitialSize);    // 初始連線數量
        dataSource.setMaxTotal(sMaxTotal);          // 最大連線數量
        dataSource.setMaxIdle(sMaxIdle);            // 最大空閒連線數量
        dataSource.setMaxWaitMillis(sMaxWait);      // 最大等待時間
        dataSource.setRemoveAbandonedTimeout(sRemoveAbandonedTimeout);  // 回收時間

        dataSource.setDefaultAutoCommit(false);

    }
    public static BasicDataSource getDataSource() throws IOException {
        if(dataSource == null) {
            new DBConnectionPool().init();
        }
        return dataSource;
    }

}
