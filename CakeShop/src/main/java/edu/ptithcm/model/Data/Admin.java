package edu.ptithcm.model.Data;

import edu.ptithcm.controller.HandelSQLException;
import edu.ptithcm.model.MySql;

import java.sql.*;
import java.time.LocalDateTime;

public class Admin{
    public static void main(String []args){
        MySql.setDefaultPasswd("tule123");
        try(
                Connection con = MySql.getConnection();
                Statement stm = con.createStatement();
                ResultSet r = stm.executeQuery("select * from Admins");
        )
        {
            while (r.next()){
                System.out.println(new Admin(r));
            }
        }catch(SQLException e){
            HandelSQLException.showMessageAndCloseProgram(e);
        }
    }

    private int id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime createdAt;


    public Admin(ResultSet r) throws SQLException{
        this.id = r.getInt("id");
        this.name = r.getString("name");
        this.email = r.getString("email");
        this.password = r.getString("password");
        this.createdAt = r.getTimestamp("created_at").toLocalDateTime();
    }

    public Admin(int id, String name, String email, String password, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}