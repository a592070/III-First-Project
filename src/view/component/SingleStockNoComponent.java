package view.component;

import pojo.StockDayDO;
import pojo.StockTotalNoDO;
import service.StockService;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SingleStockNoComponent extends Box{
    private final int WIDTH = 1600;
    private final int HEIGHT = 900;

    private JTable jTable;
    private TableModel tableModel;
    private JScrollPane jScrollPane;

    private JTextField input;

    private String sStockNo;
    private String sStockDescript;
    private List<StockDayDO> allList;
    private List<StockTotalNoDO> tempList;

    private Vector<String> tableTitle;
    private Vector<Vector> tableDate;
    private Vector vectorData;

    private String[] sTitle = {"代號", "名稱", "成交股數", "成交筆數", "最高價", "最低價", "開盤價", "收盤價", "日期"};

    public SingleStockNoComponent(String sStock){
        super(BoxLayout.Y_AXIS);
        this.sStockDescript = sStock;
        approximateSearch();
        updateList();

        JPanel inputPanel = new JPanel();
        inputPanel.setMaximumSize(new Dimension(WIDTH, 50));

        input = new JTextField(50);
        input.setFont(new Font(null, Font.PLAIN, 20));
        input.setToolTipText("輸入代號 或關鍵字");
        JLabel label = new JLabel("輸入代號 或關鍵字");
        label.setFont(new Font(null, Font.PLAIN, 20));
        JButton inputButton = new JButton("確認");
        inputButton.setFont(new Font(null, Font.PLAIN, 20));

        inputPanel.add(label, BorderLayout.WEST);
        inputPanel.add(input, BorderLayout.CENTER);
        inputPanel.add(inputButton, BorderLayout.EAST);
        this.add(inputPanel);




        settingTableDate(allList);

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
    private void approximateSearch() {
        try {
            tempList = StockService.approximateSearch(this.sStockDescript);
        } catch (IOException|SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "出現異常錯誤!");
        }
    }
    private void updateList(){
        try {
            if (tempList != null || tempList.size()==0) {
                StockService service = null;
                for (StockTotalNoDO stock : tempList) {
                    service = new StockService(stock.getStockNo().toString());
                    service.updateData();
                }
                allList = service.getList();
            }
        }catch (IOException|SQLException|NoSuchAlgorithmException|KeyManagementException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "出現異常錯誤!");
        }
    }
    private void settingTableDate(List<StockDayDO> lis){
        if(tableTitle == null) tableTitle = new Vector<>();
        if(tableDate == null) tableDate = new Vector<>();
        tableDate.clear();

        for (String s : sTitle) {
            tableTitle.add(s);
        }

        lis.forEach(ele->{
            vectorData = new Vector<>();
            vectorData.add(ele.getStockNo());
            vectorData.add(ele.getName());
            vectorData.add(ele.getTradeVolume());
            vectorData.add(ele.getTransAction());
            vectorData.add(ele.getHighestPrice());
            vectorData.add(ele.getLowestPrice());
            vectorData.add(ele.getOpeningPrice());
            vectorData.add(ele.getClosingPrice());
            vectorData.add(ele.getDate());
            tableDate.add(vectorData);
        });

    }
}
