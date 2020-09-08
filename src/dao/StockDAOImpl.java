package dao;

import dao.InitTable.CreateStockDays;
import dao.delete.StockDelete;
import dao.insert.StockInsert;
import dao.query.StockQuery;
import dao.update.StockUpdate;
import pojo.StockDayDO;
import pojo.UserDO;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class StockDAOImpl implements DataBaseDAO {
    private StockDayDO stock;
    private List<StockDayDO> list;

    public StockDAOImpl(StockDayDO stock) {
        this.stock = stock;
    }

    @Override
    public List<StockDayDO> getLists() throws IOException, SQLException {
        return new StockQuery(stock).query();
    }

    @Override
    public StockDayDO getElement(String date) throws IOException, SQLException {
        return new StockQuery(stock).queryDate(date);
    }

    @Override
    public boolean isExist() throws IOException, SQLException {
        return new StockQuery(stock).isExist();
    }

    @Override
    public boolean setElement(Object ele) throws IOException, SQLException {
        return setElement();
    }
    public boolean setElement() throws IOException, SQLException {
        return new StockUpdate(stock).update();
    }

    @Override
    public boolean addElement(Object date) throws IOException, SQLException, NoSuchAlgorithmException, KeyManagementException {
        String sDate = String.valueOf(date);
        if (date == null) {
            return new StockInsert(stock).insert();
        } else {
            return new StockInsert(stock).insert(sDate);
        }
    }

    @Override
    public StockDayDO remove(Object date) throws IOException, SQLException {
        String sDate = String.valueOf(date);
        return new StockDelete(stock).delete(Date.valueOf(sDate));
    }

    @Override
    public boolean isEmpty() throws IOException, SQLException {
        return new CreateStockDays().isExist();
    }
}
