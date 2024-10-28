package edu.ptithcm.controller;

import java.sql.*;
import edu.ptithcm.model.MySql;

public class CustomerProcess {

    /**
     * @author Le Ngoc Tu
     * @return <code>String new user's email</code>
     * @throws SQLException xử lý lệnh <code>SELECT</code>, <code>INSERT</code> lỗi thì mới gặp, hên xui :))
     * @throws DataAlreadyExistsException Một trong các dữ liệu sau đã tồn tại:
     */
    public static String registerNewAccount(String name, String email, String password, String phone, String address)
            throws SQLException, DataAlreadyExistsException
    {
        String queryCheckEmail = "SELECT * FROM users WHERE email = ? LIMIT 1";
        String queryCheckPhone = "SELECT * FROM users WHERE phone = ? LIMIT 1";
        String queryInsert = "INSERT INTO users (name, email, password, phone, address) VALUES (?, ?, ?, ?, ?)";

        try(
                Connection con = MySql.getConnection();
                PreparedStatement stmCheckEmail = con.prepareStatement(queryCheckEmail);
                PreparedStatement stmCheckPhone = con.prepareStatement(queryCheckPhone);
                PreparedStatement stmInsert = con.prepareStatement(queryInsert)
        ){
            stmCheckEmail.setObject(1, email);
            if(stmCheckEmail.executeQuery().next())
                throw new DataAlreadyExistsException("Email đã tồn tại");

            stmCheckPhone.setObject(1,phone);
            if(stmCheckPhone.executeQuery().next())
                throw new DataAlreadyExistsException("Số điện thoại đã tồn tại");

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