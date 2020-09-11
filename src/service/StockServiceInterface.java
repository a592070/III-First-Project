package service;

import pojo.StockDayDO;
import pojo.StockTotalNoDO;
import utils.StringUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class StockServiceInterface {
    protected static List<StockDayDO> list;
    protected static List<StockTotalNoDO> listAll;
    protected static Map<String, List<StockTotalNoDO>> map;
    protected StockDayDO stock;
    protected String sStockNo;

    public StockServiceInterface(StockDayDO stock) {
        this.stock = stock;
    }

    public StockServiceInterface(String sStockNo) {
        this.sStockNo = sStockNo;
    }

    public StockServiceInterface() {
    }

    public abstract Map<String, List<StockTotalNoDO>> getAllStockNo() throws IOException;
    public abstract List<StockTotalNoDO> getAllStockNoList() throws IOException;
    public abstract List<StockDayDO> updateList() throws IOException;

    public List<StockDayDO> getList() throws IOException {
        if(list == null) updateList();
        return list;
    }

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

}
