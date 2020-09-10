package view;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    public MainPanel() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

//        g.fillRect(25,75,850,600);

        JPanel loginPanel = new JPanel();
        setLayout(new BorderLayout(50, 200));


        this.add(loginPanel, BorderLayout.CENTER);
        this.add(new JPanel(), BorderLayout.NORTH);
        this.add(new JPanel(), BorderLayout.WEST);
        this.add(new JPanel(), BorderLayout.EAST);
        this.add(new JPanel(), BorderLayout.SOUTH);

        loginPanel.setLayout(new BorderLayout(40, 30));


        GridLayout lo = new GridLayout(2,1, 0, 20);

        JPanel labelPanel = new JPanel(lo);
        JPanel inputPanel = new JPanel(lo);
        JLabel title = new JLabel("LOGIN");
        JPanel buttonPanel = new JPanel();
        JPanel checkPanel = new JPanel();

        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font(Font.SERIF, Font.BOLD, 25));



        loginPanel.add(labelPanel, BorderLayout.WEST);
        loginPanel.add(inputPanel, BorderLayout.CENTER);
        loginPanel.add(title, BorderLayout.NORTH);
        loginPanel.add(buttonPanel, BorderLayout.SOUTH);
        loginPanel.add(checkPanel, BorderLayout.EAST);


        JTextField userInput = new JTextField("",10);
        userInput.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        JLabel userLabel = new JLabel("User: ");
        userLabel.setFont(new Font(Font.SERIF, Font.BOLD, 25));


        JTextField passwordInput = new JPasswordField("",10);
        passwordInput.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(new Font(Font.SERIF, Font.BOLD, 25));

        labelPanel.add(userLabel);
        labelPanel.add(passwordLabel);
        inputPanel.add(userInput);
        inputPanel.add(passwordInput);

        JButton confirm = new JButton("confirm");
        JButton cancel = new JButton("cancel");
        buttonPanel.setLayout(new GridLayout(1,2,250,10));
        buttonPanel.add(confirm);
        buttonPanel.add(cancel);


//        this.setBackground(Color.LIGHT_GRAY);


    }
}
