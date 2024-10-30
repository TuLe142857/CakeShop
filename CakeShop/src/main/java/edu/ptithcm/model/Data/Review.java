package edu.ptithcm.model.Data;

import edu.ptithcm.controller.HandelSQLException;
import edu.ptithcm.model.MySql;

import java.sql.*;
import java.time.LocalDateTime;

public class Review{

    public static void main(String []args){
        MySql.setDefaultPasswd("tule123");
        try(
                Connection con = MySql.getConnection();
                Statement stm = con.createStatement();
                ResultSet r = stm.executeQuery("select * from Reviews");
        )
        {
            while (r.next()){
                System.out.println(new Review(r));
            }
        }catch(SQLException e){
            HandelSQLException.showMessageAndCloseProgram(e);
        }
    }

    private int id;
    private int productID;
    private int userID;
    private int rating;//1-5
    private String comment;
    private LocalDateTime createdAt;

    public Review(ResultSet r)throws SQLException{
        this.id = r.getInt("id");
        this.productID = r.getInt("product_id");
        this.userID = r.getInt("user_id");
        this.rating = r.getInt("rating");
        this.comment = r.getString("comment");
        this.createdAt = r.getTimestamp("created_at").toLocalDateTime();
    }
    public Review(int id, int productID, int userID, int rating, String comment, LocalDateTime createdAt) {
        this.id = id;
        this.productID = productID;
        this.userID = userID;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if(!(rating >= 1 && rating <= 5))
            throw new IllegalArgumentException("Rating must between 1 and 5");
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", productID=" + productID +
                ", userID=" + userID +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}