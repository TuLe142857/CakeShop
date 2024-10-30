package edu.ptithcm.model.Data;


import edu.ptithcm.controller.HandelSQLException;
import edu.ptithcm.model.MySql;

import java.sql.*;
import java.time.LocalDateTime;

public class Cart{
    public static void main(String []args){
        MySql.setDefaultPasswd("tule123");
        try(

                Connection con = MySql.getConnection();
                Statement stm = con.createStatement();
                ResultSet r = stm.executeQuery("SELECT * FROM Carts")
        ){
            while(r.next()){
                System.out.println(new Cart(r));
            }
        }catch(SQLException e){
            HandelSQLException.showMessageAndCloseProgram(e);
        }
    }
    private int userID;
    private int productID;
    private int quantity;
    private LocalDateTime addedAt;

    public Cart(ResultSet r) throws SQLException{
        this.userID = r.getInt("user_id");
        this.productID = r.getInt("product_id");
        this.quantity = r.getInt("quantity");
        this.addedAt = r.getTimestamp("added_at").toLocalDateTime();
    }

    public Cart(int userID, int productID, int quantity, LocalDateTime addedAt) {
        this.userID = userID;
        this.productID = productID;
        this.quantity = quantity;
        this.addedAt = addedAt;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "userID=" + userID +
                ", productID=" + productID +
                ", quantity=" + quantity +
                ", addedAt=" + addedAt +
                '}';
    }
}