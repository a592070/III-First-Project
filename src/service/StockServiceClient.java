package service;

import dao.InitTable.CreateStockDays;
import dao.StockDAOImpl;
import dao.insert.StockInsert;
import dao.query.StockQuery;
import pojo.StockDayDO;
import pojo.StockTotalNoDO;
import utils.StringUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class StockServiceClient extends StockServiceSuper{

    public StockServiceClient(String sStockNo) throws IOException, SQLException {
        stock = new StockDayDO();
        if(!StringUtil.isEmpty(sStockNo)){
            this.sStockNo = sStockNo;
            stock.setStockNo(new BigDecimal(sStockNo));
        }
        if(list == null) getList();
    }
    public StockServiceClient(StockDayDO stock) throws IOException, SQLException {
        this.stock = stock;
        if(list == null) getList();
    }

    public StockServiceClient() {
    }

    @Override
    public List<StockTotalNoDO> approximateSearch(String description) throws IOException, SQLException {
        if(StringUtil.isEmpty(description)) return null;
        getAllStockNoList();
        List<StockTotalNoDO> tempList = new ArrayList<>();

        for (StockTotalNoDO ele : listAll) {
            String no = ele.getStockNo().toString();
            String name = ele.getName();
            if (no.contains(description) || description.contains(no)) {
                tempList.add(ele);
            }else if(name.contains(description) || description.contains(name)){
                tempList.add(ele);
            }
        }
        return tempList;
    }

//    @Override
//    public List<StockDayDO> getList() throws IOException, SQLException {
//        if(list == null) list = new StockDAOImpl().getLists();
//        return list;
//    }

    @Override
    public List<StockDayDO> updateList() throws IOException, SQLException {
        list = new StockDAOImpl().getLists();
        return list;
    }

    @Override
    public Map<String, List<StockTotalNoDO>> getAllStockNo() throws IOException, SQLException {
        if(map==null) map = new StockQuery(null).getAllStockNo();
        return map;
    }
    @Override
    public List<StockTotalNoDO> getAllStockNoList() throws IOException, SQLException {
        if(listAll == null){
            getAllStockNo();
            listAll = new ArrayList<>();
            map.values().forEach(ele->listAll.addAll(ele));
        }
        return listAll;
    }

//    public boolean updateData() throws SQLException, NoSuchAlgorithmException, KeyManagementException, IOException {
//        return updateData(null);
//    }

    @Override
    // yyyy-MM-dd to yyyyMMdd
    public boolean updateData(String sDate) throws IOException, NoSuchAlgorithmException, SQLException, KeyManagementException {
        if(StringUtil.isEmpty(sDate)){
            sDate = LocalDate.now().toString();
        }
        sDate = sDate.replace("-", "");


        boolean b = new StockInsert(stock).insert(sDate);
        updateList();
        return b;
    }

    public void initTable() throws IOException, NoSuchAlgorithmException, SQLException, KeyManagementException {
        new CreateStockDays().init();
        updateList();
    }

    public int delete(String sDate) throws IOException, SQLException {
        return delete(sDate, sDate);
    }
    public int delete(String begin, String end) throws IOException, SQLException {
        LocalDate beginDate = Date.valueOf(begin).toLocalDate();
//        LocalDate beginDate1 = beginDate;
        LocalDate endDate = Date.valueOf(end).toLocalDate();

        List<StockDayDO> removeList = new ArrayList<>();

        int count = 0;
        while(beginDate.compareTo(endDate) <= 0){
            if(new StockDAOImpl(stock).remove(beginDate)){
                ++count;
            }
            beginDate = beginDate.plusDays(1);
        }

        updateList();
        return count;
    }

}
