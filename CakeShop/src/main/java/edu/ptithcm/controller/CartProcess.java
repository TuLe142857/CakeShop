package edu.ptithcm.controller;

import java.sql.*;
import java.util.ArrayList;

import edu.ptithcm.model.MySql;
import edu.ptithcm.model.Data.*;

public class CartProcess{
    public static void main(String[]args){
        MySql.setDefaultPasswd("tule123");
//        addProductToCart(1, 10, 1);
        updateCart(1, 1, 100);
    }

    /**
     * Cart co the ton tai hoac khong(neu chua co, tu dong them vao)
     * @param userId
     * @param productID
     * @param quantity so luong san pham tang them
     */
    public static void addProductToCart(int userId, int productID, int quantity){
        String queryFindExisting = "SELECT * FROM Carts WHERE product_id = ? AND user_id = ?";
        String queryInsert = "INSERT INTO Carts (user_id, product_id, quantity) VALUES (?, ?, ?)";
        String queryUpdate = "UPDATE Carts SET quantity = ? WHERE user_id = ? AND product_id = ?";

        try (
                Connection con = MySql.getConnection();
                PreparedStatement stmFindExisting = con.prepareStatement(queryFindExisting);
                PreparedStatement stmInsert = con.prepareStatement(queryInsert);
                PreparedStatement stmUpdate = con.prepareStatement(queryUpdate)
        ){
            //Kiem tra xem cart co ton tai chua
            stmFindExisting.setObject(1, productID);    stmFindExisting.setObject(2, userId);
            ResultSet r = stmFindExisting.executeQuery();


            //Neu da ton tai thi Update
            if(r.next()){
                System.out.println("\tCheckresult: cart already exists. To do update");
                Cart oldCart = new Cart(r);
                int newQuantity = oldCart.getQuantity() + quantity;
//                String queryUpdate = "UPDATE Carts SET quantity = ? WHERE user_id = ? AND product_id = ?";
                stmUpdate.setObject(1, newQuantity);
                stmUpdate.setObject(2, userId);
                stmUpdate.setObject(3, productID);
                int updateResult = stmUpdate.executeUpdate();
                if(updateResult == 0) {
                    throw new SQLException("Update cart failed, total rows affected = 0");
                }
                System.out.println("result = " + updateResult) ;
            }
            //Neu chua ton tai thi Insert
            else{
                System.out.println("Checkresult: cart not exist, to do insert");
//                String queryInsert = "INSERT INTO Carts (user_id, product_id, quantity) VALUES (?, ?, ?)";
                stmInsert.setObject(1, userId);
                stmInsert.setObject(2, productID);
                stmInsert.setObject(3, quantity);
                int updateResult = stmInsert.executeUpdate();
                if(updateResult == 0){
                    throw new SQLException("Insert new cart filed, total rows affected = 0");
                }
                System.out.println("result = " + updateResult) ;
            }
        }
        catch(SQLException e){
            HandelSQLException.showMessageAndCloseProgram(e);
        }
    }

    /**
     * Luu y cart phai ton tai
     * @param userId
     * @param productID
     * @param quantity
     */
    public static void updateCart(int userId, int productID, int quantity){
        String queryFindExisting = "SELECT * FROM Carts WHERE product_id = ? AND user_id = ?";
        String queryUpdate = "UPDATE Carts SET quantity = ? WHERE user_id = ? AND product_id = ?";
        System.out.println("Call function addProduct to cart. Check if(cart already exists)->upadte; else insert");

        try (
                Connection con = MySql.getConnection();
                PreparedStatement stmFindExisting = con.prepareStatement(queryFindExisting);
                PreparedStatement stmUpdate = con.prepareStatement(queryUpdate)
        ){
            //Kiem tra xem cart co ton tai chua
            stmFindExisting.setObject(1, productID);    stmFindExisting.setObject(2, userId);
            ResultSet r = stmFindExisting.executeQuery();


            //Neu da ton tai thi Update
            if(r.next()){
                System.out.println("Update cart, userid = "+ userId + " productid = " + productID + " newquantity = " + quantity) ;
                Cart oldCart = new Cart(r);
//                String queryUpdate = "UPDATE Carts SET quantity = ? WHERE user_id = ? AND product_id = ?";
                stmUpdate.setObject(1, quantity);
                stmUpdate.setObject(2, userId);
                stmUpdate.setObject(3, productID);
                int updateResult = stmUpdate.executeUpdate();
                if(updateResult == 0) {
                    throw new SQLException("Update cart failed, total rows affected = 0");
                }
                System.out.println("result = " + updateResult) ;
            }
            //Neu chua ton tai thi Insert
            else{
                throw new SQLException("cart id do not exists");
            }
        }
        catch(SQLException e){
            HandelSQLException.showMessageAndCloseProgram(e);
        }
    }

    public static ArrayList<Cart> selectUserCart(int userID){
        ArrayList<Cart> list = new ArrayList<>();
        String query = "SELECT * FROM Carts WHERE user_id = ?";

        try(
            Connection con = MySql.getConnection();
            PreparedStatement stm = con.prepareStatement(query)
        ){
            stm.setObject(1, userID);
            ResultSet r = stm.executeQuery();
            while(r.next()){
                list.add(new Cart(r));
            }
        }catch(SQLException e){
            HandelSQLException.showMessageAndCloseProgram(e);
        }

        return list;
    }
}