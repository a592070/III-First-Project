package view.component;

import dao.stockDTO.RealTimeStock;
import pojo.OpenPriceVO;
import pojo.RealTimeVO;
import pojo.StockTotalNoDO;
import service.StockServiceHttp;
import utils.StringUtil;
import view.listener.ActionDoneListener;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class RealTimeComponent extends Box {
    private final int WIDTH = 1600;
    private final int HEIGHT = 900;

    private JTextField input;
    private JTable jTable;
    private JTable jTableFive;

    private TableModel tableModel;
    private TableModel tableModelFive;
    private JPanel jPanel;
    private JPanel jPanelFive;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;

    private JFrame jf;

    private String sStockNo;
    private BigDecimal stockNo;

    private List<StockTotalNoDO> listStackNo;
    private List<OpenPriceVO> listOpenPriceVO;

    private Vector<String> tableTitle;
    private Vector<Vector> tableDate;
    private Vector vectorData;

    private Vector<String> tableTitleFive;
    private Vector<Vector> tableDateFive;
    private Vector vectorDataFive;

    private String[] sTitle ={"股票代號", "股票名稱", "最近成交價", "今日最高", "今日最低", "開盤價", "昨收", "漲停點"};
    private String[] sFiveTitle = {"最佳五檔賣出價格", "最佳五檔買入價格"};


    public RealTimeComponent(JFrame jf) {
        super(BoxLayout.Y_AXIS);
        this.jf = jf;

        JPanel inputPanel = new JPanel();
        inputPanel.setMaximumSize(new Dimension(WIDTH, 50));

        input = new JTextField(50);
        input.setFont(new Font(null, Font.PLAIN, 20));
        input.setToolTipText("輸入代號");

        JLabel label = new JLabel("輸入代號");
        label.setFont(new Font(null, Font.PLAIN, 20));

        JButton inputButton = new JButton("確認");
        inputButton.setFont(new Font(null, Font.PLAIN, 20));
        inputButton.addActionListener(new RealTimeAction());


        inputPanel.add(label, BorderLayout.WEST);
        inputPanel.add(input, BorderLayout.CENTER);
        inputPanel.add(inputButton, BorderLayout.EAST);
        this.add(inputPanel);


        settingTableDate(null);
        tableModel = new DefaultTableModel(tableDate, tableTitle);
        jTable = new JTable(tableModel){
            // 不可編輯
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // 只能選一行
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        jTable.setFont(new Font(null, Font.PLAIN, 25));
        jTable.setRowHeight(30);
        jTable.getTableHeader().setFont(new Font(null, Font.BOLD, 25));
        jTable.setAutoCreateRowSorter(true);
        jTable.setUpdateSelectionOnSort(true);

//        jPanel = new JPanel();
//        jPanel.add(jTable);



        tableModelFive = new DefaultTableModel(tableDateFive, tableTitleFive);
        jTableFive = new JTable(tableModelFive){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTableFive.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableFive.setFont(new Font(null, Font.PLAIN, 25));
        jTableFive.setRowHeight(30);
        jTableFive.getTableHeader().setFont(new Font(null, Font.BOLD, 25));
        jTableFive.setAutoCreateRowSorter(true);
        jTableFive.setUpdateSelectionOnSort(true);

//        jPanelFive = new JPanel();
//        jPanelFive.add(jTable);


//        Box vBox = Box.createVerticalBox();
//        Box tableBox = Box.createHorizontalBox();
//        Box tableFiveBox = Box.createHorizontalBox();
//
//        vBox.add(tableBox);
//        vBox.add(Box.createVerticalStrut(100));
//        vBox.add(tableFiveBox);
//
//        this.add(vBox);

//        this.add(jTable);
//        this.add(jTableFive);

        jScrollPane1 = new JScrollPane(jTable);
        jScrollPane2 = new JScrollPane(jTableFive);
        this.add(jScrollPane1);
        this.add(jScrollPane2);
    }
    private void approximateSearch() {
        sStockNo = input.getText();
        if(StringUtil.isEmpty(sStockNo)) return;

        try {
            listStackNo = new StockServiceHttp().approximateSearch(sStockNo);
            if(listStackNo!=null && listStackNo.size()>0) stockNo = listStackNo.get(0).getStockNo();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "出現異常錯誤!");
        }
    }
    private RealTimeVO getRealTimeVO(){
        RealTimeVO vo = new RealTimeVO();
        try {
            vo = new RealTimeStock(stockNo.toString()).init();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "出現異常錯誤!");
        }
        return vo;
    }

    private void settingTableDate(RealTimeVO vo){
        if(tableTitle == null) tableTitle = new Vector<>();
        if(tableDate == null) tableDate = new Vector<>();


        if(tableTitleFive == null) tableTitleFive = new Vector<>();
        if(tableDateFive == null) tableDateFive = new Vector<>();

        tableDate.clear();
        tableDateFive.clear();

        tableTitle.addAll(Arrays.asList(sTitle));
        tableTitleFive.addAll(Arrays.asList(sFiveTitle));
        if(vo != null) {
            vectorData = new Vector();
            vectorData.add(vo.getStockNo());
            vectorData.add(vo.getName());
            if(!vo.getLatestTradePrice().equals("-")) vectorData.add(vo.getLatestTradePrice());
            vectorData.add(vo.getHigh());
            vectorData.add(vo.getLow());
            vectorData.add(vo.getOpen());
            vectorData.add(vo.getClose());
            vectorData.add(vo.getLimitUp());
            tableDate.add(vectorData);

//            vectorDataFive = new Vector();
//            vectorDataFive.add(vo.getBestAskPrice1());
//            vectorDataFive.add(vo.getBestBidPrice1());
            tableDateFive.add(new Vector(Arrays.asList(new String[]{vo.getBestAskPrice1(), vo.getBestBidPrice1()})));
            tableDateFive.add(new Vector(Arrays.asList(new String[]{vo.getBestAskPrice2(), vo.getBestBidPrice2()})));
            tableDateFive.add(new Vector(Arrays.asList(new String[]{vo.getBestAskPrice3(), vo.getBestBidPrice3()})));
            tableDateFive.add(new Vector(Arrays.asList(new String[]{vo.getBestAskPrice4(), vo.getBestBidPrice4()})));
            tableDateFive.add(new Vector(Arrays.asList(new String[]{vo.getBestAskPrice5(), vo.getBestBidPrice5()})));

//            vectorDataFive = new Vector();
//            vectorDataFive.add(vo.getBestAskPrice2());
//            vectorDataFive.add(vo.getBestBidPrice2());
//            tableDateFive.add(vectorDataFive);
//
//            vectorDataFive = new Vector();
//            vectorDataFive.add(vo.getBestAskPrice3());
//            vectorDataFive.add(vo.getBestBidPrice3());
//            tableDateFive.add(vectorDataFive);
//
//            vectorDataFive = new Vector();
//            vectorDataFive.add(vo.getBestAskPrice4());
//            vectorDataFive.add(vo.getBestBidPrice4());
//            tableDateFive.add(vectorDataFive);
//
//            vectorDataFive = new Vector();
//            vectorDataFive.add(vo.getBestAskPrice5());
//            vectorDataFive.add(vo.getBestBidPrice5());
//            tableDateFive.add(vectorDataFive);
        }
    }

    class RealTimeAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            approximateSearch();
            new ConfirmDialog(jf, "更新成功", true, new ActionDoneListener() {
                @Override
                public void done(Object result) {
                    RealTimeVO vo = getRealTimeVO();
                    settingTableDate(vo);
                    ((AbstractTableModel)tableModel).fireTableDataChanged();
                    ((AbstractTableModel)tableModelFive).fireTableDataChanged();

                    // 先執行一次再跑 timer
                    new Timer(5000, new TimerTask()).start();
                }
            });

        }
    }

    class TimerTask implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            RealTimeVO vo = getRealTimeVO();
            settingTableDate(vo);
            ((AbstractTableModel)tableModel).fireTableDataChanged();
            ((AbstractTableModel)tableModelFive).fireTableDataChanged();
        }
    }


}
