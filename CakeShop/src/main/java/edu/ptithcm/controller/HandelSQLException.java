package edu.ptithcm.controller;

import javax.swing.*;
import java.sql.SQLException;

/**
 * @author Le Ngoc Tu
 */
public class HandelSQLException{


    public static void showMessageAndCloseProgram(SQLException e){
        JOptionPane.showMessageDialog(
                null,
                "Unexpected SQLException: " + e.getMessage() + "\nThe program will close automatically.",
                "SQLException Handeler",
                JOptionPane.ERROR_MESSAGE
        );
        System.out.println("SQLException " + e.getMessage()+  " was handeled by showing message and closing program");
        e.printStackTrace();
        System.exit(-1);
    }

    public static void showMessageAndContinue(SQLException e){
        JOptionPane.showMessageDialog(
                null,
                "Unexpected SQLException: " + e.getMessage(),
                "SQLException Handler",
                JOptionPane.ERROR_MESSAGE
        );
        System.out.println("SQLException " + e.getMessage()+  " was handeled by show message and continue");
        e.printStackTrace();
    }
}