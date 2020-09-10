package view;

import service.LoginService;
import service.StringUtil;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;


public class Login {
    private JPanel panel1;
    private JPanel loginPanel;
    private JLabel userLabel;
    private JTextField userTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton confirm;
    private JButton cancel;
    private JButton register;

    private LoginListener listen;


    public void init() {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new Login().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setResizable(false);

    }

    public Login() {
        listen = new LoginListener();
        cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                listen.cancelClick();
            }
        });
        confirm.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                listen.confirmClick();
            }
        });
        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                listen.registerClick();
            }
        });

    }
    class LoginListener{
        void cancelClick(){
            // 清空 TextField內容
            userTextField.setText("");
            passwordField.setText("");
        }

        void confirmClick(){
            String userName = userTextField.getText();
            String password = String.valueOf(passwordField.getPassword());

            if(StringUtil.isEmpty(userName) || StringUtil.isEmpty(password)){
                JOptionPane.showMessageDialog(null, "請輸入正確帳號密碼");
                cancelClick();
                return;
            }
            try {
                LoginService service = new LoginService(userName, password);
                boolean isLogin = service.login();
                if(isLogin){
                    JOptionPane.showMessageDialog(null, "歡迎回來 "+userName);
                }else{
                    JOptionPane.showMessageDialog(null, "用戶帳號或密碼錯誤");
                    passwordField.setText("");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        void registerClick(){
            String userName = userTextField.getText();
            String password = String.valueOf(passwordField.getPassword());

            if(StringUtil.isEmpty(userName) || StringUtil.isEmpty(password)){
                JOptionPane.showMessageDialog(null, "請輸入正確帳號密碼");
                return;
            }
            try {
                LoginService service = new LoginService(userName, password);
                boolean isRegister = service.isRegistered();
                if(!isRegister){
                    if(service.register()) {
                        JOptionPane.showMessageDialog(null, "註冊成功 " + userName+" ，請重新登錄");
                        passwordField.setText("");
                    }else{
                        JOptionPane.showMessageDialog(null, "請重新確認");
                        cancelClick();
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "你已經註冊過了，"+userName);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
