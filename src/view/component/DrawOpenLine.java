package view.component;

import pojo.OpenLineVO;

import javax.swing.*;
import java.awt.*;

public class DrawOpenLine extends JDialog {
    private final int WIDTH = 300;
    private final int HEIGHT = 200;
    private OpenLineVO openLineVO;

    public DrawOpenLine(JFrame jf, String title, boolean isModel, OpenLineVO openLineVO) {
        super(jf, title, isModel);
        this.setBounds(0,0,WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);

        this.openLineVO = openLineVO;

        this.setVisible(true);
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
        g2d.dispose();


    }
}
