package edu.ptithcm.view.CustomerView;

import edu.ptithcm.controller.*;
import edu.ptithcm.view.Window.SubWindow;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

/**
 * @author Le Ngoc Tu
 */
public class RegisterFrame extends SubWindow{
    // COMPONENT
    private JLabel emailLabel;
    private JTextField emailTextField;

    private JLabel passLabel_1;
    private JPasswordField passwordField_1;

    private JLabel passLabel_2;
    private JPasswordField passwordField_2;

    private JLabel nameLabel;
    private JTextField nameTextField;

    private JLabel phoneLabel;
    private JTextField phoneTextField;

    private JLabel addressLabel;
    private JTextField addressTextField;

    private JButton registerButton;
    private JCheckBox showPasswdCheckBox;

    private char defaultEchoChar;   //ky tuc mac dinh thay the khi phap password

    //------------------------------------------------------------------------------------------------

    public RegisterFrame(){
        setTitle("Đăng ký tài khoảng");
        setConfirmClosingDialogMessage("Bạn có muốn dừng việc đăng ký tài khoảng?");
        setConfirmClosingDialogTitle("Xác nhận");

        initComponent();
        setLayoutAndAddComponent();
        addLisenerForComponet();
    }

    private void initComponent(){
        emailLabel = new JLabel("Email(dùng để đăng nhập)");
        emailTextField = new JTextField();

        passLabel_1 = new JLabel("Mật khẩu");
        passwordField_1= new JPasswordField();

        passLabel_2 = new JLabel("Xác nhận mật khẩu");
        passwordField_2 = new JPasswordField();

        defaultEchoChar = passwordField_1.getEchoChar();

        showPasswdCheckBox = new JCheckBox("Hiện mật khẩu");

        nameLabel = new JLabel("Tên");
        nameTextField = new JTextField();

        phoneLabel = new JLabel("Số điện thoại");
        phoneTextField = new JTextField();

        addressLabel = new JLabel("Địa chỉ");
        addressTextField = new JTextField();

        registerButton = new JButton("Đăng ký tài khoảng");
    }

    private void setLayoutAndAddComponent(){
        setLayout(new GridLayout(8, 2));

        add(emailLabel);    add(emailTextField);
        add(passLabel_1);   add(passwordField_1);
        add(passLabel_2);   add(passwordField_2);
        add(new JLabel());  add(showPasswdCheckBox);
        add(nameLabel);     add(nameTextField);
        add(phoneLabel);    add(phoneTextField);
        add(addressLabel);  add(addressTextField);
        add(new JLabel());  add(registerButton);
    }

    private void addLisenerForComponet(){
        showPasswdCheckBox.addActionListener(e->{
            if(showPasswdCheckBox.isSelected()){
                passwordField_1.setEchoChar((char)0);
                passwordField_2.setEchoChar((char)0);
            }
            else{
                passwordField_1.setEchoChar(defaultEchoChar);
                passwordField_2.setEchoChar(defaultEchoChar);
            }
        });

        registerButton.addActionListener(e->tryRegisterNewCustomer());
    }

    private void tryRegisterNewCustomer(){
        String pass1 = new String(passwordField_1.getPassword());
        String pass2 = new String(passwordField_2.getPassword());
        if(pass1.compareTo(pass2) != 0){
            JOptionPane.showMessageDialog(
                    this,
                    "Mật khẩu không khớp!",
                    "Chú ý",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try{
            String newAccountEmail = CustomerProcess.registerNewAccount(
                    nameTextField.getText(),
                    emailTextField.getText(),
                    pass1,
                    phoneTextField.getText(),
                    addressTextField.getText()
            );
            int autoLogin = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có muốn đăng nhập ngay bây giờ?",
                    "Đăng ký tài khoảng thành công",
                    JOptionPane.YES_NO_OPTION
            );
            if(autoLogin == JOptionPane.YES_OPTION){
                CustomerFrame view = new CustomerFrame(newAccountEmail);
                view.setVisible(true);
            }
            dispose();  //Close Register Window
        }
        catch (DataAlreadyExistsException e){
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Chú ý",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "SQL Exception",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }
}