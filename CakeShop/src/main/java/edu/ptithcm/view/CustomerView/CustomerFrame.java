package edu.ptithcm.view.CustomerView;

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
        l.setChecker(
                (u, p)->{
                    String query = "select * from users where email = ? and password = ? limit 1";
                    try(Connection con = MySql.getConnection();
                        PreparedStatement stm = con.prepareStatement(query)
                    ) {
                        stm.setObject(1, u);
                        stm.setObject(2, p);
                        ResultSet r = stm.executeQuery();
                        return r.next();
                    }
                    catch(SQLException e){
                        JOptionPane.showMessageDialog(
                                null,
                                "Loi sql khi check login: " + e.getMessage(),
                                "SQL ERROR",
                                JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                        return false;
                    }
                }
        );
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