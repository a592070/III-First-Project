package view;

import view.component.AllStockNoComponent;
import view.component.SingleStockNoComponent;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.ExpandVetoException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerFrame {
    JFrame jf = new JFrame("Stock system");

    final int WIDTH = 1600;
    final int HEIGHT = 900;

    private String inputStock;


    public void init(){
        jf.setBounds(0,0,WIDTH,HEIGHT);
        jf.setResizable(false);
        jf.setLocationRelativeTo(null); // 居中顯示

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
//        DefaultMutableTreeNode stockDataNode1 = new DefaultMutableTreeNode("每月個股資料");
        DefaultMutableTreeNode stockStatsNode = new DefaultMutableTreeNode("股票分析");

        stockRoot.add(stockNoNode);
        stockRoot.add(stockDataNode);
        stockRoot.add(stockStatsNode);
//        stockDataNode.add(stockDataNode1);
//        stockDataNode.add(stockStatsNode1);

        JTree jTree = new JTree(stockRoot);
        jTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                Object lastPathComponent = e.getNewLeadSelectionPath().getLastPathComponent();

                if(stockNoNode.equals(lastPathComponent)){
                    jSplitPane.setRightComponent(new AllStockNoComponent());
                    jSplitPane.setDividerLocation(150);

                }else if(stockDataNode.equals(lastPathComponent)){
                    if(inputStock == null) inputStock = JOptionPane.showInputDialog("輸入股票代號 或 名稱");

                    jSplitPane.setRightComponent(new SingleStockNoComponent(inputStock));
                    jSplitPane.setDividerLocation(150);
                }else if(stockStatsNode.equals(lastPathComponent)){
                    jSplitPane.setRightComponent(new JLabel("股票分析"));
                    jSplitPane.setDividerLocation(150);
                }
            }
        });
        jTree.addTreeWillExpandListener(new TreeWillExpandListener() {
            @Override
            public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
                if(inputStock == null) inputStock = JOptionPane.showInputDialog("輸入股票代號 或 名稱");
                System.out.println(inputStock);
            }

            @Override
            public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
                inputStock = null;
                System.out.println(inputStock);
            }
        });



        jSplitPane.setRightComponent(new AllStockNoComponent());
        jSplitPane.setLeftComponent(jTree);
        jf.add(jSplitPane);


        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jf.setVisible(true);
    }

    public static void main(String[] args) {
        new ManagerFrame().init();
    }

}
