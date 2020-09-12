package view.component;

import pojo.OpenPriceVO;
import pojo.StockDayDO;
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
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

public class StatisticsStockComponent extends Box {
    private final int WIDTH = 1600;
    private final int HEIGHT = 900;

    private JTextField input;
    private JTable jTable;
    private TableModel tableModel;
    private JScrollPane jScrollPane;
    private JFrame jf;

    private String sStockNo;
    private String sStockDescript;
    private List<StockDayDO> list;
    private List<StockDayDO> currList;
    private List<StockTotalNoDO> listStackNo;
    private List<OpenPriceVO> listOpenPriceVO;

    private Vector<String> tableTitle;
    private Vector<Vector> tableDate;
    private Vector vectorData;

    private String[] sTitle = {"代號", "名稱", "開盤價", "收盤價", "漲跌" , "開盤價差", "日期"};

    public StatisticsStockComponent(JFrame jf) {
        super(BoxLayout.Y_AXIS);

        this.jf = jf;

        getAllList();

        JPanel inputPanel = new JPanel();
        inputPanel.setMaximumSize(new Dimension(WIDTH, 50));

        input = new JTextField(50);
        input.setFont(new Font(null, Font.PLAIN, 20));
        input.setToolTipText("輸入代號");

        JLabel label = new JLabel("輸入代號");
        label.setFont(new Font(null, Font.PLAIN, 20));

        JButton inputButton = new JButton("確認");
        inputButton.setFont(new Font(null, Font.PLAIN, 20));
        inputButton.addActionListener(new ActionStatisticsListen(ActionStatisticsListen.INPUT_STOCK_NO));

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

        jScrollPane = new JScrollPane(jTable);

        this.add(jScrollPane);
    }

    private void settingTableDate(List<OpenPriceVO> lis){
        if(tableTitle == null) tableTitle = new Vector<>();
        if(tableDate == null) tableDate = new Vector<>();
        tableDate.clear();

        for (String s : sTitle) {
            tableTitle.add(s);
        }
        if(lis != null) {
            lis.forEach(ele -> {
                vectorData = new Vector<>();
                vectorData.add(ele.getStockNo());
                vectorData.add(ele.getName());
                vectorData.add(ele.getOpenPrice());
                vectorData.add(ele.getClosePrice());
                vectorData.add(ele.getDiffOCPrice());
                vectorData.add(ele.getDiffOpenPrice());
                vectorData.add(ele.getDate());
                tableDate.add(vectorData);
            });
        }
    }

    private  void getAllList() {
        try {
            list = new StockServiceHttp().getList();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "出現異常錯誤!");
        }
    }
    private void updateList(){
        try{
            list = new StockServiceHttp().updateList();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "出現異常錯誤!");
        }
    }
    private void approximateSearch() {
        try {
            listStackNo = new StockServiceHttp().approximateSearch(sStockDescript);

        } catch (IOException|SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "出現異常錯誤!");
        }
    }


    class ActionStatisticsListen implements ActionListener{
        static final int INPUT_STOCK_NO = 1;
        private int type;

        public ActionStatisticsListen(int type) {
            this.type = type;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (type){
                case INPUT_STOCK_NO:
                    getData();
                    break;
            }
            ((AbstractTableModel)tableModel).fireTableDataChanged();
        }

        private void getData() {
            String stockNo = input.getText();
            if(StringUtil.isEmpty(stockNo)) return;
            sStockDescript = stockNo;
            approximateSearch();
            if(listStackNo==null || listStackNo.size()==0) return;

            try {
                LocalDate now = LocalDate.now();

//                int year = now.getYear();
//                int monthValue = now.getMonthValue();
//                StringBuilder begin = new StringBuilder();
//
//                if(monthValue-1 < 10) begin.append(year).append(0).append(monthValue-1).append("01");
                LocalDate begin = now.minusMonths(1);

                listOpenPriceVO = new StockServiceHttp(stockNo).openingPriceLine(begin, now);

                new ConfirmDialog(jf, "更新成功", true, new ActionDoneListener() {
                    @Override
                    public void done(Object result) {
//                        updateList();
                        settingTableDate(listOpenPriceVO);
//                        ((AbstractTableModel)tableModel).fireTableDataChanged();
                    }
                });

            } catch (SQLException | IOException | NoSuchAlgorithmException | KeyManagementException throwables) {
                throwables.printStackTrace();
                JOptionPane.showMessageDialog(null, "出現異常錯誤!");
            }
        }
    }
}
