package view;

import pojo.StockDayDO;
import pojo.StockTotalNoDO;
import pojo.UserDO;
import service.StockServiceHttp;
import view.component.AllStockNoComponent;
import view.component.RealTimeComponent;
import view.component.SingleStockNoComponent;
import view.component.StatisticsStockComponent;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.ExpandVetoException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ManagerFrame {
    JFrame jf = new JFrame("Stock system");

    final int WIDTH = 1600;
    final int HEIGHT = 900;

    public static String inputStock;
    private String userName;

    protected UserDO user;
    protected List<StockDayDO> list;
    protected List<StockTotalNoDO> listStackNo;


    public void init(UserDO user){
        jf.setBounds(0,0,WIDTH,HEIGHT);
        jf.setResizable(false);
        jf.setLocationRelativeTo(null); // 居中顯示
        jf.setTitle("歡迎回來! "+user.getUserName());

        this.user = user;
        getAllList();

        // Setting menu
        JMenuBar jmBar = new JMenuBar();
        JMenu jMenu = new JMenu("Setting");
        JMenuItem changeUser = new JMenuItem("切換帳號");
        JMenuItem exitSys = new JMenuItem("Exit");

        changeUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrame().init();
                jf.dispose();
            }
        });
        exitSys.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        jMenu.add(changeUser);
        jMenu.add(exitSys);
        jmBar.add(jMenu);

        jf.setJMenuBar(jmBar);


        // Content Panel
        JSplitPane jSplitPane = new JSplitPane();
        jSplitPane.setContinuousLayout(true);
        jSplitPane.setDividerLocation(150);
        jSplitPane.setDividerSize(7);

        DefaultMutableTreeNode stockRoot = new DefaultMutableTreeNode("股票系統");
        DefaultMutableTreeNode stockNoNode = new DefaultMutableTreeNode("台股代號總表");
        DefaultMutableTreeNode stockDataNode = new DefaultMutableTreeNode("個股資料");
        DefaultMutableTreeNode stockStatisticsNode = new DefaultMutableTreeNode("股票分析");
        DefaultMutableTreeNode realTimeNode = new DefaultMutableTreeNode("即時股票資訊");

        stockRoot.add(stockNoNode);
        stockRoot.add(stockDataNode);
        stockRoot.add(stockStatisticsNode);
        stockRoot.add(realTimeNode);


        JTree jTree = new JTree(stockRoot);
        jTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                Object lastPathComponent = e.getNewLeadSelectionPath().getLastPathComponent();

                if(stockNoNode.equals(lastPathComponent)){
//                    inputStock = null;
                    jSplitPane.setRightComponent(new AllStockNoComponent());
                    jSplitPane.setDividerLocation(150);

                }else if(stockDataNode.equals(lastPathComponent)){
//                    if(inputStock == null) inputStock = JOptionPane.showInputDialog("輸入股票代號 或 名稱");

                    jSplitPane.setRightComponent(new SingleStockNoComponent(jf));
                    jSplitPane.setDividerLocation(150);
                }else if(stockStatisticsNode.equals(lastPathComponent)){
                    jSplitPane.setRightComponent(new StatisticsStockComponent(jf));
                    jSplitPane.setDividerLocation(150);
                }else if(realTimeNode.equals(lastPathComponent)){
                    jSplitPane.setRightComponent(new RealTimeComponent(jf));
                    jSplitPane.setDividerLocation(150);
                }
            }
        });


        jSplitPane.setRightComponent(new AllStockNoComponent());
        jSplitPane.setLeftComponent(jTree);
        jf.add(jSplitPane);


        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jf.setVisible(true);
    }

    private  void getAllList() {
        try {
//            list = StockService.getList();
            list = new StockServiceHttp().getList();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "出現異常錯誤!");
        }
    }
}
