package edu.ptithcm.view.Login;

import edu.ptithcm.util.DoSomething;
import edu.ptithcm.view.Window.SubWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author Le Ngoc Tu
 */
public class LoginFrame extends SubWindow {


    /**
     * test
     */
//    public static void main(String []args){
//        LoginFrame l = new LoginFrame();
//        l.setPermitRegister(true);
//        l.setChecker((u, p)->(u.compareTo("tule") == 0 && p.compareTo("123") == 0 ));
//        l.setDoRegister(o-> System.out.println("Register"));
//        l.setDoAfterLogin(o->System.out.println("Login success"));
//        l.setVisible(true);
//    }

    private JTextField inputLoginName;
    private JPasswordField inputPasswd;
    private JCheckBox showPasswdCheckBox;
    private JButton loginButton;
    private JButton reigisterButton;
    private boolean permitRegister; //Co hien nut dang ky tai khoang khong

    private LoginChecker checker;
    private DoSomething doAfterLogin;   //Hanh dong sau khi dang nhap thanh con
    private DoSomething doRegister;     //Hanh dong khi an nut"Dang ky tai khoang"



    public LoginFrame(){
        setTitle("Login");
        setConfirmClosingDialogMessage("Bạn có xác nhận hủy đăng nhập?");
        setConfirmClosingDialogTitle("Xác nhận hủy đăng nhập");
        //mac dinh cac interface ko lam gi het
        setChecker((u, p)->false);
        setDoAfterLogin((o)->{});
        setDoRegister((o)->{});


        initLoginButton();
        initRegisterButton();
        JPanel loginNamePanel = initLoginNamePanel();
        JPanel passwdPanel = initPasswdPanel();
        JPanel showPasswdPanel = initShowPasswdPanel();

        setLayout(new GridLayout(5, 1));
        add(loginNamePanel);
        add(passwdPanel);
        add(showPasswdPanel);
        add(loginButton);
        add(reigisterButton);

//        JPanel mainPanel = new JPanel();
//        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
//        mainPanel.add(loginNamePanel);
//        mainPanel.add(passwdPanel);
//        mainPanel.add(loginButton);
//        mainPanel.add(reigisterButton);
//        add(mainPanel);

        setPermitRegister(false);
    }

    public void setChecker(LoginChecker checker) {
        this.checker = checker;
    }

    public void setDoAfterLogin(DoSomething doAfterLogin) {
        this.doAfterLogin = doAfterLogin;
    }

    public void setDoRegister(DoSomething doRegister) {
        this.doRegister = doRegister;
    }

    public void setPermitRegister(boolean permitRegister){
        this.permitRegister = permitRegister;
        reigisterButton.setVisible(permitRegister);
    }

    private void initLoginButton(){
        loginButton = new JButton("Đăng nhập");
        loginButton.addActionListener(e->{
            String u = inputLoginName.getText();                //u - User
            String p = new String(inputPasswd.getPassword());   //p - Password
            if(checker.check(u, p)){
                dispose();
                doAfterLogin.doing(u);  //Dua userLoginName vao de xu ly
            }else{
                JOptionPane.showMessageDialog(
                        null,
                        "Tên đăng nhập hoặc mật khẩu không đúng",
                        "Thông báo",
                        JOptionPane. ERROR_MESSAGE
                );
            }
        });
    }

    private void initRegisterButton(){
        reigisterButton = new JButton("Đăng ký");
        reigisterButton.addActionListener(e->{
            dispose();
            doRegister.doing(null);
        });
    }

    private JPanel initLoginNamePanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        panel.add(new JLabel("Tên đăng nhập"));
        inputLoginName = new JTextField();
        panel.add(inputLoginName);

        return panel;
    }

    private JPanel initPasswdPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        panel.add(new JLabel("Mật khẩu"));
        inputPasswd = new JPasswordField();
        panel.add(inputPasswd);

        return panel;
    }

    private JPanel initShowPasswdPanel(){
        showPasswdCheckBox = new JCheckBox("Hiện mật khẩu");
        showPasswdCheckBox.addActionListener(new AbstractAction() {
            private char echo = inputPasswd.getEchoChar();
            @Override
            public void actionPerformed(ActionEvent e) {
                if(showPasswdCheckBox.isSelected())
                    inputPasswd.setEchoChar(echo);
                else
                    inputPasswd.setEchoChar((char)0);
            }
        });
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(new JLabel());
        panel.add(showPasswdCheckBox);
        return panel;
    }


}