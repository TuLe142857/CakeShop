package edu.ptithcm;

import edu.ptithcm.controller.HandelSQLException;
import edu.ptithcm.view.App;
import edu.ptithcm.model.MySql;

import javax.swing.*;
import java.sql.*;
import java.util.*;

public class Main {
    static{
        System.out.println("Start.....");
        setSystemLookAndFell();
        FirstTimeConnectToMySql();
    }

    public static void main(String[] args) {
        App app = new App();
        app.setVisible(true);
    }

    public static void setSystemLookAndFell(){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e){
            System.out.println("Can not set system LookAndFeel, exception message: " + e.getMessage());
        }
    }

    public static void FirstTimeConnectToMySql(){
        System.out.print("Data base password: ");
        Scanner in = new Scanner(System.in);
        MySql.setDefaultPasswd(in.nextLine());
        in.close();
        try(Connection con = MySql.getConnection()){}
        catch (SQLException e){
            HandelSQLException.showMessageAndCloseProgram(e);
        }
    }

}