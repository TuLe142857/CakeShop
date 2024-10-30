package edu.ptithcm.controller;

import edu.ptithcm.model.Data.Category;
import edu.ptithcm.model.Data.Product;
import edu.ptithcm.model.MySql;

import java.sql.*;
import java.util.ArrayList;

public class ProductProcess {

    /**
     * Lấy tất cả sản phẩm đang kinh doanh(Product.isInBusiness = true).
     * @param category_id Lọc theo phân loại
     * @param getAvailableOnly <code>true</code>(Chỉ lấy các sản phẩm có quantity >0); <code>false</code>(lấy tất cả)
     * @return
     */
    public static ArrayList<Product> selectByFilter(int category_id, boolean getAvailableOnly){
        System.out.println("Select product " + category_id + getAvailableOnly);
        String query = "";;
        if(getAvailableOnly)
            query = "SELECT * FROM Products WHERE category_id = ? AND status = TRUE AND quantity > 0";
        else
            query = "SELECT * FROM Products WHERE category_id = ? AND status = TRUE";

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

    public static ArrayList<Product> selectAllProductInBussiness(boolean getAvailableOnly){
        System.out.println("Select all product in business");
        String query = "SELECT * FROM Products WHERE status = TRUE";
        if(getAvailableOnly)
            query += " AND quantity > 0";
        ArrayList<Product> products = new ArrayList<>();
        try(
                Connection con = MySql.getConnection();
                Statement stm = con.createStatement();
                ResultSet r = stm.executeQuery(query)
        ){

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