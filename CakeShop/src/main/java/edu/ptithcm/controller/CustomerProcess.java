package edu.ptithcm.controller;

import java.sql.*;

import edu.ptithcm.model.Data.User;
import edu.ptithcm.model.MySql;

import javax.swing.*;

public class CustomerProcess {

    public static boolean checkLogin(String email, String password){
        String query = "select * from Users where email = ? and password = ? limit 1";
        try(Connection con = MySql.getConnection();
            PreparedStatement stm = con.prepareStatement(query)
        ) {
            stm.setObject(1, email);
            stm.setObject(2, password);
            ResultSet r = stm.executeQuery();
            return r.next();
        }
        catch(SQLException e){
            HandelSQLException.showMessageAndCloseProgram(e);
        }
        return false;
    }

    /**
     * @author Le Ngoc Tu
     * @return <code>String new user's email</code>
     * @throws SQLException xử lý lệnh <code>SELECT</code>, <code>INSERT</code> lỗi thì mới gặp, hên xui :))
     * @throws DataAlreadyExistsException exception này mang thông báo lỗi data đã tồn tại
     */
    public static String registerNewAccount(String name, String email, String password, String phone, String address)
            throws SQLException, DataAlreadyExistsException
    {
        String queryCheck = "SELECT * FROM Users WHERE email = ? OR phone = ? LIMIT 1";
        String queryInsert = "INSERT INTO Users (name, email, password, phone, address) VALUES (?, ?, ?, ?, ?)";

        try(
                Connection con = MySql.getConnection();
                PreparedStatement stmCheck = con.prepareStatement(queryCheck);
                PreparedStatement stmInsert = con.prepareStatement(queryInsert)
        ){
            stmCheck.setObject(1, email);
            stmCheck.setObject(2, phone);
            ResultSet checkResult = stmCheck.executeQuery();
            if(checkResult.next()){
                User user = new User(checkResult);
                String message = ((user.getEmail().compareTo(email) == 0)?"Email ":"Số điện thoại ") + "đã tồn tại";
                throw new DataAlreadyExistsException(message);
            }

            stmInsert.setObject(1, name);
            stmInsert.setObject(2, email);
            stmInsert.setObject(3, password);
            stmInsert.setObject(4, phone);
            stmInsert.setObject(5, address);
            int effectedRows = stmInsert.executeUpdate();
            if(effectedRows == 0)
                throw new SQLException("Dữ liệu dúng hết nhưng sau khi insert totalEffedtedRows = 0. Check lai lệnh INSERT");
        }
        return email;
    }
}