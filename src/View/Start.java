package View;

import javax.swing.*;

public class Start {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        frame.add(new MainPanel());


        frame.setVisible(true);
        frame.setBounds(0,0,900,725);
        // 窗口大小固定
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
