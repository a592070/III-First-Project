package view.component;

import pojo.StockDayDO;
import service.StockService;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.List;

public class SingleStockNoComponent extends Box{
    private final int WIDTH = 1600;
    private final int HEIGHT = 900;

    private JTable jTable;
    private TableModel tableModel;
    private JScrollPane jScrollPane;

    private String sStockNo;
    private List<StockDayDO> list;

    public SingleStockNoComponent(String sStock){
        super(BoxLayout.Y_AXIS);



        JPanel inputPanel = new JPanel();
        inputPanel.setMaximumSize(new Dimension(WIDTH, 50));

        JTextField input = new JTextField(50);
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

    }
}
