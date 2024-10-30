package edu.ptithcm.controller;

import java.sql.*;

import edu.ptithcm.model.Data.Admin;
import edu.ptithcm.model.MySql;

public class AdminProcess{

    public static boolean checkLogin(String email, String password){
        String query = "SELECT * FROM Admins WHERE email = ? AND password = ? LIMIT 1";
        try(
                Connection con = MySql.getConnection();
                PreparedStatement stm = con.prepareStatement(query)
        ){
            stm.setObject(1, email);
            stm.setObject(2, password);
            ResultSet r = stm.executeQuery();
            return r.next();
        }catch(SQLException e){
            HandelSQLException.showMessageAndCloseProgram(e);
        }
        return false;
    }

    /**
     * @return null or Admin
     */
    public static Admin selectByEmail(String email) {
        String query = "SELECT * FROM Admins WHERE email = ?";
        try(
                Connection con = MySql.getConnection();
                PreparedStatement stm = con.prepareStatement(query)
        ){
            stm.setObject(1, email);
            ResultSet r = stm.executeQuery();
            if(r.next())
                return new Admin(r);
        }catch(SQLException e){
            HandelSQLException.showMessageAndCloseProgram(e);
        }
        return null;
    }
}