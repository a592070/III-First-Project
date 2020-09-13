package service;

import org.joda.time.format.DateTimeFormat;
import pojo.OpenPriceVO;
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
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    public List<StockDayDO> getStockByDate(String beginDate, String endDate) throws SQLException, IOException, NoSuchAlgorithmException, KeyManagementException {

        LocalDate begin = LocalDate.parse(beginDate.subSequence(0, beginDate.length()), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate end;
        if(endDate == null){
            end = LocalDate.now();
        }else {
            end = LocalDate.parse(endDate.subSequence(0, endDate.length()), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return getStockByDate(begin, end);
    }
    public List<StockDayDO> getStockByDate(LocalDate begin, LocalDate end) throws IOException, SQLException, KeyManagementException, NoSuchAlgorithmException {
        List<StockDayDO> stockNoList = new ArrayList<>();
        for (StockDayDO stockDayDO : list) {
            if(stockDayDO.getStockNo().toString().equals(sStockNo)){
                stockNoList.add(stockDayDO);
            }
        }
        Collections.sort(stockNoList, new Comparator<StockDayDO>() {
            @Override
            public int compare(StockDayDO o1, StockDayDO o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        boolean isBeginDateExist = false;
        boolean isEndDateExist = false;
        for (StockDayDO stockDayDO : stockNoList) {
            if(stockDayDO.getDate().toLocalDate().getMonthValue() == begin.getMonthValue()){
                isBeginDateExist = true;
            }
            if(stockDayDO.getDate().toLocalDate().getMonthValue() == end.getMonthValue()){
                isEndDateExist = true;
            }
        }
        if(!isBeginDateExist) updateData(begin.toString());
        if(!isEndDateExist) updateData(end.toString());
//        if(stockNoList.get(0).getDate().toLocalDate().getMonthValue() != begin.getMonthValue()) updateData(begin.toString());
//        if(stockNoList.get(stockNoList.size()-1).getDate().toLocalDate().compareTo(end) < 0) updateData(end.toString());

        updateList();

        stockNoList = new ArrayList<>();
        for (StockDayDO stockDayDO : list) {
            if(stockDayDO.getStockNo().toString().equals(sStockNo)){
                stockNoList.add(stockDayDO);
            }
        }
        Collections.sort(stockNoList, new Comparator<StockDayDO>() {
            @Override
            public int compare(StockDayDO o1, StockDayDO o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        for (int i=0 ; i < stockNoList.size(); i++) {
            if(stockNoList.get(i).getDate().toLocalDate().getMonthValue() == begin.getMonthValue()){
                return stockNoList.subList(i, stockNoList.size());
            }
        }
        return stockNoList;
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

    public List<OpenPriceVO> openingPriceLine(LocalDate begin, LocalDate end) throws NoSuchAlgorithmException, SQLException, KeyManagementException, IOException {
        List<StockDayDO> interval = getStockByDate(begin, end);
        List<OpenPriceVO> opLine = new ArrayList<>();

        StockDayDO stockDayDO = interval.get(0);
        OpenPriceVO openPriceVO = new OpenPriceVO();

        openPriceVO.setStockNo(String.valueOf(stockDayDO.getStockNo()));
        openPriceVO.setName(stockDayDO.getName());
        openPriceVO.setDate(stockDayDO.getDate());
        openPriceVO.setOpenPrice(stockDayDO.getOpeningPrice());
        openPriceVO.setClosePrice(stockDayDO.getClosingPrice());
        openPriceVO.setDiffOpenPrice(BigDecimal.ZERO);
        openPriceVO.setDiffOCPrice(stockDayDO.getOpeningPrice().subtract(stockDayDO.getClosingPrice()));

        BigDecimal openingPrice = stockDayDO.getOpeningPrice();

        opLine.add(openPriceVO);
        for (int i = 1; i < interval.size(); i++) {
            stockDayDO = interval.get(i);
            BigDecimal nextOpen = stockDayDO.getOpeningPrice();
            BigDecimal nextClose = stockDayDO.getClosingPrice();
            BigDecimal diff = nextOpen.subtract(openingPrice);

            openPriceVO = new OpenPriceVO();

            openPriceVO.setStockNo(String.valueOf(stockDayDO.getStockNo()));
            openPriceVO.setName(stockDayDO.getName());
            openPriceVO.setDate(stockDayDO.getDate());
            openPriceVO.setOpenPrice(nextOpen);
            openPriceVO.setClosePrice(nextClose);
            openPriceVO.setDiffOpenPrice(diff);
            openPriceVO.setDiffOCPrice(nextOpen.subtract(nextClose));

            opLine.add(openPriceVO);
            openingPrice = nextOpen;
        }
        return opLine;
    }
    public List<OpenPriceVO> openingPriceLine(String sBegin, String sEnd) throws SQLException, NoSuchAlgorithmException, IOException, KeyManagementException {
        LocalDate begin = LocalDate.parse(sBegin.subSequence(0, sBegin.length()), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate end;
        if(sEnd == null){
            end = LocalDate.now();
        }else {
            end = LocalDate.parse(sEnd.subSequence(0, sEnd.length()), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return openingPriceLine(begin, end);
    }

}
