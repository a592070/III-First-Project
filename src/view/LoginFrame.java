package view;

import pojo.UserDO;
import service.LoginService;
import service.LoginServiceHttp;
import service.LoginServiceSuper;
import utils.StringUtil;
import view.component.RandNumImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.sql.SQLException;

public class LoginFrame {
    JFrame jf = new JFrame("Login");

    final int WIDTH = 800;
    final int HEIGHT = 450;

    private String username;

    private JTextField userField;
    private JPasswordField passwordField;

    private JLabel ckLabel;
    private JTextField ckField;
    private RandNumImage imgNum;
    private String checkNum;
    private Image img;

    public void init() {
        jf.setBounds(0,0,WIDTH,HEIGHT);
        jf.setResizable(false);
        jf.setLocationRelativeTo(null); // 居中顯示

        Box vBox = Box.createVerticalBox();


        // user password
        Box userBox = Box.createHorizontalBox();
        JLabel userLabel = new JLabel("User: ");
        userField = new JTextField(15);

        userBox.add(userLabel);
        userBox.add(Box.createHorizontalStrut(70)); // 間隔作用
        userBox.add(userField);

        Box passwordBox = Box.createHorizontalBox();
        JLabel passwordLabel = new JLabel("Password: ");
        passwordField = new JPasswordField(15);

        passwordBox.add(passwordLabel);
        passwordBox.add(Box.createHorizontalStrut(20));
        passwordBox.add(passwordField);


        imgNum = new RandNumImage();
        try {
            img = imgNum.init();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "出現異常錯誤!");
        }
        checkNum = imgNum.getImgNum();


        Box checkBox = Box.createHorizontalBox();

        ckLabel = new JLabel(new ImageIcon(img));
        ckField = new JTextField(4);
        JButton resetBtn = new JButton("換一張");
        resetBtn.addActionListener(new ActionLogin(ActionLogin.RESET_IMG));

        checkBox.add(ckLabel);
        checkBox.add(Box.createHorizontalStrut(10));
        checkBox.add(ckField);
//        checkBox.add(Box.createHorizontalStrut(20));
        checkBox.add(resetBtn);



        userLabel.setFont(new Font(null, Font.BOLD, 20));
        userField.setFont(new Font(null, Font.PLAIN, 20));
        passwordLabel.setFont(new Font(null, Font.BOLD, 20));
        passwordField.setFont(new Font(null, Font.PLAIN, 20));


        // button
        Box buttonBox = Box.createHorizontalBox();
        JButton loginBtn = new JButton("登錄");
        JButton cancelBtn = new JButton("取消");
        JButton registerBtn = new JButton("註冊");

        loginBtn.addActionListener(new ActionLogin(ActionLogin.LOGIN_ACTION));
        cancelBtn.addActionListener(new ActionLogin(ActionLogin.CANCEL_ACTION));
        registerBtn.addActionListener(new ActionLogin(ActionLogin.REGISTER_ACTION));

        loginBtn.setFont(new Font(null, Font.BOLD, 20));
        cancelBtn.setFont(new Font(null, Font.BOLD, 20));
        registerBtn.setFont(new Font(null, Font.BOLD, 20));


        buttonBox.add(loginBtn);
        buttonBox.add(Box.createHorizontalStrut(75));
        buttonBox.add(cancelBtn);
        buttonBox.add(Box.createHorizontalStrut(75));
        buttonBox.add(registerBtn);


        vBox.add(Box.createVerticalStrut(90));
        vBox.add(userBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(passwordBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(checkBox);
        vBox.add(Box.createVerticalStrut(40));
        vBox.add(buttonBox);

        JPanel jPanel = new JPanel();
        jPanel.add(vBox);
        jPanel.setBackground(Color.lightGray);
        jf.add(jPanel);


        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }

    class ActionLogin implements ActionListener{
        static final int LOGIN_ACTION = 1;
        static final int CANCEL_ACTION = 2;
        static final int REGISTER_ACTION = 3;
        static final int RESET_IMG = 4;
        private int action;
        public ActionLogin(int action) {
            this.action = action;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (action){
                case LOGIN_ACTION:
                    confirmClick();
                    break;
                case CANCEL_ACTION:
                    cancelClick();
                    break;
                case REGISTER_ACTION:
                    registerClick();
                    break;
                case RESET_IMG:
                    resetImg();
                    break;

            }
        }
        void cancelClick(){
            // 清空 TextField內容
            userField.setText("");
            passwordField.setText("");
            ckField.setText("");
        }
        void confirmClick(){
            String userName = userField.getText();
            String password = String.valueOf(passwordField.getPassword());
            String inputCkNum = ckField.getText();

            if(StringUtil.isEmpty(userName) || StringUtil.isEmpty(password)){
                JOptionPane.showMessageDialog(null, "請輸入正確帳號密碼!");
                cancelClick();
            }else if(checkNum.equals(inputCkNum)) {
                try {
//                LoginService service = new LoginService(userName, password);
                    LoginServiceSuper service = new LoginServiceHttp(userName, password);
                    boolean isLogin = service.login();
                    if (isLogin) {
                        JOptionPane.showMessageDialog(null, "歡迎回來! " + userName);

//                    username = userName;
                        UserDO userDO = service.getUser();

                        // 進入主畫面
                        new ManagerFrame().init(userDO);
                        // 當前畫面消失
                        jf.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "帳號、密碼或驗證碼錯誤!");
                        passwordField.setText("");
                        ckField.setText("");
                    }

                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "出現異常錯誤!");
                    cancelClick();
                }

            }else{
                JOptionPane.showMessageDialog(null, "帳號、密碼或驗證碼錯誤!");
                passwordField.setText("");
                ckField.setText("");
            }
            resetImg();
        }
        void registerClick(){
            String userName = userField.getText();
            String password = String.valueOf(passwordField.getPassword());
            String inputCkNum = ckField.getText();

            if(StringUtil.isEmpty(userName) || StringUtil.isEmpty(password) || !checkNum.equals(inputCkNum)){
                JOptionPane.showMessageDialog(null, "帳號、密碼或驗證碼錯誤!");
                cancelClick();
                resetImg();
                return;
            }
            try {
//                LoginService service = new LoginService(userName, password);
                LoginServiceSuper service = new LoginServiceHttp(userName, password);
                boolean isRegister = service.isRegistered();
                if(!isRegister){
                    if(service.register()) {
                        JOptionPane.showMessageDialog(null, "註冊成功 " + userName+" ，請重新登錄");
                        passwordField.setText("");
                        ckField.setText("");

                    }else{
                        JOptionPane.showMessageDialog(null, "請重新確認");
                        cancelClick();
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "你已經註冊過了，"+userName);
                    passwordField.setText("");
                    ckField.setText("");
                }

            } catch (IOException|SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "出現異常錯誤!");
                cancelClick();
            }
            resetImg();
        }
        void resetImg(){
            imgNum = new RandNumImage();
            try {
                img = imgNum.init();
                checkNum = imgNum.getImgNum();
                ckLabel.setIcon(new ImageIcon(img));
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "出現異常錯誤!");
            }
            ckLabel.updateUI();
        }
    }


}
