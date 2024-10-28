package edu.ptithcm.model.Data;

import edu.ptithcm.model.MySql;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * @author Le Ngoc Tu
 */
public class User {
    public static void main (String []args){
        MySql.setDefaultPasswd("tule123");
        try(
                Connection con = MySql.getConnection();
                Statement stm = con.createStatement();
                ResultSet r = stm.executeQuery("select * from users")
                )
        {
            System.out.println("Connected");
            int count = 0;
            while(r.next()){
                User u = new User(r);
                System.out.println("User " + (count++) + ": " + u.toString());
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private int id;
    private String name;
    private String email;
    private String passwd;
    private String phoneNumber;
    private String address;
    private LocalDateTime createAt; //Khong co setter

    public User(ResultSet r)throws SQLException{
        this.id = r.getInt("id");
        this.name = r.getString("name");
        this.email = r.getString("email");
        this.passwd = r.getString("password");
        this.phoneNumber = r.getString("phone");
        this.address = r.getString("address");
        this.createAt = r.getTimestamp("created_at").toLocalDateTime();
    }

    public User(int id, String name,
                String email, String passwd,
                String phoneNumber, String address,
                LocalDateTime createAt)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwd = passwd;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.createAt = createAt;
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

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

//    public void setCreateAt(LocalDateTime createAt) {
//        this.createAt = createAt;
//    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", passwd='" + passwd + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}