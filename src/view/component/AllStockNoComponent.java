package view.component;

import pojo.StockTotalNoDO;
import service.StockService;
import service.StockServiceHttp;
import service.StockServiceInterface;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class AllStockNoComponent extends Box {
    private final int WIDTH = 1600;
    private final int HEIGHT = 900;
    private String[] sTitle = {"代號", "名稱", "國際證券辨識號碼(ISIN Code)", "上市日", "產業別"};

    private String[] sGroups = {"塑膠工業", "造紙工業", "電子零組件業", "橡膠工業", "玻璃陶瓷", "電機機械", "水泥工業",
            "建材營造業", "電腦及週邊設備業", "觀光事業", "半導體業", "航運業", "光電業", "貿易百貨業", "" +
            "生技醫療業", "汽車工業", "紡織纖維", "電子通路業", "資訊服務業", "金融保險業", "鋼鐵工業",
            "化學工業", "食品工業", "其他業", "通信網路業", "電器電纜", "油電燃氣業", "其他電子業"};

    private JTable jTable;
    private TableModel tableModel;
    private JScrollPane jScrollPane;

    private JTextField input;

    private Map<String, List<StockTotalNoDO>> map;
    private List<StockTotalNoDO> listAll;
    private String sStockNo;

    private Vector<String> tableTitle;
    private Vector<Vector> tableDate;
    private Vector vectorData;

    public AllStockNoComponent() {
        // 垂直布局
        super(BoxLayout.Y_AXIS);

        JPanel inputPanel = new JPanel();
        inputPanel.setMaximumSize(new Dimension(WIDTH, 50));

        input = new JTextField(50);
        input.setFont(new Font(null, Font.PLAIN, 20));
        input.setToolTipText("輸入代號 或關鍵字");
        JLabel label = new JLabel("輸入代號 或關鍵字");
        label.setFont(new Font(null, Font.PLAIN, 20));
        JButton inputButton = new JButton("確認");
        inputButton.setFont(new Font(null, Font.PLAIN, 20));

        inputButton.addActionListener(new ActionAllStockEvent(ActionAllStockEvent.APPROXIMATE_SEARCH));

        inputPanel.add(label, BorderLayout.WEST);
        inputPanel.add(input, BorderLayout.CENTER);
        inputPanel.add(inputButton, BorderLayout.EAST);
        this.add(inputPanel);



        JPanel groupPanel = new JPanel(new GridLayout(4,7));
        groupPanel.setMaximumSize(new Dimension(WIDTH, 160));
        for (int i = 0; i < sGroups.length; i++) {
            String name = sGroups[i];
            JButton button = new JButton(name);
            button.setFont(new Font(null, Font.PLAIN, 20));
            button.addActionListener(new ActionAllStockEvent(ActionAllStockEvent.SELECT_GROUP, sGroups[i]));
            groupPanel.add(button);
        }
        this.add(groupPanel);



        try {
            settingTableDate(settingList());
        } catch (IOException|SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "出現異常錯誤!");
        }

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
    private List<StockTotalNoDO> settingList() throws IOException, SQLException {
//        if(listAll==null) listAll =StockService.getAllStockNoList();
        if(listAll==null) listAll = new StockServiceHttp().getAllStockNoList();
        return listAll;
    }

    private void settingTableDate(List<StockTotalNoDO> lis){
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
            vectorData.add(ele.getCodeISIN());
            vectorData.add(ele.getDataListed());
            vectorData.add(ele.getGroup());
            tableDate.add(vectorData);
        });

    }

    class ActionAllStockEvent implements ActionListener {
        static final int SELECT_GROUP = 1;
        static final int APPROXIMATE_SEARCH = 2;

        int listenEvent;
        String str;
        public ActionAllStockEvent(int listenEvent) {
            this(listenEvent, "");
        }
        public ActionAllStockEvent(int listenEvent , String str){
            this.str = str;
            this.listenEvent = listenEvent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (listenEvent){
                case SELECT_GROUP:
                    selectGroup();
                    break;
                case APPROXIMATE_SEARCH:
                    approximateSearch();
                    break;
                default:
                    break;
            }
        }
        private void selectGroup(){
            if(map == null) {
                try {
//                    map = StockService.getAllStockNo();
                    map = new StockServiceHttp().getAllStockNo();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "出現異常錯誤!");
                }
            }
            List<StockTotalNoDO> filterList = map.get(str);
            settingTableDate(filterList);
            ((AbstractTableModel)tableModel).fireTableDataChanged();
        }
        private void approximateSearch() {
            List<StockTotalNoDO> tempList;
            try {
//                tempList = StockService.approximateSearch(input.getText());
                tempList = new StockServiceHttp().approximateSearch(input.getText());
                List<StockTotalNoDO> filterList = new ArrayList<>();

                settingTableDate(tempList);
            } catch (IOException|SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "出現異常錯誤!");
            }
            ((AbstractTableModel)tableModel).fireTableDataChanged();
        }
    }
}
