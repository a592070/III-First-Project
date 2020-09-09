package View;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    public MainPanel() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.setBackground(Color.LIGHT_GRAY);
        g.fillRect(25,75,850,600);


//        setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));

        JTextField userInput = new JTextField("",10);
        userInput.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        JLabel userLabel = new JLabel("user: ");
        userLabel.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        JPanel userPanel = new JPanel();
        userPanel.add(userLabel);
        userPanel.add(userInput);


        JTextField passwordInput = new JTextField("",10);
        passwordInput.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        JLabel passwordLabel = new JLabel("password: ");
        passwordLabel.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        JPanel passwordPanel = new JPanel();
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordInput);
//        this.add(passwordPanel, BorderLayout.CENTER);

        JPanel loginPanel = new JPanel(new GridLayout(2,2));
//        loginPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        loginPanel.add(userLabel);
        loginPanel.add(userInput);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordInput);

//        loginPanel.setSize(400,300);


    }
}
