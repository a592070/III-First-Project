package view.component;

import view.listener.ActionDoneListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmDialog extends JDialog {
    private final int WIDTH = 300;
    private final int HEIGHT = 200;

    private ActionDoneListener listener;

    public ConfirmDialog(JFrame jf, String title, boolean isModel, ActionDoneListener listener) {
        super(jf, title, isModel);

        this.listener = listener;
        this.setBounds(0,0,WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);

        Box vBox = Box.createVerticalBox();
        Box box = Box.createHorizontalBox();
        JButton button = new JButton("成功");

        button.addActionListener(new ConfirmAction());

        box.add(button);

        vBox.add(Box.createVerticalStrut(50));
        vBox.add(box);

        this.add(vBox);
        this.setVisible(true);
    }
    class ConfirmAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            listener.done("null");
        }
    }
}
