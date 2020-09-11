package view.component;

import service.StockServiceHttp;
import service.StockServiceSuper;
import utils.StringUtil;
import view.listener.ActionDoneListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UpdateStockDialog extends JDialog {
    private final int WIDTH = 500;
    private final int HEIGHT = 200;

    private ActionDoneListener listener;

    private JTextField noTextField;
    private JTextField dateTextField;

    // isModel 視窗存在時，其他視窗不能操作 T/F
    public UpdateStockDialog(JFrame jf, String title, boolean isModel, ActionDoneListener listener) {
        super(jf, title, isModel);

        this.listener = listener;

        this.setBounds(0,0,WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);

        Box vBox = Box.createVerticalBox();

        Box stockNoBox = Box.createHorizontalBox();
        JLabel noLabel = new JLabel("股票代號: ");
        noTextField = new JTextField(5);

        stockNoBox.add(noLabel);
        stockNoBox.add(Box.createHorizontalStrut(80));
        stockNoBox.add(noTextField, FlowLayout.RIGHT);

        Box dateBox = Box.createHorizontalBox();
        JLabel dateLabel = new JLabel("更新月份(yyyyMM):");
        dateTextField = new JTextField(6);
        dateTextField.setToolTipText("默認為當前月份");


        dateBox.add(dateLabel);
        dateBox.add(Box.createHorizontalStrut(10));
        dateBox.add(dateTextField, FlowLayout.RIGHT);

        Box clickBox = Box.createHorizontalBox();
        JButton button = new JButton("確認");
        button.addActionListener(new UpdateAction());
        clickBox.add(button);


        vBox.add(Box.createVerticalStrut(20));
        vBox.add(stockNoBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(dateBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(clickBox);

        this.add(vBox);
        this.setVisible(true);
    }
    class UpdateAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String stockNo = noTextField.getText();
            String date = dateTextField.getText();

            if(!StringUtil.isEmpty(stockNo)){
                try {
                    StockServiceSuper service = new StockServiceHttp(stockNo);

                    boolean result;
                    if(!StringUtil.isEmpty(date) && date.matches("\\d{4}\\d{2}")){
                        result = service.updateData(date+"01");
                    }else{
                        result = service.updateData();
                    }
                    if(result){
                        JOptionPane.showMessageDialog(null, "更新成功!");
                        dispose();

                        listener.done(stockNo);
                    }else{
                        JOptionPane.showMessageDialog(null, "更新失敗，請重試!");
                    }
                } catch (IOException | SQLException | NoSuchAlgorithmException | KeyManagementException ioException) {
                    JOptionPane.showMessageDialog(null, "出現異常錯誤!");
                    ioException.printStackTrace();
                }
            }else{
                JOptionPane.showMessageDialog(null, "請輸入代號!");
            }

        }
    }
}
