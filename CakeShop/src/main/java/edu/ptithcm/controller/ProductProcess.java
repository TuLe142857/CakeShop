package edu.ptithcm.controller;

import edu.ptithcm.model.Data.Category;
import edu.ptithcm.model.Data.Product;
import edu.ptithcm.model.MySql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductProcess {

    public static ArrayList<Product> selectByCategory(int category_id){
        System.out.println("Select product " + category_id);
        String query = "SELECT * FROM Products WHERE category_id = ?";
        ArrayList<Product> products = new ArrayList<>();
        try(
                Connection con = MySql.getConnection();
                PreparedStatement stm = con.prepareStatement(query)
        ){
            stm.setObject(1, category_id);
            ResultSet r = stm.executeQuery();
            while(r.next()){
                products.add(new Product(r));
            }
        }
        catch (SQLException e){
            HandelSQLException.showMessageAndCloseProgram(e);
        }
        return products;
    }
}