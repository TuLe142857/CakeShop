package edu.ptithcm.controller;

import edu.ptithcm.model.Data.Category;
import edu.ptithcm.model.MySql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CategoryProcess {
    /**
     * @author Le Ngoc Tu
     */
    public static ArrayList<Category> selectCategoryThatInBussiness()
    {
        ArrayList<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM Categories WHERE id IN (SELECT DISTINCT category_id FROM Products WHERE status = TRUE)";
        try(
                Connection con = MySql.getConnection();
                Statement stm = con.createStatement();
                ResultSet r = stm.executeQuery(query)
        ){
            while(r.next()){
                categories.add(new Category(r));
            }
        }
        catch (SQLException e){
            HandelSQLException.showMessageAndCloseProgram(e);
        }
        return categories;
    }
}