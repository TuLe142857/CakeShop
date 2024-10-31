package edu.ptithcm.controller;

import java.sql.*;

import edu.ptithcm.model.MySql;
import edu.ptithcm.model.Data.*;
import java.util.*;

public class ReviewProcess {
    public static ArrayList<Review> selectAllReviewAboutProduct(int productID){

        ArrayList<Review> list = new ArrayList<>();
        String query = "SELECT * FROM Reviews WHERE product_id = ?";
        try (
                Connection con = MySql.getConnection();
                PreparedStatement stm = con.prepareStatement(query);
        ){
            stm.setObject(1, productID);
            ResultSet r = stm.executeQuery();
            while(r.next())
                list.add(new Review(r));
        }
        catch(SQLException e){
            HandelSQLException.showMessageAndCloseProgram(e);
        }

        return list;
    }
}