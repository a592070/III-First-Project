package service;

import pojo.OpenPriceVO;
import pojo.StockDayDO;
import pojo.StockTotalNoDO;
import utils.StringUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class StockServiceSuper {
    protected static List<StockDayDO> list;
    protected static List<StockTotalNoDO> listAll;
    protected static Map<String, List<StockTotalNoDO>> map;
    protected StockDayDO stock;
    protected String sStockNo;

    public StockServiceSuper(StockDayDO stock) {
        this.stock = stock;
    }

    public StockServiceSuper(String sStockNo) {
        this.sStockNo = sStockNo;
    }

    public StockServiceSuper() {
    }

    public abstract Map<String, List<StockTotalNoDO>> getAllStockNo() throws IOException, SQLException;
    public abstract List<StockTotalNoDO> getAllStockNoList() throws IOException, SQLException;
    public abstract List<StockDayDO> updateList() throws IOException, SQLException;
    public abstract boolean updateData(String sDate) throws IOException, NoSuchAlgorithmException, SQLException, KeyManagementException;

    public boolean updateData() throws SQLException, NoSuchAlgorithmException, KeyManagementException, IOException {
        return updateData(null);
    }

    public List<StockDayDO> getList() throws IOException, SQLException {
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

    public StockDayDO showAvg(String sBegin) throws NoSuchAlgorithmException, SQLException, KeyManagementException, IOException {
        return showAvg(sBegin, null);
    }


    // yyyy-MM-dd
    // return this month data
    public List<StockDayDO> getStockByDate(String date) throws SQLException, NoSuchAlgorithmException, IOException, KeyManagementException {
        return getStockByDate(date, LocalDate.now().toString());
    }
    // return interval date
    public List<StockDayDO> getStockByDate(String beginDate, String endDate) throws SQLException, NoSuchAlgorithmException, KeyManagementException, IOException {

        updateData(beginDate);

        LocalDate begin = LocalDate.parse(beginDate.subSequence(0, beginDate.length()));

        LocalDate end;
        if(endDate == null){
            end = LocalDate.now();
        }else {
            end = LocalDate.parse(endDate.subSequence(0, endDate.length()));
        }
        if(end.getMonthValue() != begin.getMonthValue()) updateData(endDate);

        getList();

        List<StockDayDO> pick = new ArrayList<>();
        for (StockDayDO stockDayDO : list) {
            if(begin.compareTo(stockDayDO.getDate().toLocalDate()) <= 0 && end.compareTo(stockDayDO.getDate().toLocalDate()) >= 0){
                pick.add(stockDayDO);
            }
        }
        return pick;
    }

    public StockDayDO showAvg(String sBegin, String sEnd) throws IOException, SQLException, KeyManagementException, NoSuchAlgorithmException {
        List<StockDayDO> interval = getStockByDate(sBegin, sEnd);

        StockDayDO avg = new StockDayDO();

        BigDecimal tradeVolume = BigDecimal.ZERO;
        BigDecimal transAction = BigDecimal.ZERO;
        BigDecimal highestPrice = BigDecimal.ZERO;
        BigDecimal lowestPrice = BigDecimal.ZERO;
        BigDecimal openingPrice = BigDecimal.ZERO;
        BigDecimal closingPrice = BigDecimal.ZERO;


        for (StockDayDO ele : interval) {
            tradeVolume = tradeVolume.add(ele.getTradeVolume());
            transAction = transAction.add(ele.getTransAction());
            highestPrice = highestPrice.add(ele.getHighestPrice());
            lowestPrice = lowestPrice.add(ele.getLowestPrice());
            openingPrice = openingPrice.add(ele.getOpeningPrice());
            closingPrice = closingPrice.add(ele.getClosingPrice());
        }
        BigDecimal length = BigDecimal.valueOf(interval.size());
        avg.setTradeVolume(tradeVolume.divide(length, 2, BigDecimal.ROUND_HALF_UP));
        avg.setTransAction(transAction.divide(length, 2, BigDecimal.ROUND_HALF_UP));
        avg.setHighestPrice(highestPrice.divide(length, 2, BigDecimal.ROUND_HALF_UP));
        avg.setLowestPrice(lowestPrice.divide(length, 2, BigDecimal.ROUND_HALF_UP));
        avg.setOpeningPrice(openingPrice.divide(length, 2, BigDecimal.ROUND_HALF_UP));
        avg.setClosingPrice(closingPrice.divide(length, 2, BigDecimal.ROUND_HALF_UP));

        avg.setStockNo(interval.get(0).getStockNo());
        avg.setName(interval.get(0).getName());

        return avg;
    }

    public List<OpenPriceVO> openingPriceLine(String sBegin, String sEnd) throws SQLException, NoSuchAlgorithmException, IOException, KeyManagementException {
        List<StockDayDO> interval = getStockByDate(sBegin, sEnd);
        List<OpenPriceVO> opLine = new ArrayList<>();

        StockDayDO stockDayDO = interval.get(0);
        OpenPriceVO openPriceVO = new OpenPriceVO();

        openPriceVO.setStockNo(String.valueOf(stockDayDO.getStockNo()));
        openPriceVO.setName(stockDayDO.getName());
        openPriceVO.setDate(stockDayDO.getDate());
        openPriceVO.setOpenPrice(stockDayDO.getOpeningPrice());
        openPriceVO.setDiffOpenPrice(BigDecimal.ZERO);
        openPriceVO.setDiffOCPrice(stockDayDO.getOpeningPrice().subtract(stockDayDO.getClosingPrice()));

        BigDecimal openingPrice = stockDayDO.getOpeningPrice();

        opLine.add(openPriceVO);
        for (int i = 1; i < interval.size(); i++) {
            stockDayDO = interval.get(i);
            BigDecimal next = stockDayDO.getOpeningPrice();
            BigDecimal diff = openingPrice.subtract(next);

            openPriceVO = new OpenPriceVO();

            openPriceVO.setStockNo(String.valueOf(stockDayDO.getStockNo()));
            openPriceVO.setName(stockDayDO.getName());
            openPriceVO.setDate(stockDayDO.getDate());
            openPriceVO.setOpenPrice(next);
            openPriceVO.setDiffOpenPrice(diff);
            openPriceVO.setDiffOCPrice(stockDayDO.getOpeningPrice().subtract(stockDayDO.getClosingPrice()));

            opLine.add(openPriceVO);
            openingPrice = next;
        }
        return opLine;
    }

}
