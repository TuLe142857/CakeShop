package edu.ptithcm.view.CustomerView;

import edu.ptithcm.controller.CustomerProcess;
import edu.ptithcm.model.MySql;
import edu.ptithcm.view.Login.LoginFrame;
import edu.ptithcm.view.Window.SubWindow;

import javax.swing.*;
import java.sql.*;

/**
 * @author Le Ngoc Tu
 */
public class CustomerFrame extends SubWindow{

    public CustomerFrame(String loginName){
        setTitle("Hello user "  + loginName);
    }

    public static void login(){
        LoginFrame l = new LoginFrame();
        l.setPermitRegister(true);
        l.setChecker((user, password)-> CustomerProcess.checkLogin(user, password));
        l.setDoRegister(o->{
            RegisterFrame view = new RegisterFrame();
            view.setVisible(true);
        });

        l.setDoAfterLogin((userLoginName)->
                {
                    CustomerFrame view = new CustomerFrame((String)(userLoginName));
                    view.setVisible(true);
                }
        );

        l.setTitle("Customer login");
        l.setVisible(true);
    }
}