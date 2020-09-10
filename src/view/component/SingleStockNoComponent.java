package view.component;

import pojo.StockDayDO;
import pojo.StockTotalNoDO;
import service.StockService;
import service.StringUtil;

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
    private List<StockDayDO> list;
    private List<StockDayDO> currList;
    private List<StockTotalNoDO> listStackNo;

    private Vector<String> tableTitle;
    private Vector<Vector> tableDate;
    private Vector vectorData;

    private String[] sTitle = {"代號", "名稱", "成交股數", "成交筆數", "最高價", "最低價", "開盤價", "收盤價", "日期"};

    public SingleStockNoComponent(String sStock){
        super(BoxLayout.Y_AXIS);
        this.sStockDescript = sStock;
        approximateSearch();
        getAllList();
        getCurrentList();


        JPanel inputPanel = new JPanel();
        inputPanel.setMaximumSize(new Dimension(WIDTH, 50));

        input = new JTextField(50);
        input.setFont(new Font(null, Font.PLAIN, 20));
        input.setToolTipText("輸入代號 或關鍵字");

        JLabel label = new JLabel("輸入代號 或關鍵字");
        label.setFont(new Font(null, Font.PLAIN, 20));

        JButton inputButton = new JButton("確認");
        inputButton.setFont(new Font(null, Font.PLAIN, 20));
        inputButton.addActionListener(new ActionSingleStockEvent(ActionSingleStockEvent.INPUT_NO));

        inputPanel.add(label, BorderLayout.WEST);
        inputPanel.add(input, BorderLayout.CENTER);
        inputPanel.add(inputButton, BorderLayout.EAST);
        this.add(inputPanel);



        JPanel buttonPanel = new JPanel();
        buttonPanel.setMaximumSize(new Dimension(WIDTH, 80));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton buttonUpdate = new JButton("更新資料");
        JButton buttonShowAll = new JButton("顯示全部");

        buttonUpdate.addActionListener(new ActionSingleStockEvent(ActionSingleStockEvent.UPDATE_LIST));
        buttonShowAll.addActionListener(new ActionSingleStockEvent(ActionSingleStockEvent.SHOW_ALL));

        buttonPanel.add(buttonUpdate);
        buttonPanel.add(buttonShowAll);



        settingTableDate(currList);

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
            listStackNo = StockService.approximateSearch(this.sStockDescript);
        } catch (IOException|SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "出現異常錯誤!");
        }
    }
    private  void getAllList() {
        try {
            list = StockService.getList();
        } catch (IOException|SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "出現異常錯誤!");
        }
    }
    private void getCurrentList(){
        currList = new ArrayList<>();
        if (listStackNo != null || listStackNo.size()!=0 || list == null) {
            List<String> collection = new ArrayList<>();
            listStackNo.forEach(ele -> {
                collection.add(ele.getStockNo().toString());
                collection.add(ele.getName());
            });

            list.stream().filter(ele -> collection.contains(ele.getStockNo().toString()) || collection.contains(ele.getName()))
                    .forEach(ele -> currList.add(ele));
        }
    }
    private void updateList(){
        // form input text
        String no = null;
        String beginDate = null;
        String endDate = null;
        try {
            if (StringUtil.isEmpty(no)) {
                StockService service = new StockService(no);
                service.updateData();

                list = service.getList();
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
        ((AbstractTableModel)tableModel).fireTableDataChanged();
    }

    class ActionSingleStockEvent implements ActionListener{
        static final int INPUT_NO = 1;
        static final int UPDATE_LIST = 2;
        static final int SHOW_ALL = 3;

        private int type;
        public ActionSingleStockEvent(int type) {
            this.type = type;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (this.type){
                case INPUT_NO:
                    sStockDescript = input.getText();
                    approximateSearch();
                    getCurrentList();
                    settingTableDate(currList);
                    break;
                case UPDATE_LIST:

                    break;
                case SHOW_ALL:
                    getAllList();
                    settingTableDate(list);
                    break;
                default:
                    break;
            }
        }
    }
}
